package org.corodiak.ahmusic.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ThumbsUp {

	private LocalDateTime thumbsupDate;
	private int musicIdx;
	private int userIdx;
	
}
