package com.ssafy.user.admin.model;

/**
 * user 관리자(admin) API ([POST] /api/user/admin) 요청에 대한 응답값 정의.
 * - user와 consultant 테이블의 join 데이터를 가져오기 위한 DTO
 */
public interface UserConsultantDto {
	
	String getId(); 
	
	String getUser_name(); 
	
	String getUser_phone(); 
	
	String getUser_joindate(); 
	
	String getConsultant_pet_type(); 
	
	String getConsultant_license_number(); 
	
	String getConsultant_license_copy_image();
	
}
