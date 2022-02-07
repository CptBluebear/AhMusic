package org.corodiak.ahmusic.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.corodiak.ahmusic.mapper.HashTagMapper;
import org.corodiak.ahmusic.mapper.MusicHashTagMapper;
import org.corodiak.ahmusic.mapper.MusicMapper;
import org.corodiak.ahmusic.mapper.ThumbsUpMapper;
import org.corodiak.ahmusic.mapper.UserMapper;
import org.corodiak.ahmusic.util.FileUtil;
import org.corodiak.ahmusic.vo.Music;
import org.corodiak.ahmusic.vo.MusicHashTag;
import org.corodiak.ahmusic.vo.MusicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MusicService {

	@Value("${resources.music.location}")
	private String resourceMusicLocation;
	
	@Value("${resources.music.uripath}")
	private String resourceMusicUriPath;
	
	@Value("${resources.thumbs.location}")
	private String resourceThumbsLocation;
	
	@Value("${resources.thumbs.uripath}")
	private String resourceThumbsUriPath;
	
	@Autowired
	HashTagService hashTagService;
	
	@Autowired
	MusicMapper musicMapper;
	
	@Autowired
	HashTagMapper hashTagMapper;
	
	@Autowired
	ThumbsUpMapper thumbsUpMapper;
	
	@Autowired
	MusicHashTagMapper musicHashTagMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	FileUtil fileUtil;
	
	public List<MusicResponse> getUserMusicList(int userIdx) {
		return convertMusicList(musicMapper.selectMusicByUserIdx(userIdx));
	}
	
	public List<MusicResponse> getMusicList() {
		return convertMusicList(musicMapper.selectAllMusic());
	}
	
	public MusicResponse getMusic(int idx) {
		musicMapper.updatePlayCount(idx);
		return convertMusicList(musicMapper.selectMusicByIdx(idx));
	}
	
	public List<MusicResponse> getHashTagedMusicList(String hashTag) {
		return convertMusicList(musicMapper.selectMusicByHashTag(hashTag));
	}
	
	public List<MusicResponse> getThumbsUpMusicList(int userIdx) {
		return convertMusicList(musicMapper.selectMusicByThumbsUp(userIdx));
	}
	
	public List<MusicResponse> getKeywordMusicList(String keyword) {
		return convertMusicList(musicMapper.selectMusicByKeyword(keyword));
	}
	
	//닉네임 기반으로 찾기 만들어야됨
	
	//여기 완성해야함
	public boolean createMusic(String title, String content, MultipartFile thumbsFile, int type, MultipartFile musicFile, int userIdx, String[] hashTagList) {
		
		Music music = new Music();
		music.setTitle(title);
		music.setContent(content);
		music.setType(type);
		music.setUserIdx(userIdx);
		music.setUploadDate(LocalDateTime.now());
		music.setPlaycount(0);
		
		//업로드 파일 처리 부분
		String thumbnail = null;
		if(thumbsFile != null) {
			thumbnail = fileUtil.save(thumbsFile, resourceThumbsLocation);
			if(thumbnail == null) {
				return false;
			}
			thumbnail = resourceThumbsUriPath + "/" + thumbnail;
		}
		
		String musicLink = fileUtil.save(musicFile, resourceMusicLocation);
		if(musicLink == null) {
			//Error Handling
			return false;
		}
		musicLink = resourceMusicUriPath+ "/" + musicLink;
		
		music.setThumbnail(thumbnail);
		music.setLink(musicLink);
		
		//해시태그 정리 해야함
		
		musicMapper.insertMusic(music);
		
		for(String tag:hashTagList) {
			MusicHashTag musicHashTag = new MusicHashTag();
			musicHashTag.setMusicIdx(music.getIdx());
			musicHashTag.setHashTagIdx(hashTagService.getHashTagIdx(tag));
			musicHashTagMapper.insertMusicHashTag(musicHashTag);
			//Music-HASHTAG에 값 박아넣어야됨
			//인자는 time, music_idx, hashtag_idx
		}
		
		return true;
	}
	
	private List<MusicResponse> convertMusicList(List<Music> list) {
		List<MusicResponse> results = new ArrayList<MusicResponse>();
		
		for(Music music:list) {
			MusicResponse musicResponse = new MusicResponse(music, userMapper.selectUserNickNameByIdx(music.getUserIdx()), thumbsUpMapper.countByMusicIdx(music.getIdx()));
			results.add(musicResponse);
		}
		
		return results;
	}
	
	private MusicResponse convertMusicList(Music music) {
		MusicResponse musicResponse = new MusicResponse(music, userMapper.selectUserNickNameByIdx(music.getUserIdx()), thumbsUpMapper.countByMusicIdx(music.getIdx()));
		return musicResponse;
		
	}
	
}
