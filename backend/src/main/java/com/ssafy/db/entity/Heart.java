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
 * heart(좋아요) 테이블 모델 정의
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
	@ApiModelProperty(value = "PK, AI")
    int no;
	
	@Column(name = "board_no")
	@ApiModelProperty(value = "좋아요를 누른 게시글 번호")
    int boardNo;
	
	@Column(name = "user_id")
	@ApiModelProperty(value = "유저 아이디")
    String userId;
}
