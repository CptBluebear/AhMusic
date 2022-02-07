package org.corodiak.ahmusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.ahmusic.vo.MusicHashTag;

@Mapper
public interface MusicHashTagMapper {
	
	public int insertMusicHashTag(@Param("musicHashTag")MusicHashTag musicHashTag);
	
}
