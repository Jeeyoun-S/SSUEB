package com.ssafy.user.phone.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Message {
	
	@ApiModelProperty(value = "수신번호", required = true)
	String to;
	
	@ApiModelProperty(value = "개별 메시지 제목")
	String subject;
	
	@ApiModelProperty(value = "개별 메시지 내용")
	String content;

	public Message(String to) {
		this.to = to;
	}
	
}
