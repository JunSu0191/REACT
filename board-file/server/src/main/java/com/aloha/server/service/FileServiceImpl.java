package com.aloha.server.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.server.dto.Files;
import com.aloha.server.mapper.FileMapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileMapper fileMapper;

    @Value("${upload.path}")
    private String uploadPath;  // 업로드 경로

    @Override
    public List<Files> list() throws Exception {
        return fileMapper.list();
    }

    @Override
    public Files select(int no) throws Exception {
        return fileMapper.select(no);
    }
    
    @Override
    public int insert(Files file) throws Exception {
        return fileMapper.insert(file);
    }

    
    @Override
    public int update(Files file) throws Exception {
        return fileMapper.update(file);
    }
    
    @Override
    public int delete(int no) throws Exception {
        return fileMapper.delete(no);
    }
    
    // 파일 업로드 처리 요청
    @Override
    public Files upload(Files file) throws Exception {
        Files uploadedFile = uploadFile(file, file.getFile());
        if (uploadedFile != null) {
            log.info("파일 업로드 성공");
        }
        return uploadedFile;
    }

    // 파일 업로드 2️⃣번 과정 ( 파일 목록에서 파일 하나하나 요청보낸거 여기서 처리)
    @Override
    public Files uploadFile(Files fileInfo, MultipartFile file) throws Exception{
        int result = 0;
        
        if (file.isEmpty()) {
            return null;
        }

        // Multipartfile에서 넘겨받은 파일정보 꺼내기
        String originName = file.getOriginalFilename(); // 파일 원본명
        long fileSize = file.getSize();                 // 파일 사이즈
        byte[] fileData = file.getBytes();              // 파일 데이터

        // 파일명 중복 방지
        String fileName = UUID.randomUUID().toString() + "_" + originName;
        // 파일 경로/파일명 세팅
        String filePath = uploadPath + "/" + fileName;

        // 파일 시스템에 복사 (업로드)
        File uploadFile = new File(uploadPath, fileName);   // 업로드 경로와 파일명을 넣어 java 파일 객체 만듦
        FileCopyUtils.copy(fileData, uploadFile);           // 파일데이터와 자바파일객체를 복사해서 업로드

        // DB에 파일 정보 등록
        Files uploadedFile = new Files();
        uploadedFile.setParentTable(fileInfo.getParentTable());
        uploadedFile.setParentNo(fileInfo.getParentNo());
        uploadedFile.setFileName(fileName);
        uploadedFile.setOriginName(originName);
        uploadedFile.setFilePath(filePath);
        uploadedFile.setFileSize(fileSize);
        uploadedFile.setFileCode(fileInfo.getFileCode());

        result = fileMapper.insert(uploadedFile);
        log.info("result: {}", result);

        return uploadedFile;
    }
    
    // 파일 업로드 1️⃣번 과정 (파일 목록을 가져와서 uploadFile()로 업로드 처리 요청 올림)
    @Override
    public List<Files> uploadFiles(Files fileInfo, List<MultipartFile> fileList) throws Exception {

        List<Files> uploadedFilelList = new ArrayList<>();
        
        for (MultipartFile file : fileList) {
            Files uploadedFile = uploadFile(fileInfo, file);
            uploadedFilelList.add(uploadedFile);
            log.info("업로드된 파일: {}", uploadedFile);
        }

        return uploadedFilelList;
    }

    @Override
    public List<Files> listByParent(Files file) throws Exception {
        List<Files> fileList = fileMapper.listByParent(file);
        return fileList;
    }

    @Override
    public int download(int no, HttpServletResponse response) throws Exception {
        Files file = fileMapper.select(no);
        if(file == null){
            // BAD_REQUEST : 400, 클라이언트의 요청이 잘못되었음을 알리는 상태코드
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return 0;
        }

        String filePath = file.getFilePath();       // 파일 경로
        String fileName = file.getFileName();       // 파일 이름

        // 파일 다운로드를 위한 헤더 세팅
        // - Content-Type           : application/octect-stream
        // - Content-Disposition    : attachment, filename="파일명.확장자"
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // 파일 다운로드
        File downloadFile = new File(filePath);
        FileInputStream fis = new FileInputStream(downloadFile);

        // 파일 출력
        ServletOutputStream sos = response.getOutputStream();

        // 다운로드
        FileCopyUtils.copy(fis,sos);

        // byte[] buffer = new byte[1024];             // 1024bytes : 1KB
        // int data;
        // while((data = fis.read(buffer)) != -1 ){    // 1KB 입력
        //     sos.write(buffer, 0, data);         // 1KB 출력 전송
        // }
        fis.close();
        sos.close();

        return 1;
    }

    
}