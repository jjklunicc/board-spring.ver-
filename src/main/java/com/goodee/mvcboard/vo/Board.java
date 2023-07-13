package com.goodee.mvcboard.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Board {
	private int boardNo;
	private String localName;
	private String boardTitle;
	private String boardContent;
	private String memberId;
	private String createdate;
	private String updatedate;
	
	List<MultipartFile> multipartFile;
}
