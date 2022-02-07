package org.corodiak.ahmusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.ahmusic.vo.User;

@Mapper
public interface UserMapper {

	public User selectUserById(@Param("id")String id);
	public User selectUserByIdx(@Param("idx")int idx);
	public User selectUserByNickname(@Param("nickname")String nickname);
	public int insertUser(@Param("user")User user);
	public String selectUserNickNameByIdx(@Param("idx")int idx);
	
}
