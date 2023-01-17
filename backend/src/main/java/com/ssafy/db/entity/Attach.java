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
@Table(name="attach")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attach{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	@Column(name="reservation_no")
    int reservationNo;
	@Column(name="attach_file")
    String attachFile;
}
