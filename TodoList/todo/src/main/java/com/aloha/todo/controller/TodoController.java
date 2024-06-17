package com.aloha.todo.controller;

import java.util.List;

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

import com.aloha.todo.dto.Todo;
import com.aloha.todo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    
    @GetMapping()
    public ResponseEntity<?> TodoList() {
        try {
            List<Todo> todoList = todoService.list();
            return new ResponseEntity<>(todoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{no}")
    public ResponseEntity<?> read(@PathVariable("no") int no) {
        try {
            Todo todo = todoService.select(no);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> insert(@RequestBody Todo todo) {
        try {
            Todo newTodo = todoService.insert(todo);
            if ( newTodo != null) 
                return new ResponseEntity<>(newTodo, HttpStatus.OK);
            else
                return new ResponseEntity<>("FAIL", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Todo todo) throws Exception {
            int result = 0;
            
            log.info("todo : " + todo);
            try {
                // 전체 완료
                if( todo.getNo() == -1 ) {
                    result = todoService.completeAll();
                }
                else {
                    result = todoService.update(todo);
                }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{no}")
    public ResponseEntity<?> delete(@PathVariable("no") int no) {
        log.info("todo 삭제: " + no);
        try {
            int result = 0;
            // 전체 삭제
            if( no == -1 ) {
                result = todoService.allDelete();
            } else {
                result = todoService.delete(no);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
