package com.ssafy.user.admin.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * user 관리자(admin) API ([POST] /api/user/admin) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("UserAlertEmailResponse")
public class UserAlertEmailResponse extends BaseResponseBody{
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	public static UserAlertEmailResponse of(Integer statusCode, String response, String message) {
		UserAlertEmailResponse res = new UserAlertEmailResponse();
		
		res.setStatusCode(statusCode);
		res.setResponse(response);
		res.setMessage(message);
		
		return res;
	}
}
