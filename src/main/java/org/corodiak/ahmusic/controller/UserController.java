package org.corodiak.ahmusic.controller;

import java.util.HashMap;
import java.util.Map;

import org.corodiak.ahmusic.service.UserService;
import org.corodiak.ahmusic.type.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ApiOperation(value = "로그인", notes = "로그인")
	public ResponseEntity<Message> signup(
			@RequestParam(value = "id")String id,
			@RequestParam(value = "pw")String pw
			)
	{
		
		String token = userService.signin(id, pw);
		if(token == null) {
			Message message = new Message();
			message.setHttpStatus(HttpStatus.UNAUTHORIZED);
			message.setMessage("아이디 또는 비밀번호가 일치하지 않습니다.");
			
			return ResponseEntity.status(message.getHttpStatus()).body(message);
		}
		
		Map<String, String> tmp = new HashMap<String, String>();
		tmp.put("token", token);
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("사용자 인증이 완료되었습니다.");
		message.setData(tmp);
			
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ApiOperation(value = "회원가입", notes = "회원가입")
	public ResponseEntity<Message> signin(
			@RequestParam(value = "id")String id,
			@RequestParam(value = "pw")String pw,
			@RequestParam(value = "nickname")String nickname
			) {
		
		if(!userService.signup(id, pw, nickname)) {
			Message message = new Message();
			message.setHttpStatus(HttpStatus.CONFLICT);
			message.setMessage("회원가입에 실패하였습니다.");
			
			return ResponseEntity.status(message.getHttpStatus()).body(message);
		}
		
		Message message = new Message();
		message.setHttpStatus(HttpStatus.OK);
		message.setMessage("회원가입이 완료되었습니다.");
		
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
}
