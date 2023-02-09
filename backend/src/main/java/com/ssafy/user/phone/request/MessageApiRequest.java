package com.ssafy.user.phone.request;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageApiRequest {
	
	@ApiModelProperty(value = "SMS, LMS, MMS (소문자 가능)", required = true)
	String type;
	
	@ApiModelProperty(value = "COMM(일반, default), AD(광고)")
	String contentType;
	
	@ApiModelProperty(value = "국가번호 (default 82)")
	String countryCode;
	
	@ApiModelProperty(value = "발신번호", required = true)
	String from;
	
	@ApiModelProperty(value = "기본 메시지 내용", required = true)
	String content;
	
	@ApiModelProperty(value = "메시지 정보", required = true)
	List<Message> messages;
	
	@ApiModelProperty(value = "메시지 발송 예약 일시 (yyyy-MM-dd HH:mm)")
	String reserveTime;
	
	@ApiModelProperty(value = "예약 일시 타임존 (기본: Asia/Seoul)")
	String reserveTimeZome;
	
	@ApiModelProperty(value = "등록하려는 스케줄 코드")
	String scheduleCode;
}
