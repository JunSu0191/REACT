package com.aloha.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.server.dto.Board;
import com.aloha.server.dto.Files;
import com.aloha.server.service.BoardService;
import com.aloha.server.service.FileService;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/boards")
public class BoardController {
  
    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;
    
    @GetMapping()
    public ResponseEntity<?> list() {
        try {
            List<Board> boardList = boardService.list();
            log.info("boardList : " + boardList);
            return new ResponseEntity<>(boardList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{no}")
    public ResponseEntity<?> read(@PathVariable("no") int no ) {
        try {
            // 🎫 게시글
            Board board = boardService.select(no);
            // 📄 파일 목록
            Files file = new Files();
            file.setParentTable("board");
            file.setParentNo(no);
            List<Files> fileList = fileService.listByParent(file);

            Map<String, Object> response = new HashMap<>();
            response.put("board", board);
            response.put("fileList", fileList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    // public ResponseEntity<?> insert(@RequestBody Board board) {      // Content-Type : application/json
    public ResponseEntity<?> insert(Board board) {                      // Content-Type : mulipart/form-data
        log.info("개 시부랄 것");
        try {
            Board newBoard = boardService.insert(board);
            // log.info("----------------newBoard : " + newBoard);
            if (newBoard != null) {
                log.info("소주 한잔 하자 성공했다 ");
                return new ResponseEntity<>(newBoard, HttpStatus.OK);
            }
            else {
                log.info("소주 한잔 하자 엘스다 ");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        } catch (Exception e) {
            log.error(null, e);
            log.info("소주 한잔 하자 시발련");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Board board) {
        try {
            int result = boardService.update(board);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{no}")
    public ResponseEntity<?> delete(@PathVariable("no") int no) {
        try {
            int result = boardService.delete(no);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
