package com.ssafy.user.info.consultant.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConsultantInfoRequest {
	
	@ApiModelProperty(value = "전문가 아이디", required = true)
	String id;
	
	@ApiModelProperty(value = "전문가 이름")
	String userName;
	
	@ApiModelProperty(value = "전문가 비밀번호")
	String userPassword;
	
	@ApiModelProperty(value = "전문가 알림방식")
	int userAlertFlag;
	
	@ApiModelProperty(value = "전문가 휴대폰 번호")
	String userPhone;
	
	@ApiModelProperty(value = "전문가 상담가능동물")
	String consultantPetType;
	
	@ApiModelProperty(value = "이력 및 소개글")
	String consultantIntro;
	
	@ApiModelProperty(value = "전문가 사진")
	MultipartFile consultantProfile;
	
	@ApiModelProperty(value = "전문가 사진 삭제 여부")
	boolean deleteProfile;
}
