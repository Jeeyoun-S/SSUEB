package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * like 테이블 모델 정의
 */
@Entity
@Table(name = "heart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Heart {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	
	@Column(name = "board_no")
    int boardNo;
	
	@Column(name = "user_id")
    String userId;
}
