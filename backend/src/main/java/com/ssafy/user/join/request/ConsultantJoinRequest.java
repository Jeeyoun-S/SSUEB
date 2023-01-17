package com.ssafy.user.join.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConsultantJoinRequest {
	
	@ApiModelProperty(value = "상담가능한 동물 (강아지/고양이/토끼/패럿/기니피그/햄스터)", required = true)
	String consultPetType;
	
	@ApiModelProperty(value = "자격번호", required = true)
	String consultantLicenseNumber;
	
	@ApiModelProperty(value = "자격증 사본 파일명", required = true)
	MultipartFile consultantLicenseCopyImage;
}
