package com.qskx.quartz.dao;

import com.qskx.quartz.entity.SheduleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SheduleUserDao {

    public List<SheduleUser> pageList(@Param("offset") int offset,
                                      @Param("pagesize") int pagesize,
                                      @Param("username") String username,
                                      @Param("permission") int permission);
    public int pageListCount(@Param("offset") int offset,
                             @Param("pagesize") int pagesize,
                             @Param("username") String username,
                             @Param("permission") int permission);

    public int add(SheduleUser xxlConfUser);

    public int update(SheduleUser xxlConfUser);

    public int delete(@Param("username") String username);

    public SheduleUser load(@Param("username") String username);

}
