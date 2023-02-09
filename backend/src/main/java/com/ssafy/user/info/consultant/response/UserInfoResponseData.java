package com.ssafy.user.info.consultant.response;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoResponseData {
	
	@ApiModelProperty(value = "전문가 아이디") //
	String id;
	
	@ApiModelProperty(value = "전문가 소개글 및 이력") //
	String consultantIntro;
	
	@ApiModelProperty(value = "전문가 사진") //
	String consultantProfile;
	
	@ApiModelProperty(value = "전문가 상담가능동물") //
	String consultantPetType;
	
	@ApiModelProperty(value = "전문가 이름") //
    String userName;
	
	@ApiModelProperty(value = "전문가 휴대폰 번호") //
    String userPhone;
	
	@ApiModelProperty(value = "전문가 알림방식선택") //
    int userAlertFlag;
	
	@ApiModelProperty(value = "전문가 평균 평점") //
	Double consultantRate;
	
	@ApiModelProperty(value = "전문가 평점") //
	List<Star> rate;
	
	@ApiModelProperty(value = "전문가 예약 제안 횟수") //
	int consultantReservationCount;
	
	@ApiModelProperty(value = "평균 예약 제안 횟수") //
	double reservationAverage;
	
	@ApiModelProperty(value = "전문가 예약 확정 횟수") //
	int confirmCount;
	
	@ApiModelProperty(value = "평균 예약 확정 횟수") //
	double confirmAverage;
	
	@ApiModelProperty(value = "예약 확정 수 최근 6개월")
	List<Map<String, Integer>> reservationNumber;
	
	@ApiModelProperty(value = "별점 최근 6개월")
	List<Map<String, Double>> starNumber;
}
