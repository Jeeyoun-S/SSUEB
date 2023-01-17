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
    int no;
	@Column(name="reservation_no")
    int reservationNo;
	@Column(name="user_id")
    String userId;
	@Column(name="consultant_id")
    String consultantId;
	@Column(name="matching_cost")
    int matchingCost;//매칭 확정 후 reservation으로 옮겨지는 유일한 값
	@Column(name="matching_comment")
    String matchingComment;
}
