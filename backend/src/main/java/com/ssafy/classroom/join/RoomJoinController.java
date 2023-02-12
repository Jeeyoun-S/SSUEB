package com.ssafy.classroom.join;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = {"Room"}, description = "회의실 입장  api")
@RestController
public class RoomJoinController {
	
	@Value("${OPENVIDU_URL}")
	private String OPENVIDU_URL;

	@Value("${OPENVIDU_SECRET}")
	private String OPENVIDU_SECRET;
	
	private OpenVidu openvidu;
	
	@PostConstruct
	public void init() {
		this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
	}
	
	
	@ApiOperation(value = "회의실 세션생성", notes = "회의실이 없다면 opennvidu server에 생성. 있다면 상태 업데이트."
			+ "intercetor 로 jwt token, 시간 유효성 검사를 완료.", response = String.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	@PostMapping("/api/room/session")
	ResponseEntity<?> joinRoom(@RequestBody(required = false) Map<String, Object> params) {
		log.info("roomjoincontroller ----------------------------------------------------");
		
		
		//redis 연결과  transaction 처리 필요.
		// id, sessionId, 시각으로처리.
		//redis { id , UUID, 시간 } 저장.
		//세션생성. 이미 있다면 업데이트.
		SessionProperties sessionProperties = SessionProperties.fromJson(params).build();
		log.info(params.toString());
		try {
			Session session = openvidu.createSession(sessionProperties);
			log.info(session.getSessionId());
			
			//connection 생성
			
			//간단하게 null값. 나중에 이중 json을 hash map에 넘길것.
			ConnectionProperties connectionProperties = ConnectionProperties.fromJson(null).build();
			
			Connection connection = session.createConnection(connectionProperties);
			//해쉬한후 redis에 저장. 어떻게든 redis사용하자.
			return new ResponseEntity<String>(connection.getToken(), HttpStatus.OK);
			
			
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new ResponseEntity<String>("session 생성 에러",HttpStatus.OK);


		}
		
	}
	
	@ApiOperation(value = "회의실 세션 커넥션 생성", notes = ""
			+ "intercetor 로 jwt token, 시간 유효성 검사.", response = String.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	@PostMapping("/api/room/connection")
	ResponseEntity<?> joinRoomConnection(@RequestBody(required = false) Map<String, Object> params){
		
		return new ResponseEntity<String>("testResult",HttpStatus.OK);
	}
	
	
	
	
	
}
