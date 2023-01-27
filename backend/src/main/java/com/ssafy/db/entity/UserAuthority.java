package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_authority")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserAuthority {
	
	@Id
	@Column(name = "user_id")
    String id;
	
	@Column(name = "authority_name")
	String authorityName;
}
