package com.ssafy.user.admin.response;

import java.util.List;

import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAllListResponse {
	
	@ApiModelProperty(value = "성공 또는 실패")
	String response;
	
	@ApiModelProperty(value = "메세지")
	String message;
	
	@ApiModelProperty(value = "유저 리스트")
	List<User> data;
}
