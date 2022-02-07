package org.corodiak.ahmusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.ahmusic.vo.HashTag;

@Mapper
public interface HashTagMapper {

	public HashTag selectTagByName(@Param("name")String name);
	public int insertHashTag(@Param("hashTag")HashTag hashTag);
	
}
