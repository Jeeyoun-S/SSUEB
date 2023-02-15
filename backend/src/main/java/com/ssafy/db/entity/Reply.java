package com.ssafy.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * reply 테이블 모델 정의
 */
@Entity
@Table(name = "reply")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reply {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "PK, AI", dataType = "int", example="0")
    int no;
	
	@Column(name = "board_no")
	@ApiModelProperty(value = "댓글을 단 게시글의 번호", dataType = "int", example="0")
    int boardNo;
	
	@Column(name = "user_id")
	@ApiModelProperty(value = "댓글을 단 유저 아이디")
    String userId;
	
	@Column(name = "user_nickname")
	@ApiModelProperty(value = "댓글을 단 유저 닉네임, 전문가의 경우 이름")
    String userNickname;
	
	@Column(name = "reply_content")
	@ApiModelProperty(value = "댓글 내용")
	String replyContent;
	
	@Column(name = "reply_writetime")
	@ApiModelProperty(value = "댓글을 적은 시간")
	String replyWritetime;
}
