package org.corodiak.ahmusic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.ahmusic.vo.ThumbsUp;

@Mapper
public interface ThumbsUpMapper {
	
	public int countByMusicIdxAndUserIdx(@Param("musicIdx")int musicIdx, @Param("userIdx")int userIdx);
	public int countByMusicIdx(@Param("musicIdx")int musicIdx);
	public int insertThumbsUp(@Param("thumbsUp")ThumbsUp thumbsUp);

}
