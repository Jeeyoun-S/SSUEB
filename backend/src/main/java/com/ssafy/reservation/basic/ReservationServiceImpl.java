package com.ssafy.reservation.basic;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Reservation;

@Service
public class ReservationServiceImpl implements ReservationService {
	@Autowired
	ReservationRepo repo;

	@Override
	public Reservation createReservation(Reservation reservation) throws SQLException {
		return repo.save(reservation);
	}
	
	@Override
	public Reservation readReservation(int no) throws SQLException {
		return repo.findById(no).get();
	}

	@Override
	public void deleteReservation(int no) throws SQLException {
		repo.deleteById(no);
	}


	@Override
	public void finishReservation(int no) throws SQLException {
		repo.finishReservation(no);
	}
	
	@Override
	public void writeDignosis(int no, String reservationDignosisRecord) throws SQLException {
		repo.writeDignosis(no, reservationDignosisRecord);
	}
	
	@Override
	public void writeReview(int no, String reviewComment, int reviewGrade, int reservationOpen) throws SQLException {
		repo.writeReview(no, reviewComment, reviewGrade, reservationOpen);
	}
	
	@Override
	public List<Reservation> getPartnerConfirmedReservation(String userId) throws SQLException {
		return repo.findByUserIdAndConsultantIdIsNotNullAndReservationFinish(userId,0);//확정되었지만 예약이 끝나지 않은 예약 목록이니까 0을 줌. 밑도 마찬가지
	}

	@Override
	public List<Reservation> getConsultantConfirmedReservation(String consultantId) throws SQLException {
		return repo.findByConsultantIdAndReservationFinish(consultantId, 0);
	}

	@Override
	public List<Reservation> getPartnerUnconfirmedReservation(String userId) throws SQLException {
		return repo.findByUserIdAndConsultantIdIsNull(userId);//확정도 안났으니 당연히 reservationfinish는 다 0이라 필요없음.
	}

	@Override
	public List<Reservation> getAllUnconfirmedReservation() throws SQLException {
		return repo.findByConsultantIdIsNull();//확정안난애들 목록
	}

}
