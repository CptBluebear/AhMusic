package org.corodiak.ahmusic.controller;

import org.corodiak.ahmusic.service.MusicService;
import org.corodiak.ahmusic.service.ThumbsUpService;
import org.corodiak.ahmusic.type.Message;
import org.corodiak.ahmusic.type.exception.UserTokenInvalidException;
import org.corodiak.ahmusic.util.JWTUtil;
import org.corodiak.ahmusic.vo.Music;
import org.corodiak.ahmusic.vo.MusicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/music")
public class MusicController {

	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	MusicService musicService;
	
	@Autowired
	ThumbsUpService thumbsUpService;
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ApiOperation(value = "음악 업로드", notes = "음악 업로드")
	public ResponseEntity<Message> uploadMusic(
			@RequestParam(value = "token", required = false)String token,
			@RequestParam("title")String title,
			@RequestParam("content")String content,
			@RequestParam("type")int type,
			@RequestParam("hashtag")String hashTagList,
			@RequestParam("musicfile")MultipartFile musicFile,
			@RequestParam(value = "thumbsfile", required = false)MultipartFile thumbsFile
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		int userIdx = (int)claims.get("idx");
		
		Message message = new Message();
		
		if(!musicService.createMusic(title, content, thumbsFile, type, musicFile, userIdx, hashTagList.split("\\|"))) {
			message.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("요청 처리에 실패하였습니다.");
		}
		else {
			message.setHttpStatus(HttpStatus.OK);
			message.setMessage("요청에 대한 업로드 처리를 완료했습니다.");
		}
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
		
	}
	
	//유저 닉네임 넘겨줘야함
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "음악 전체 리스트", notes = "음악 전체 리스트 최신순으로 가져옴")
	public ResponseEntity<Message> getAllMusicList(
			@RequestParam(value = "token", required = false)String token
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		Message message = new Message();
		message.setData(musicService.getMusicList());
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@RequestMapping(value = "/{idx}", method = RequestMethod.GET)
	@ApiOperation(value = "음악 정보", notes = "음악 정보")
	public ResponseEntity<Message> getMusic(
			@RequestParam(value = "token", required = false)String token,
			@PathVariable(value = "idx")int idx 
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		Message message = new Message();
		message.setData(musicService.getMusic(idx));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@RequestMapping(value = "/mymusic", method = RequestMethod.GET)
	@ApiOperation(value = "사용자가 업로드한 음악 리스트", notes = "현재 토큰을 기반으로 사용자가 업로드한 음악 리스트")
	public ResponseEntity<Message> getMyMusicList(
			@RequestParam(value = "token", required = false)String token
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		int userIdx = (int)claims.get("idx");
		
		Message message = new Message();
		message.setData(musicService.getUserMusicList(userIdx));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@RequestMapping(value = "/user/{useridx}", method = RequestMethod.GET)
	@ApiOperation(value = "특정 유저가 올린 음악 리스트", notes = "특정 유저의 idx를 이용해 해당 사용자가 업로드한 음악 리스트")
	public ResponseEntity<Message> getUserMusic(
			@RequestParam(value = "token", required = false)String token,
			@PathVariable(value = "useridx")int userIdx 
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		Message message = new Message();
		message.setData(musicService.getUserMusicList(userIdx));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	//음악에 해당하는 해시태그 리턴도 해줘야댐 나중에
	@RequestMapping(value = "/tag/{hashtag}", method = RequestMethod.GET)
	@ApiOperation(value = "래시태그 기반 음악 검색", notes = "해시태그 기반 음악 검색")
	public ResponseEntity<Message> getHashTagedMusic(
			@RequestParam(value = "token", required = false)String token,
			@PathVariable(value = "hashtag")String hashTag 
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		Message message = new Message();
		message.setData(musicService.getHashTagedMusicList(hashTag));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	//내가 따봉 누른거 부르기
	@RequestMapping(value = "/thumbsup", method = RequestMethod.GET)
	@ApiOperation(value = "자신이 추천한 음악 리스트", notes = "사용자 토큰을 기반으로 사용자가 추천한 음악 리스트")
	public ResponseEntity<Message> getThumbsUpdMusic(
			@RequestParam(value = "token", required = false)String token
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		int userIdx = (int)claims.get("idx");
		
		Message message = new Message();
		message.setData(musicService.getThumbsUpMusicList(userIdx));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	//곡 따봉하기
	@RequestMapping(value = "/{idx}/thumbsup", method = RequestMethod.POST)
	@ApiOperation(value = "음악 추천", notes = "음악 추천")
	public ResponseEntity<Message> thumbsUp(
			@RequestParam(value = "token", required = false)String token,
			@PathVariable(value = "idx")int idx
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		int userIdx = (int)claims.get("idx");
		
		thumbsUpService.createThumbsUp(idx, userIdx);
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
	
	@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	@ApiOperation(value = "키워드 기반 음악 검색", notes = "키워드 기반 음악 검색")
	public ResponseEntity<Message> getMusicListByKeyword(
			@RequestParam(value = "token", required = false)String token,
			@PathVariable(value = "keyword")String keyword
			) throws UserTokenInvalidException {
		
		Claims claims = jwtUtil.validateToken(token);
		if(claims == null) {
			throw new UserTokenInvalidException();
		}
		
		Message message = new Message();
		message.setData(musicService.getKeywordMusicList(keyword.toLowerCase()));
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("요청에 성공하였습니다.");
		
		return ResponseEntity.status(message.getHttpStatus()).body(message);
	}
}
