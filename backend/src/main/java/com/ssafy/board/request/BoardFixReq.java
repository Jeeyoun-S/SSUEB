package com.ssafy.board.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * [skeleton-code] 
 * 유저 로그인 API ([POST] /api/v1/auth/login) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("BoardFixReq")
public class BoardFixReq {
	
	@ApiModelProperty(value = "수정할 게시글 번호", dataType = "int", example="0")
	int no;
	
	@ApiModelProperty(value = "수정할 게시글 제목")
	String boardTitle;
	
	@ApiModelProperty(value = "수정할 게시글 내용")
	String boardContent;
}
