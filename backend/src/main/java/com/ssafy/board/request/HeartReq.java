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
@ApiModel("LikeRequest")
public class HeartReq {
	@ApiModelProperty(value = "PK, AI")
	int no;
	@ApiModelProperty(value = "좋아요를 누른 게시글 번호")
	int boardNo;
	@ApiModelProperty(value = "좋아요를 누른 사람", example="aa@a")
	String userId;
	@ApiModelProperty(value = "좋아요가 눌려 있는지 - 1이면 눌러있음 -> 삭제요청, 0이면 안눌려있음 -> 좋아요 누름요청")
	int flag;
}
