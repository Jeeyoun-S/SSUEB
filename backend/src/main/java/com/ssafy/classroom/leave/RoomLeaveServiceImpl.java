package com.ssafy.classroom.leave;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.reservation.basic.ReservationRepo;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoomLeaveServiceImpl implements RoomLeaveService {

	@Autowired
	private ReservationRepo reservationRepositary;
	
	@Autowired
	private OpenVidu openvidu;
	
	@Transactional
	@Override
	public void leaveRoom() {
		reservationRepositary.offSafeMode();
		reservationRepositary.updateRooms();
		reservationRepositary.onSafeMode();
	}

	@Override
	public void leaveSessions() {
		Set<Integer> rooms= reservationRepositary.expiredRooms();
		for(int id : rooms) {
			//세션종료.
			try {
				Session session = openvidu.getActiveSession(id+"");
				if(session != null)
					session.close();
			} catch (OpenViduJavaClientException e) {
				e.printStackTrace();
			} catch (OpenViduHttpException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	

}
