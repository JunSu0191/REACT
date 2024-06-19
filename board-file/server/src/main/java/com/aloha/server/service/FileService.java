package com.aloha.server.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aloha.server.dto.Files;

import jakarta.servlet.http.HttpServletResponse;

public interface FileService {

    public List<Files> list() throws Exception;
    
    public Files select(int no) throws Exception;

    public int insert(Files file) throws Exception;

    public int update(Files file) throws Exception;

    public int delete(int no) throws Exception;

    
    // 파일 
    public Files upload(Files file) throws Exception;

    // 파일 업로드
    public Files uploadFile(Files fileInfo, MultipartFile file) throws Exception;

    public List<Files> uploadFiles(Files fileInfo, List<MultipartFile> fileList) throws Exception;

    // 파일 목록 - 부모 기준
    public List<Files> listByParent(Files file) throws Exception;

    // 파일 다운로드
    public int download(int no, HttpServletResponse response) throws Exception;

    // 파일 선택 삭제
    public int deleteFiles(String nos) throws Exception;

    // 파일 목록 삭제 - 부모 테이블 기준
    public int deleteByParent(Files file) throws Exception;
}