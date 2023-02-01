package com.ssafy.user.auth.response;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.Authority;
import com.ssafy.db.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthResponse {
	
	@Id
    String id;
	
	@Column(name = "user_name")
    String userName;
	
	@Column(name = "user_nickname")
    String userNickname;
	
	@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "user_password")
    String userPassword;
	
	@Column(name = "user_phone")
	String userPhone;
	
	@Column(name = "user_joindate")
	String userJoindate;
	
	@Column(name = "user_delete_flag")
	int userDeleteFlag;
	
	@Column(name = "user_alert_flag")
	int userAlertFlag;
	
	@Column(name = "user_activated")
	int userActivated;
	
	private Set<Authority> authoritySet; 

	public static UserAuthResponse from(User user) {
		if(user == null) return null;

	      return UserAuthResponse.builder()
	    		  .id(user.getId())
	              .userName(user.getUserName())
	              .userNickname(user.getUserNickname())
	              .userPhone(user.getUserPhone())
	              .userJoindate(user.getUserJoindate())
	              .userDeleteFlag(user.getUserDeleteFlag())
	              .userAlertFlag(user.getUserAlertFlag())
	              .userActivated(user.getUserActivated())
	              .authoritySet(user.getAuthorities().stream()
	                      .map(authority -> Authority.builder().authorityName(authority.getAuthorityName()).build())
	                      .collect(Collectors.toSet()))
	              .build();
	}
}
