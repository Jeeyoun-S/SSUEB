package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * consultant 테이블 모델 정의
 */
@Entity
@Table(name = "consultant")
@Getter
@Setter
public class Consultant {
	@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
	
	@Column(name = "consult_pet_type")
    String consultPetType;
	
	@Column(name = "consultant_intro")
    String consultantIntro;
	
	@Column(name = "consultant_rate")
    Double consultantRate;
	
	@Column(name = "consultant_profile")
	String consultantProfile;
	
	@Column(name = "consultant_license_number")
	String consultantLicenseNumber;
	
	@Column(name = "consultant_license_copy_image")
	int consultantLicenseCopyImage;
	
	@Column(name = "consultant_reservation_count")
	int consultantReservationCount;
}
