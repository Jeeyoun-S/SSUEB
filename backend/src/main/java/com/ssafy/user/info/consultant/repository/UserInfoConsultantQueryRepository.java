package com.ssafy.user.info.consultant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.db.entity.Consultant;

public interface UserInfoConsultantQueryRepository extends JpaRepository<Consultant, String> {
	
	/**
	 * 전문가의 별점별 총 개수 조회하기
	 * @param id 전문가 아이디
	 * **/
	@Query(value = "SELECT review_grade AS starNum, COUNT(review_grade) AS starCnt FROM reservation WHERE consultant_id = :id AND reservation_finish = 1 GROUP BY review_grade", nativeQuery = true)
	List<StarMapping> findReviewGradeById(@Param("id") String id);
	
	/**
	 * 전문가의 총 확정 예약 횟수
	 * **/
	@Query(value = "SELECT COUNT(*) FROM reservation WHERE consultant_id = :id", nativeQuery = true)
	Optional<Integer> findTotalConfirmById(@Param("id") String id);
	
	/**
	 * 전문가 예약 제안 횟수 전체 평균
	 * **/
	@Query(value = "SELECT AVG(c.consultant_reservation_count) FROM consultant AS c INNER JOIN (SELECT id, user_delete_flag FROM user) AS u ON u.id = c.id WHERE u.user_delete_flag = 0", nativeQuery = true)
	Optional<Double> findAverageCnt();
	
	/**
	 * 전문가 예약 확정 횟수 전체 평균
	 * **/
	@Query(value = "SELECT AVG(cnt) FROM (SELECT consultant_id, COUNT(consultant_id) AS cnt FROM reservation WHERE consultant_id IS NOT NULL GROUP BY consultant_id) AS a", nativeQuery = true)
	Optional<Double> findAverageConfirm();
	
	/**
	 * 그래프 데이터
	 * @param beforeMonth 6개월 전 YYYY-MM
	 * @param nowMonth 현재 YYYY-MM
	 * **/
	@Query(value = "SELECT DATE_FORMAT(DATE_SUB(reservation_date, INTERVAL 1 MONTH), '%Y-%m') AS reservationDate, COUNT(review_grade) AS total, AVG(review_grade) AS grade FROM reservation WHERE consultant_id = :id AND reservation_finish = 1 AND DATE_FORMAT(reservation_date, '%Y-%m') BETWEEN :beforeMonth AND :nowMonth GROUP BY reservationDate", nativeQuery = true)
	List<GraphMapping> findAverage(@Param("id") String id, @Param("beforeMonth") String beforeMonth, @Param("nowMonth") String nowMonth);
}
