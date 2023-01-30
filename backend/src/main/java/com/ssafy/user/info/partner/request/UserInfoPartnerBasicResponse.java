package com.ssafy.user.info.partner.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoPartnerBasicResponse {
	
	@ApiModelProperty(value = "성공 또는 실패")
	String response;
	
	@ApiModelProperty(value = "응답 메세지")
	String message;
	
	@ApiModelProperty(value = "")
	UserPartnerInfoData data;
	
	@Data
	@NoArgsConstructor
	public static class UserPartnerInfoData {
		UserInfoResponse userInfo;
		List<UserPetResponse> petInfo;
	}
}
