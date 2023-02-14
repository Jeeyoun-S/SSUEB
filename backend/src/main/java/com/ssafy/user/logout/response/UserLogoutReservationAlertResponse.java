package com.ssafy.user.logout.response;

import com.ssafy.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * user 로그아웃 API ([POST] /api/user/logout) 요청에 대한 응답값 정의. - 로그아웃 시 가장 최신 예약 상담 조회
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("UserLogoutReservationAlertResponse")
public class UserLogoutReservationAlertResponse extends BaseResponseBody {
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	@ApiModelProperty(name = "예약 반려동물 이름")
	String reservationPetName; 
	
	@ApiModelProperty(name = "예약 반려동물 대분류")
	String reservationPetType; 
	
	@ApiModelProperty(name = "예약 상담 일")
	String reservationDate; 
	
	@ApiModelProperty(name = "상담을 진행할 전문가 이름")
	String reservationConsultName; 
	
	public static UserLogoutReservationAlertResponse of(Integer statusCode, String message, String response, String reservationPetName, String reservationPetType, String reservationDate, String reservationConsultName) {
		UserLogoutReservationAlertResponse res = new UserLogoutReservationAlertResponse();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setResponse(response);
		res.setReservationPetName(reservationPetName);
		res.setReservationPetType(reservationPetType);
		res.setReservationDate(reservationDate);
		res.setReservationConsultName(reservationConsultName);
		
		return res;
	}
}
