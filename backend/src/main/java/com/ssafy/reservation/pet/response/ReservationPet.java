package com.ssafy.reservation.pet.response;

import javax.persistence.Column;

import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;

import io.swagger.annotations.ApiModel;

//예약 정보에 대해 해당 예약 정보 + 펫 정보를 담은 response 클래스 -> 둘다 no이라는 pk가 있어서 그냥 통으로 가져오는게 안되서 이런 방식을 차용
@ApiModel("ReservationPet")
public interface ReservationPet {
	//자세한 내용(전체)가 아닌 보이는 내용만 보이게 하려고
	int getRno();
   	String getUserId();
   	String getConsultantId();
   	String getReservationDate();
   	String getReservationConsultContent();
   	
   	int getPno();
   	String getPetImage();
   	String getPetName();
	String getPetType();
	String getPetVariety();
	String getPetBirth();
	String getPetInfo();
}
