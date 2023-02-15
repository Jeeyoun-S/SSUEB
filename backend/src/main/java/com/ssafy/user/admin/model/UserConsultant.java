package com.ssafy.user.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserConsultant {

//	@Id
	String id;
	
//	@Column(name = "user_name")
    String user_name;
	
//	@Column(name = "user_phone")
	String user_phone;
	
//	@Column(name = "user_joindate")
	String user_joindate;
	
//	@Column(name = "consultant_pet_type")
    String consultant_pet_type;
	
//	@Column(name = "consultant_license_number")
	String consultant_license_number;
	
//	@Column(name = "consultant_license_copy_image")
	String consultant_license_copy_image;
	
}
