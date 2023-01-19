package com.ssafy.reservation.matching;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Matching;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Reservation/Matching"}, description = "매칭(견적 제안과 수락)관련 API")
@RestController
@RequestMapping("/api/reservation/matching")
public class MatchingController {
	
	@Autowired
	MatchingService mService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping()
	@ApiOperation(value = "예약 생성", notes = "입력한 정보에 따라 예약을 생성한다", response = Matching.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createMatching(@RequestBody Matching matching) {
		try {
			Matching res = null;
			//System.out.println(res);
			return new ResponseEntity<Matching>(res, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
