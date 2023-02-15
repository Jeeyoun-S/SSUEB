package com.ssafy.reservation.pet;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.db.entity.Pet;
import com.ssafy.reservation.pet.response.PetSummary;

public interface PetRepo extends JpaRepository<Pet, Integer> {
	List<PetSummary> findByUserId(String userId);
}
