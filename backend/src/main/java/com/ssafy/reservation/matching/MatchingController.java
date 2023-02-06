package com.ssafy.reservation.matching;

import java.util.List;

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

import com.ssafy.db.entity.Matching;
import com.ssafy.reservation.matching.request.MatchingConfirm;
import com.ssafy.reservation.matching.response.MatchingConsultant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Reservation/Matching"}, description = "매칭(견적 제안과 수락)관련 API")
@RestController
@RequestMapping("/api/reservation/matching")
@CrossOrigin("*")
public class MatchingController {
	
	@Autowired
	MatchingService mService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping()
	@ApiOperation(value = "견적 제안 생성", notes = "입력한 정보에 따라 견적 제안을 생성한다", response = Matching.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createMatching(@RequestBody Matching matching) {
		try {
			Matching result = mService.createMatching(matching);
			return new ResponseEntity<Matching>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "견적 삭제", notes = "해당 no에 해당하는 견적을 삭제한다.", response = Void.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "삭제하고자 하는 견적의 번호", dataType = "int", example = "0", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> deleteReservation(@PathVariable int no) {
		try {
			mService.deleteMatching(no);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@GetMapping("/consultant/{consultantId}")
	@ApiOperation(value = "해당 전문가의 견적 내역", notes = "해당 전문가가 제안한 견적들의 목록을 쭉 불러온다.", response = Matching.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "consultantId", value = "자신이 제안한 견적들을 보고싶은 전문가의 Id", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readSendMatching(@PathVariable String consultantId) {
		try {
			List<Matching> result = mService.readSendMatching(consultantId);
			//System.out.println(result);
			return new ResponseEntity<List<Matching>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/partner/{reservationNo}")
	@ApiOperation(value = "해당 예약에 대한 견적 정보", notes = "유저의 특정 예약에 대해 들어온 견적들에 대한 정보를 쭉 불러온다", response = MatchingConsultant.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "reservationNo", value = "해당 번호의 상담 예약에 들어온 견적들을 리턴한다", dataType = "int", example = "0", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readReceiveMatching(@PathVariable int reservationNo) {
		try {
			List<MatchingConsultant> result = mService.readReceiveMatching(reservationNo);
			return new ResponseEntity<List<MatchingConsultant>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/confirm")
	@ApiOperation(value = "예약 매칭 확정", notes = "유저와 전문가의 예약을 확정하고 예약테이블에 내용을 갱신한 후 그 예약과 관련된 견적들을 전부 삭제한다.", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> confirmMatching(@RequestBody MatchingConfirm matchingConfirm) {
		try {
			//System.out.println(matchingConfirm);
			mService.confirmMatching(matchingConfirm.getReservationNo(), matchingConfirm.getConsultantId(), matchingConfirm.getMatchingCost());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
