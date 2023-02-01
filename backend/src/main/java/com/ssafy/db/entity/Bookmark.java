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
 * bookmark 테이블 모델 정의
 */
@Entity
@Table(name = "bookmark")
@Getter
@Setter
public class Bookmark {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int no;
	
	@Column(name = "user_id")
    String userId;
	
	@Column(name = "consulant_id")
    String consulantId;
}
