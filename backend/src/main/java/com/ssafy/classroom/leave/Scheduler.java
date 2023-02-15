package com.ssafy.classroom.leave;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.reservation.basic.ReservationRepo;

import io.openvidu.java.client.OpenVidu;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableAsync
public class Scheduler {
	
	@Autowired
	private RoomLeaveService roomLeaveService;
	
	@Async
	//spring expression으로 env파일과 연동필요.
	@Scheduled(fixedRate = 60000)
	//스케쥴 시작후 fixedRate 후에 다음스케쥴 실행
	public void leaveSession() {
		//redis와 연동하도록 확장필요.
//		log.info("scheduler....");
		
		//DB에서 가져온 정보로 openvidu session 종료.
		//openvidu session 종료
		roomLeaveService.leaveSessions();
		
		//시간지난 회의 종료.
		//DB 업데이트
		roomLeaveService.leaveRoom();
	
		
	}
}
