package org.corodiak.ahmusic.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {

	private int idx;
	private String id;
	@JsonIgnore
	private String pw;
	private String nickname;
	
}
