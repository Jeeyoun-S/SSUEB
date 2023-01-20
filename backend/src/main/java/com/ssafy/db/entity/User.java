package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * user 테이블 모델 정의
 * - skeleton-code에서는 BaseEntity를 상속받고 있었으나, user 테이블에 따라 상속 제거함 
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	//Timestamp userJoindate;
	
	@Column(name = "user_role")
	int userRole;
	
	@Column(name = "user_delete_flag")
	int userDeleteFlag;
	
	@Column(name = "user_token")
	String userToken;
	
	@Column(name = "user_alert_flag")
	int userAlertFlag;
	
	// for. add JWT - 사용자 계정 활성화 여부 판단  
	@Column(name = "user_activated")
	int userActivated;
	
	// for. add JWT - 인증 권한 테이블 
	@ManyToMany
	@JoinTable(
			name = "user_authority",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "autthority_name", referencedColumnName = "authority_name")})
	Set<Authority> authorities; 
}
