package com.ssafy.reservation.pet.response;

import io.swagger.annotations.ApiModel;


@ApiModel("PetSummary")
public interface PetSummary {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
    int getNo();
    String getPetName();
    String getPetImage();
}

