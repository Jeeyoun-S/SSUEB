package com.ssafy.user.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.user.admin.model.UserConsultant;
import com.ssafy.user.admin.model.UserConsultantDto;

@Repository
public interface UserAdminRepository extends JpaRepository<Consultant, Integer>{
	
	/** 
	 * 전문가 회원 중 자격증 검증이 되지 않은 회원 조회
	 */
	@Transactional
	@Query(value = "select u.id, u.user_name, u.user_phone, u.user_joindate, c.consultant_pet_type, c.consultant_license_number, c.consultant_license_copy_image from user u inner join consultant c on u.id = c.id where c.consultant_certified = 0", nativeQuery = true)
	List<UserConsultantDto> getUncertifiedConsultantList();
	
}
