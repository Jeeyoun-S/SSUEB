package com.ssafy.user.login.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * user 로그인 API ([POST] /api/user/login) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostResponse extends BaseResponseBody{
	
	@ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
	String accessToken;
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	public static UserLoginPostResponse of(Integer statusCode, String response, String message, String accessToken) {
		UserLoginPostResponse res = new UserLoginPostResponse();
		
		res.setStatusCode(statusCode);
		res.setResponse(response);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		
		return res;
	}
}
