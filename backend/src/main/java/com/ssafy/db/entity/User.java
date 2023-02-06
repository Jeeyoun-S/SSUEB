package com.ssafy.db.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * user 테이블 모델 정의 
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@DynamicInsert
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

	@Column(name = "user_delete_flag")
	int userDeleteFlag;
	
	@Column(name = "user_alert_flag")
	int userAlertFlag;
	
	// for. add JWT - 사용자 계정 활성화 여부 판단  
	@Column(name = "user_activated")
	int userActivated;
	
	@Column(name = "user_is_social_id")
	int userIsSocialId;
	
	// for. add JWT - 인증 권한 테이블 
//	@ManyToMany
//	@JoinTable(
//			name = "user_authority",
//			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
//			inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
//	Set<Authority> authorities;
	@ManyToMany
	@JoinTable(
			name = "user_authority",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	
	Set<Authority> authorities;
}
