package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    int no;
	
	@Column(name = "user_id")
    String userId;
	
	@Column(name = "user_nickname")
    String userNickname;
	
	@Column(name = "board_title")
    String boardTitle;
	
	@Column(name = "board_content")
	String boardContent;
	
	@Column(name = "board_writetime")
	String boardWritetime;
	
	@Column(name = "board_heartnum")
	int boardHeartnum;
	
	@Column(name = "board_views")
	int boardViews;
	
	@Column(name = "board_flag")
	int boardFlag;
	
	@Column(name = "board_file")
	String boardFile;
}
