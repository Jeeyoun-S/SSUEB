package com.ssafy.reservation.basic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.db.entity.Attach;

import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;


public interface AttachService {
	List<Attach> readByReservationNo(int reservationNo) throws SQLException;
	
	void uploadFiles(List<MultipartFile> files, int reservationNo) throws IOException, ImageException, VideoException;
}
