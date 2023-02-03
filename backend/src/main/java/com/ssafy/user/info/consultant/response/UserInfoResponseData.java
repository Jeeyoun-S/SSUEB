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
	
	@ApiModelProperty(value = "전문가 평균 평점") //임시
	Double consultantRate;
	
	@ApiModelProperty(value = "전문가 0 평점") //임시
	int rateZero;
	
	@ApiModelProperty(value = "전문가 1 평점") //임시
	int rateOne;
	
	@ApiModelProperty(value = "전문가 2 평점") //임시
	int rateTwo;
	
	@ApiModelProperty(value = "전문가 3 평점") //임시
	int rateThree;
	
	@ApiModelProperty(value = "전문가 4 평점") //임시
	int rateFour;
	
	@ApiModelProperty(value = "전문가 5 평점") //임시
	int rateFive;
	
	@ApiModelProperty(value = "전문가 예약 제안 횟수") //
	int consultantReservationCount;
	
	@ApiModelProperty(value = "평균 예약 제안 횟수") //
	int reservationAverage;
	
	@ApiModelProperty(value = "전문가 예약 확정 횟수") //
	int confirmCount;
	
	@ApiModelProperty(value = "평균 예약 확정 횟수") //
	int confirmAverage;
	
	@ApiModelProperty(value = "예약 확정 수 최근 6개월")
	List<Map<String, Integer>> reservationNumber;
	
	@ApiModelProperty(value = "별점 최근 6개월")
	List<Map<String, Double>> starNumber;
}
