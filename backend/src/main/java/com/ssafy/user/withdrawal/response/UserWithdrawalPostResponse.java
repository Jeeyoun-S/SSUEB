package com.ssafy.user.withdrawal.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * withdrawal 회원 탈퇴 API ([POST] /api/user/withdrawal) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("UserWithdrawalPostResponse")
public class UserWithdrawalPostResponse extends BaseResponseBody{
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	public static UserWithdrawalPostResponse of(Integer statusCode, String response, String message) {
		UserWithdrawalPostResponse res = new UserWithdrawalPostResponse();
		
		res.setStatusCode(statusCode);
		res.setResponse(response);
		res.setMessage(message);
		
		return res;
	}
}
