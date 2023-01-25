package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_authority")
@Getter
@Setter
@AllArgsConstructor
public class UserAuthority {
	
	@Id
	@Column(name = "user_id")
    String userId;
	
	@Column(name = "authority_name")
	String authorityName;
}
