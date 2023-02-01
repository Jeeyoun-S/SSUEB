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
 * board 테이블 모델 정의
 */
@Entity
@Table(name = "board")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "PK, AI")
    int no;
	
	@Column(name = "user_id")
	@ApiModelProperty(value = "유저 아이디")
    String userId;
	
	@Column(name = "user_nickname")
	@ApiModelProperty(value = "유저의 닉네임")
    String userNickname;
	
	@Column(name = "board_title")
	@ApiModelProperty(value = "게시글의 제목")
    String boardTitle;
	
	@Column(name = "board_content")
	@ApiModelProperty(value = "게시글의 내용")
	String boardContent;
	
	@Column(name = "board_writetime")
	@ApiModelProperty(value = "게시글이 작성된 시간 -> db저장시 알아서 들어감")
	String boardWritetime;
	
	@Column(name = "board_heartnum")
	@ApiModelProperty(value = "좋아요를 받은 횟수")
	int boardHeartnum;
	
	@Column(name = "board_views")
	@ApiModelProperty(value = "조회수")
	int boardViews;
	
	@Column(name = "board_flag")
	@ApiModelProperty(value = "0:어드민, 1:전문가, 2:유저 - 누가 작성한지")
	int boardFlag;
	
	@Column(name = "board_file")
	@ApiModelProperty(value = "게시글에 들어간 사진의 경로")
	String boardFile;
}
