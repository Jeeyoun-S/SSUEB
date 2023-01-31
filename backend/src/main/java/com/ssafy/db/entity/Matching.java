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

import io.swagger.annotations.ApiModelProperty;

/**
 * 유저 모델 정의.
 */
@Entity
@Table(name="matching")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Matching{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "PK, AI")
    int no;
	@Column(name="reservation_no")
	@ApiModelProperty(value = "견적을 제안하고자 하는 예약의 번호")
    int reservationNo;
	@Column(name="consultant_id")
	@ApiModelProperty(value = "견적을 제안하는 전문가 아이디")
    String consultantId;
	@Column(name="matching_cost")
	@ApiModelProperty(value = "제안 가격")
    int matchingCost;
	@Column(name="matching_comment")
	@ApiModelProperty(value = "제안 가격의 이유나 자기어필 등")
    String matchingComment;
	@Column(name="reservation_date")
	@ApiModelProperty(value = "해당 예약의 시간")
    String reservationDate;
}
