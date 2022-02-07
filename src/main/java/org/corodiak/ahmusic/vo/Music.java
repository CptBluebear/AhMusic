package org.corodiak.ahmusic.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Music {

	protected int idx;
	protected String title;
	protected String content;
	protected String thumbnail;
	protected int type;
	protected String link;
	protected int playcount;
	protected LocalDateTime uploadDate;
	protected int userIdx;
	
}
