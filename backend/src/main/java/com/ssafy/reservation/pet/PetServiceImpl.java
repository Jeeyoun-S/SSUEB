package com.ssafy.reservation.pet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.reservation.pet.response.PetSummary;

@Service("PetService")
public class PetServiceImpl implements PetService {
	@Autowired
	PetRepo pRepo;

	@Override
	public List<PetSummary> findByUserId(String userId) throws SQLException {
		return pRepo.findByUserId(userId);
	}
	
	
	
	
}
