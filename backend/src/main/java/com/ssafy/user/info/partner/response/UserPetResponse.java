package com.ssafy.user.info.partner.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPetResponse {
	
	@ApiModelProperty(name="반려동물 번호")
	int no;
	
	@ApiModelProperty(name="반려동물 이미지")
	String petImage;
	
	@ApiModelProperty(name="반려동물 이름")
	String petName;
	
	@ApiModelProperty(value = "반려동물 대분류 (강아지, 고양이, 토끼, 페럿, 기니피그, 햄스터)")
	String petType;
	
	@ApiModelProperty(name="반려동물 품종")
	String petVariety;
	
	@ApiModelProperty(name="반려동물 생일 (YYYY-MM-DD)")
	String petBirth;
	
	@ApiModelProperty(name="반려동물 특이사항")
	String petInfo;
}
