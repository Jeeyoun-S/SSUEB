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

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	@Column(name="consultant_id")
    String consultantId;
	@Column(name="user_id")
    String userId;
	@Column(name="reservation_pet_no")
    int reservationPetNo;
	@Column(name="reservation_pet_type")
    String reservationPetType;
	@Column(name="reservation_date")
    Timestamp reservationDate;
	@Column(name="reservation_register_time")
    Timestamp reservationRegisterTime;
	@Column(name="reservation_consult_content")
    String reservationConsultContent;
	@Column(name="reservation_dignosis_record")
    String reservationDignosisRecord;
	@Column(name="reservation_open")
    int reservationOpen;
	@Column(name="reservation_finish")
    int reservationFinish;
	@Column(name="review_grage")
    int reviewGrade;
	@Column(name="review_comment")
    String reviewComment;
	@Column(name="reservation_cost")
    int reservationCost;
    
}
