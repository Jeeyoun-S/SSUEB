package com.ssafy.user.info.consultant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoConsultantQueryRepository extends JpaRepository<Object, Object> {
	
	/**
	 * 전문가의 별점별 총 개수 조회하기
	 * @param id 전문가 아이디
	 * **/
	@Query("SELECT review_grade, COUNT(review_grade) FROM reservation WHERE consultant_id = :id AND reservation_finish = 1 GROUP BY review_grade")
	List<Star> findReviewGradeById(String id);
	
	/**
	 * 전문가의 총 확정 예약 횟수
	 * **/
	@Query("SELECT COUNT(*) FROM reservation WHERE consultant_id = :id")
	int findTotalConfirmById(String id);
	
	/**
	 * 전문가 예약 제안 횟수 전체 평균
	 * **/
	@Query("SELECT AVG(c.consultant_reservation_count) FROM consultant AS c INNER JOIN (SELECT id, user_delete_flag FROM user) AS u ON u.id = c.id WHERE u.user_delete_flag = 0;")
	double findAverageCnt();
	
	/**
	 * 전문가 예약 확정 횟수 전체 평균
	 * **/
	@Query("SELECT AVG(cnt) FROM (SELECT consultant_id, COUNT(consultant_id) AS cnt FROM reservation WHERE consultant_id IS NOT NULL GROUP BY consultant_id)")
	double findAverageConfirm();
	
	/**쿼리 작성중**/
	@Query("SELECT consultant_id, DATE_FORMAT(DATE_SUB(reservation_date, INTERVAL 1 MONTH), '%Y-%m') FROM reservation")
	double findAverage();
}
