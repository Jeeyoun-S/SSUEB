package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * heart(좋아요) 테이블 모델 정의
 */
@Entity
@Table(name = "heart")
@Getter
@Setter
public class Heart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	
	@Column(name = "board_no")
    int boardNo;
	
	@Column(name = "user_id")
    String userId;
}
