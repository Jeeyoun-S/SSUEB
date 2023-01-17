package com.ssafy.reservation.basic;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.service.ReservationService;
import com.ssafy.reservation.service.ReservationServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(value = "예약 API", tags = {"Reservation"})
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping()
	@ApiOperation(value = "예약 생성", notes = "입력한 정보에 따라 예약을 생성한다. 2023-01-17 16:50:31 \n"
			+ "no, finish, open, registertime은 공란으로 하면 알아서 default 값이 들어간다.", response = Integer.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
		try {
			int res = reservationService.createReservation(reservation);
			System.out.println(res);
			if(res == 1) {
				return new ResponseEntity<Integer>(res, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
