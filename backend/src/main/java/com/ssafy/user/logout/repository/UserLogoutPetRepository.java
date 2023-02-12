package com.ssafy.user.logout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.db.entity.Pet;

@Repository
public interface UserLogoutPetRepository extends JpaRepository<Pet, Integer>{
	
	/** 
	 * id에 해당되는 가장 최근 예약의 반려동물 정보 조회
	 * @param reservationPetNo 조회할 반려동물 no
	 */
	Pet findByNo(int reservationPetNo);
//	@Transactional
//    @Query(value = "select * from pet where no = ?1", nativeQuery = true)
//	Pet getRecentlyReservationPet(int reservationPetNo);
}
