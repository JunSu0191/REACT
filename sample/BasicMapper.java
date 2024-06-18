package com.aloha.server.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BasicMapper {

    public List<> list() throws Exception;

    public int insert() throws Exception;

    public int select(int no) throws Exception;

    public int update() throws Exception;
 
    public int delete(int no) throws Exception;
    
}
