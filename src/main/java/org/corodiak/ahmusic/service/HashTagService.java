package org.corodiak.ahmusic.service;

import org.corodiak.ahmusic.mapper.HashTagMapper;
import org.corodiak.ahmusic.vo.HashTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashTagService {
	
	@Autowired
	HashTagMapper hashTagMapper;
	
	public int getHashTagIdx(String hashTag) {
		
		HashTag tag = hashTagMapper.selectTagByName(hashTag);
		if(tag == null) {
			tag = new HashTag();
			tag.setName(hashTag);
			hashTagMapper.insertHashTag(tag);
		}
		
		return tag.getIdx();
	}

}
