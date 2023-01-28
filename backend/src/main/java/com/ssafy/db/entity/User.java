package com.ssafy.db.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.auth.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * user 테이블 모델 정의 
 */
@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@ToString
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
	
	// for. add JWT - 인증 권한 테이블 
	@ManyToMany
	@JoinTable(
			name = "user_authority",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	Set<Authority> authorities;
	
	// for. 회원가입 (UserJoinServiceImple에서 사용)
	public User() {
		
	}
	
	// for. OAuth
	private Role role;
	
	@Builder
	public User(String name, String email, Role role) {
		this.userName = name; 
		this.id = email; 
		this.role = role; 
	}

	public User update(String name) {
		this.userName = name;
		
		return this; 
	}
	
	public String getRoleKey() {
		return this.role.getKey(); 
	}
	// -------------------
}
