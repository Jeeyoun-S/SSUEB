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
@Table(name="attach")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Attach{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "PK, AI")
    int no;
	@Column(name="reservation_no")
	@ApiModelProperty(value = "이 파일과 관련된 상담 번호")
    int reservationNo;
	@ApiModelProperty(value = "이 파일의 경로")
	@Column(name="attach_file")
    String attachFile;
}
