package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * pet 테이블 모델 정의
 */
@Entity
@Table(name = "pet")
@Getter
@Setter
public class Pet {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="no")
    int no;
	
	@Column(name = "user_id")
    String userId;
	
	@Column(name = "pet_image")
    String petImage;
	
	@Column(name = "pet_name")
    String petName;
	
	@Column(name = "pet_type")
	String petType;
	
	@Column(name = "pet_variety")
	String petVariety;
	
	@Column(name = "pet_birth")
	String petBirth;
	
	@Column(name = "pet_info")
	String petInfo;
	
	
}
