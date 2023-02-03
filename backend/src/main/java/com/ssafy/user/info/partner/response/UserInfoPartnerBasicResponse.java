package com.ssafy.user.info.partner.response;

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
	
	@ApiModelProperty(value = "반려인 회원정보")
	UserPartnerInfoData data;
	
	@Data
	@NoArgsConstructor
	public static class UserPartnerInfoData {
		UserInfoResponse userInfo;
		List<UserPetResponse> petInfo;
	}
}
