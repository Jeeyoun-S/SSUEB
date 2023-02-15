package com.ssafy.reservation.matching.response;



import io.swagger.annotations.ApiModel;


@ApiModel("ConsultantInfo")
public interface ConsultantInfo {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
    String getConsultant_name();
    String getConsultant_intro();
    Double getConsultant_rate();
    String getConsultant_profile();
}

