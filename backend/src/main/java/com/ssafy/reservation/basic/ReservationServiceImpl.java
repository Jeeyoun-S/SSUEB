package com.ssafy.reservation.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Attach;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.pet.response.ReservationPet;
import com.ssafy.reservation.pet.response.ReservationPetFinish;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationRepo repo;
	
	@Autowired
	AttachRepo aRepo;

	@Override
	public Reservation createReservation(Reservation reservation) throws SQLException {
		return repo.save(reservation);
	}
	
	@Override
	public Reservation readReservation(int no) throws SQLException {
		return repo.findById(no).get();
	}
	
	@Override
	public Attach createAttach(int reservationNo, String attachFile) throws SQLException {
		return aRepo.save(new Attach(0, reservationNo, attachFile));
	}

	@Override
	public void deleteReservation(int no) throws SQLException {
		repo.deleteById(no);
	}


	@Override
	public void finishReservation(int no) throws SQLException {
		Reservation reserve = repo.findById(no).get();
		reserve.setReservationFinish(1);
		repo.save(reserve);
	}
	
	@Override
	public void writeDignosis(int no, String reservationDignosisRecord) throws SQLException {
		Reservation reserve = repo.findById(no).get();
		reserve.setReservationDignosisRecord(reservationDignosisRecord);
		repo.save(reserve);
	}
	
	@Override
	public void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen) throws SQLException {
		Reservation reserve = repo.findById(no).get();
		reserve.setReviewComment(reviewComment);
		reserve.setReviewGrade(reviewGrade);
		reserve.setReservationOpen(reservationOpen);
		repo.save(reserve);
	}
	
	@Override
	public List<ReservationPet> getPartnerConfirmedReservation(String userId) throws SQLException {
		return repo.findPartnerConfirmedReservation(userId);//확정되었지만 예약이 끝나지 않은 예약 목록이니까 0을 줌. 밑도 마찬가지
	}

	@Override
	public List<ReservationPet> getConsultantConfirmedReservation(String consultantId) throws SQLException {
		return repo.findConsultantConfirmedReservation(consultantId);
	}

	@Override
	public List<ReservationPet> getPartnerUnconfirmedReservation(String userId) throws SQLException {
		return repo.findPartnerUnconfirmedReservation(userId);//확정도 안났으니 당연히 reservationfinish는 다 0이라 필요없음.
	}

	@Override
	public List<ReservationPet> getAllUnconfirmedReservation() throws SQLException {
		return repo.findAllUnconfirmedReservation();//확정안난애들 목록
	}
	
	@Override
	public List<ReservationPetFinish> getOpenReservation() throws SQLException {
		return repo.findByReservationOpen(1);//오픈된 리저베이션 목록 가져오기
	}

	@Override
	public List<ReservationPetFinish> getPartnerPastReservation(String userId) throws SQLException {
		return repo.findByUserIdAndReservationFinish(userId);//상담이 끝난 애들
	}

	@Override
	public List<ReservationPetFinish> getConsultantPastReservation(String consultantId) throws SQLException {
		return repo.findConsultantPastReservation(consultantId);//잠시 지워두기
	}

	@Override
	public List<String> getDateValidation(String userId) throws SQLException {
		return repo.readDateValidation(userId);
	}
}
