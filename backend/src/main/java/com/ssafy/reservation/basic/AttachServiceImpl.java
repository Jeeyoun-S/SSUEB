package com.ssafy.reservation.basic;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.ssafy.db.entity.Attach;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.ImageFormats;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.ImageException;
import io.github.techgnious.exception.VideoException;

@Service
public class AttachServiceImpl implements AttachService {
	@Autowired
	AttachRepo aRepo;
	
	@Value("${file.image.path.attachImage}")
	String imagePath;
	
	@Value("${file.image.path.attachVideo}")
	String videoPath;

	@Override
	public List<Attach> readByReservationNo(int reservationNo) throws SQLException {
		return aRepo.findByReservationNo(reservationNo);
	}

	@Override
	public void uploadFiles(List<MultipartFile> files, int reservationNo) throws IOException, ImageException, VideoException {
		for(MultipartFile file : files) {
			IVCompressor compressor=new IVCompressor();
			String uuid = UUID.randomUUID().toString();//랜덤한 코드명 ex)49eec5bf-dce3-43b2-8ff8-c041c792ed0a를 넣어준다
			String savefileName = uuid + "_" + file.getOriginalFilename();
			
			if(file.getContentType().startsWith("image")) {
				System.out.println(file.getContentType());
				IVSize customRes=new IVSize();
				//커스텀 크기 설정
				customRes.setWidth(400);
				customRes.setHeight(400);
				String path = imagePath+savefileName;				
				//설정한 값으로 byte저장 -> return값이 byte[]라서 Files.write(byte[], 경로)로 저장
				byte[] resizeFile = compressor.resizeImageWithCustomRes(file.getBytes(), ImageFormats.JPEG, customRes);
				Files.write(resizeFile, new File(path));
				aRepo.save(new Attach(0,reservationNo,savefileName));
				//저장한 byte[]값을 저장 -> R720P 480P등설정 가능 -> 파일이나 byte가지고 바로 저장 가능
				//compressor.resizeAndSaveImageToAPath(file.getBytes(), savefileName, ImageFormats.JPEG, "C:/Temp", ResizeResolution.SMALL_THUMBNAIL);
			}
			else if(file.getContentType().startsWith("video")) {
				compressor.reduceVideoSizeAndSaveToAPath(file.getBytes(), savefileName, VideoFormats.MP4, ResizeResolution.R480P, videoPath);
				aRepo.save(new Attach(0,reservationNo,savefileName));
			}
			else {//그 외 파일 거름망 -> 어차피 front에서 할 거긴 한데
				
			}
		}	
	}
	
	
}
