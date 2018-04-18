package com.chinasws.web.dao;

import com.chinasws.web.entity.Application;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationDao {
    int deleteByPrimaryKey(String id);

    int insert(Application record);

    int insertSelective(Application record);

    Application selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKey(Application record);

    Application selectByPid(String pid);
}