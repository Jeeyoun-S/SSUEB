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

import io.swagger.annotations.ApiModelProperty;

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
	@ApiModelProperty(value = "PK, AI")
    int no;
	
	@Column(name = "board_no")
	@ApiModelProperty(value = "댓글을 단 게시글의 번호")
    int boardNo;
	
	@Column(name = "user_id")
	@ApiModelProperty(value = "댓글을 단 유저 아이디")
    String userId;
	
	@Column(name = "reply_content")
	@ApiModelProperty(value = "댓글 내용")
	String replyContent;
	
	@Column(name = "reply_writetime")
	@ApiModelProperty(value = "댓글을 적은 시간")
	String replyWritetime;
}
