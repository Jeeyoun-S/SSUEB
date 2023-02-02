package com.ssafy.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import io.github.techgnious.IVCompressor;

public class ImageFile {
	
	// IVCompressor 인스턴스 생성
	IVCompressor compressor = new IVCompressor();
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	/**
	 * 300X300으로 이미지 저장하기
	 * @param imageFile 압축할 이미지 파일
	 * @return 저장 결과
	 * **/
	public boolean saveImage300(MultipartFile imageFile, String filename, String path) {

		try {
			// 이미지 읽기
			BufferedImage inputImage = ImageIO.read(imageFile.getInputStream());
			
			// 원본 가로 세로 길이
			int oldWidth = inputImage.getWidth();
			int oldHeight = inputImage.getHeight();
			
			// 변경 후 길이
			int newWidth = Math.min(oldWidth, oldHeight);
			int newHeight = newWidth;
			
			// 이미지 Crop (더 짧은 변 1:1 비율, 중심 기준)
			// param 1) BufferedImage 변환할 이미지 파일  2) int crop할 떄 중심 x 3) int crop할 때 중심 y 4) int crop 후 너비 5) int crop 후 높이
			BufferedImage imageCrop = Scalr.crop(inputImage, (oldWidth-newWidth)/2, (oldHeight-newHeight)/2, newWidth, newHeight);
			
			// 이미지 Resize (크기 300으로 조절)
			// param 1) BufferedImage 변환할 이미지 파일 2) int resize 후 너비 3) int resize 후 높이
			BufferedImage imageResize = Scalr.resize(imageCrop, 300, 300);
			
			// 확장자 가져오기
			String extension = filename.substring(filename.lastIndexOf(".") + 1);
			
			// 경로 설정해서 파일 객체 생성
			File file = new File("C:/Users/SSAFY/" + filename);
			
			// 저장하기
			boolean result = ImageIO.write(imageResize, extension.toUpperCase(), file);
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return false;
		
	}
	
	/**
	 * 파일 이름 생성하기
	 * @param originalName 원본 파일 이름
	 * **/
	public String makeFilename(String originalName) {
		
		// 확장자 추출
		String extension = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
		
		// 파일명 생성
		String imageName = UUID.randomUUID().toString() + extension;
		
		return imageName;
		
	}
	
	/**
	 * 확장자를 입력 받아 파일 이름 생성하기
	 * @param extension 파일 확장자
	 * **/
	public String makeFilnameWithExtension(String extension) {
		
		return UUID.randomUUID().toString() + extension.toLowerCase();
	}
	
	/**
	 * imageName을 파일명으로 파일 저장하기
	 * @param imageFile 저장할 파일
	 * @param imageName 파일 이름
	 * @param path 파일 경로
	 * **/
	public void saveImage(MultipartFile imageFile, String imageName, String path) {
		
		// File 객체 생성
		File target = new File(path, imageName);
		
		// 폴더로 옮겨주기
		try {
			imageFile.transferTo(target);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
