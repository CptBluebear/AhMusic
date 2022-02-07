package org.corodiak.ahmusic.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MusicResponse {

	private int idx;
	private String title;
	private String content;
	private String thumbnail;
	private int type;
	private String link;
	private int playcount;
	private LocalDateTime uploadDate;
	private int userIdx;
	private String userNickname;
	private int thumbsupCount;
	
	public MusicResponse() {}
	public MusicResponse(Music music, String userNickname, int thumbsupCount) {
		this.idx = music.getIdx();
		this.title = music.getTitle();
		this.content = music.getContent();
		this.thumbnail = music.getThumbnail();
		this.type = music.getType();
		this.link = music.getLink();
		this.playcount = music.getPlaycount();
		this.uploadDate = music.getUploadDate();
		this.userIdx = music.getUserIdx();
		this.userNickname = userNickname;
		this.thumbsupCount = thumbsupCount;
	}
	
}
