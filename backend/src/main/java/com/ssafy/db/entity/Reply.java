package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * reply 테이블 모델 정의
 */
@Entity
@Table(name = "reply")
@Getter
@Setter
public class Reply {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	
	@Column(name = "board_no")
    int boardNo;
	
	@Column(name = "user_id")
    String userId;
	
	@Column(name = "reply_content")
	String replyContent;
	
	@Column(name = "reply_writetime")
	//String replyWritetime;
	Timestamp replyWritetime;
}
