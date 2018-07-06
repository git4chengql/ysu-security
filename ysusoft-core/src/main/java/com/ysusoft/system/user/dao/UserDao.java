package com.ysusoft.system.user.dao;

import com.ysusoft.system.user.SysUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by qlcheng on 2018/4/27.
 */
@Repository
public interface UserDao extends CrudRepository<SysUser,Long> {
    public SysUser findByUsername(String userName);
}
