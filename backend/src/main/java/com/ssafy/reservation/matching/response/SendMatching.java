package com.ssafy.reservation.matching.response;



import io.swagger.annotations.ApiModel;


@ApiModel("SendMatching")
public interface SendMatching {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
	String getReservation_date();
	String getUser_id();
	String getReservation_consult_content();
	
	int getMatching_no();
    int getMatching_cost();
    String getMatching_comment();
    
    String getPet_name();
    String getPet_image();
    String getPet_type();
	String getPet_variety();
	String getPet_birth();
	String getPet_info();
}

