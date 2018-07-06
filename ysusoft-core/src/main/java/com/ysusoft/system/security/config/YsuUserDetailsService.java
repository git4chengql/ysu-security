package com.ysusoft.system.security.config;

import com.ysusoft.system.user.SysUser;
import com.ysusoft.system.user.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author chengql
 * @create 2018-01-02 下午4:53
 **/
@Component
public class YsuUserDetailsService implements UserDetailsService{
    Logger logger = LoggerFactory.getLogger(YsuUserDetailsService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * 备注:
     *   权限列表
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username="+username);
        SysUser sysUser = userDao.findByUsername(username);
        logger.info(String.valueOf(sysUser.getRoles()));
        String password = passwordEncoder.encode(sysUser.getPassword());
            return new User(username,password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList(sysUser.getUsername()));
    }
}
