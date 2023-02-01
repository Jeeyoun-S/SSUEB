package com.ssafy.common.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.ImageFormats;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.exception.ImageException;

public class ImageFile {
	
	// IVCompressor 인스턴스 생성
	IVCompressor compressor = new IVCompressor();
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	/**
	 * IVCompressor를 이용한 이미지 압축 및 저장
	 * @param imageFile 압축할 이미지 파일
	 * **/
	public void compressorResizing(MultipartFile imageFile, String filename) {
		try {
			
			compressor.resizeAndSaveImageToAPath(imageFile.getBytes(), filename, ImageFormats.JPEG, "C:\\Users\\SSAFY", ResizeResolution.R480P);
			
		} catch (ImageException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 파일 이름 생성하기
	 * @param originalName 원본 파일 이름
	 * **/
	public String makeFilename(String originalName) {
		
		// 확장자 추출
		String extension = originalName.substring(originalName.lastIndexOf("."));
		
		// 파일명 생성
		String imageName = UUID.randomUUID().toString() + extension;
		
		return imageName;
		
	}
	
	/**
	 * 확장자를 입력 받아 파일 이름 생성하기
	 * @param extension 파일 확장자
	 * **/
	public String makeFilnameWithExtension(String extension) {
		
		return UUID.randomUUID().toString() + extension;
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
	
	/**
	 * 100KB 이하로 이미지 파일 저장하기
	 * @param imageFile 저장할 파일 
	 * **/
	public String saveMultipartFile100KB(MultipartFile imageFile, String path) {
		
		// 파일명 생성
		String imageName = makeFilename(imageFile.getOriginalFilename());
		
		// 이미지 크기 100KB 이상이면 Resizing
		if (!parameterCheck.isValidFileSize(100000, imageFile)) {
			imageName = makeFilnameWithExtension(".jpeg");
			compressorResizing(imageFile, imageName);
		}
		// 그 외에는 바로 저장
		else {
			imageName = makeFilename(imageFile.getOriginalFilename());
			saveImage(imageFile, imageName, path);
		}
		
		return imageName;
	}
}
