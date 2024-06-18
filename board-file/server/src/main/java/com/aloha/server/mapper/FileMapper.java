package com.aloha.server.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.server.dto.Files;

@Mapper
public interface FileMapper {

    public List<Files> list() throws Exception;

    public int insert(Files file) throws Exception;

    public Files select(int no) throws Exception;

    public int update(Files file) throws Exception;
 
    public int delete(int no) throws Exception;
    
}
