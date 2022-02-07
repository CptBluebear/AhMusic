package org.corodiak.ahmusic.service;

import java.util.HashMap;
import java.util.Map;

import org.corodiak.ahmusic.mapper.UserMapper;
import org.corodiak.ahmusic.util.JWTUtil;
import org.corodiak.ahmusic.vo.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	JWTUtil jwtUtil;
	
	@Autowired
	UserMapper userMapper;
	
	public String signin(String id, String pw) {
		
		User user = userMapper.selectUserById(id);
		
		if(user == null || !BCrypt.checkpw(pw, user.getPw()))
			return null;
		
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("idx", user.getIdx());
		
		return jwtUtil.createToken(payload);
	}
	
	public boolean signup(String id, String pw, String nickname) {
		
		User user = new User();
		user.setId(id);
		user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt()));
		user.setNickname(nickname);
		
		int result = userMapper.insertUser(user);
		if(result != 1)
			return false;
		
		return true;
		
	}
	
	public String getUserNickname(int userIdx) {
		return userMapper.selectUserNickNameByIdx(userIdx);
	}
}
