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
 * user 로그아웃 API ([POST] /api/user/logout) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("userLogoutPostResponse")
public class UserLogoutPostResponse extends BaseResponseBody {
	
	@ApiModelProperty(name = "예약 건 수", example = "3")
	int reservationCount; 
	
	@ApiModelProperty(name = "성공/실패 여부", example = "success / failure")
	String response; 
	
	public static UserLogoutPostResponse of(Integer statusCode, String message, String response, int reservationCount) {
		UserLogoutPostResponse res = new UserLogoutPostResponse();
		
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setResponse(response);
		res.setReservationCount(reservationCount);
		
		return res;
	}
}
