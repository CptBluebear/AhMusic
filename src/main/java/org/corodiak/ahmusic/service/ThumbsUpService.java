package org.corodiak.ahmusic.service;

import java.time.LocalDateTime;

import org.corodiak.ahmusic.mapper.ThumbsUpMapper;
import org.corodiak.ahmusic.vo.ThumbsUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThumbsUpService {

	@Autowired
	ThumbsUpMapper thumbsUpMapper;
	
	public void createThumbsUp(int musicIdx, int userIdx) {
		ThumbsUp thumbsUp = new ThumbsUp();
		thumbsUp.setMusicIdx(musicIdx);
		thumbsUp.setUserIdx(userIdx);
		thumbsUp.setThumbsupDate(LocalDateTime.now());
		
		try {
			thumbsUpMapper.insertThumbsUp(thumbsUp);
		}
		catch (Exception e) {
		}
	}
	
	public int getThumbsUpCount(int musicIdx) {
		return thumbsUpMapper.countByMusicIdx(musicIdx);
	}
	
}
