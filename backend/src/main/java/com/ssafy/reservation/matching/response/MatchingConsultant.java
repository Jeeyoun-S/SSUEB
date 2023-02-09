package com.ssafy.reservation.matching.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModel;


@ApiModel("MatchingConsultant")
public interface MatchingConsultant {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
	int getNo();
	int getReservation_no();
	int getMatching_cost();
	
	String getMatching_comment();
	String getReservation_date();
	String getConsultant_id();
    String getConsultant_name();
    String getConsultant_intro();
    Double getConsultant_rate();
    String getConsultant_profile();
}

