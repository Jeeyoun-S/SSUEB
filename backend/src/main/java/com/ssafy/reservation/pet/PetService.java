package com.ssafy.reservation.pet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.reservation.pet.response.PetSummary;

@Service
public interface PetService {
	
	//해당 유저의 펫에 대한 정보를 불러온다 -> 자신의 펫 정보들을 봐야할 때 사용
	List<PetSummary> findByUserId(String userId) throws SQLException;
	
	
}
