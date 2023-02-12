package com.ssafy.user.withdrawal.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *  withdrawal 회원탈퇴 API ([POST] /api/user/withdrawal) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("UserWithdrawalPostRequest")
public class UserWithdrawalPostRequest {
	
	@ApiModelProperty(name="유저 ID", example="ssafy@gmail.com")
	String id;
}
