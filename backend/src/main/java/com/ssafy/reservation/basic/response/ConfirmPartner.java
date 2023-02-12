package com.ssafy.reservation.basic.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ssafy.reservation.matching.response.ConsultantInfo;
import com.ssafy.reservation.matching.response.MatchingConsultant;
import com.ssafy.reservation.pet.response.ReservationPet;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("ConfirmPartner")
public class ConfirmPartner {
	ReservationPet reservationPet;
	ConsultantInfo consultantInfo;
}

