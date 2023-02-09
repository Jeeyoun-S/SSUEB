package com.ssafy.reservation.basic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.pet.response.ReservationPet;
import com.ssafy.reservation.pet.response.ReservationPetFinish;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Reservation/Basic/Consultant"}, description = "전문가 예약 목록 관련 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/reservation/consultant")
@CrossOrigin("*")
public class ReservationConsultantController {
	
	@Autowired
	ReservationService reservationService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@GetMapping("/{consultantId}")
	@ApiOperation(value = "전문가 확정 예약 목록", notes = "해당 전문가의 확정된 예약 목록을 가져온다.", response = ReservationPet.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consultantId", value = "확정된 예약 목록을 가져오고 싶은 전문가의 아이디", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> getConsultantConfirmedReservation(@PathVariable String consultantId) {
		try {
			List<ReservationPet> result = reservationService.getConsultantConfirmedReservation(consultantId);
			//System.out.println(result);
			return new ResponseEntity<List<ReservationPet>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/unconfirmed")
	@ApiOperation(value = "전문가 미확정 예약 목록", notes = "전문가가 견적을 제안할 확정되지 않은 예약 목록을 가져온다.", response = ReservationPet.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> getConsultantUnconfirmedReservation() {
		try {
			List<ReservationPet> result = reservationService.getAllUnconfirmedReservation();
			//System.out.println(result);
			return new ResponseEntity<List<ReservationPet>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/past/{consultantId}")
	@ApiOperation(value = "전문가의 지난 상담 내역", notes = "해당 전문가의 예전에 상담한(이미 완료된) 내역을 불러온다.", response = ReservationPetFinish.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consultantId", value = "예전에 상담한 기록을 가져오고 싶은 전문가의 아이디", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> getConsultantPastReservation(@PathVariable String consultantId) {
		try {
			List<ReservationPetFinish> result = reservationService.getConsultantPastReservation(consultantId);
			return new ResponseEntity<List<ReservationPetFinish>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
}