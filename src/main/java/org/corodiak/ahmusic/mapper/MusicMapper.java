package org.corodiak.ahmusic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.corodiak.ahmusic.vo.Music;

@Mapper
public interface MusicMapper {

	public List<Music> selectAllMusic();
	public Music selectMusicByIdx(@Param("idx")int idx);
	public List<Music> selectMusicByUserIdx(@Param("userIdx")int userIdx);
	public List<Music> selectMusicByHashTag(@Param("hashtag")String hashTag);
	public List<Music> selectMusicByKeyword(@Param("keyword")String keyword);
	public List<Music> selectMusicByThumbsUp(@Param("userIdx")int userIdx);
	public int insertMusic(@Param("music")Music music);
	public void updatePlayCount(@Param("idx")int idx);
	
}
