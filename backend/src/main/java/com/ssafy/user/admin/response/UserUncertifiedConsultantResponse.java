package com.ssafy.user.admin.response;

import java.util.List;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Consultant;
import com.ssafy.user.admin.model.UserConsultantDto;

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
@ApiModel("UserUncertifiedConsultantResponse")
public class UserUncertifiedConsultantResponse extends BaseResponseBody{
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	@ApiModelProperty(name = "자격증 미인증 전문가 List")
	List<UserConsultantDto> consultants; 
	
	public static UserUncertifiedConsultantResponse of(Integer statusCode, String response, String message, List<UserConsultantDto> consultants) {
		UserUncertifiedConsultantResponse res = new UserUncertifiedConsultantResponse();
		
		res.setStatusCode(statusCode);
		res.setResponse(response);
		res.setMessage(message);
		res.setConsultants(consultants);
		
		return res;
	}
}
