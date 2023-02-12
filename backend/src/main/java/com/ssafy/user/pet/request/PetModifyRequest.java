package com.ssafy.user.pet.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PetModifyRequest {
	
	@ApiModelProperty(value = "반려동물 이름")
    String petName;
	
	@ApiModelProperty(value = "반려동물 대분류 (강아지, 고양이, 토끼, 페럿, 기니피그, 햄스터)")
	String petType;
	
	@ApiModelProperty(value = "반려동물 품종")
	String petVariety;
	
	@ApiModelProperty(value = "반려동물 생일 (YYYY-MM-DD로 입력)")
	String petBirth;
	
	@ApiModelProperty(value = "반려동물 특이사항")
	String petInfo;
	
	@ApiModelProperty(value = "이미지 삭제 여부")
	boolean petDeleteImage;
}
