package com.itheima.service.impl;

import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
//实例化bean
@Service("userService")
public class UserServiceImpl implements UserService {
  //注入
    @Resource(name = "userDao")
    private UserDao userDao;
  //  public void setUserDao(UserDao userDao) {this.userDao = userDao;}
    @Resource(name = "roleDao")
    private RoleDao roleDao;
  //  public void setRoleDao(RoleDao roleDao) {this.roleDao = roleDao;}

    public List<User> list() {
        List<User> userList = userDao.findAll();
        //封装userList中的每一个User的roles数据
        for (User user : userList) {
            //获得user的id
            Long id = user.getId();
            //将id作为参数 查询当前userId对应的Role集合数据
            List<Role> roles = roleDao.findRoleByUserId(id);
            System.out.println("角色："+roles);
            user.setRoles(roles);
        }
        return userList;
    }

    public void save(User user, Long[] roleIds) {
        //第一步 向sys_user表中存储数据并返回主键userId
        Long userId = userDao.save(user);
        //第二步 向sys_user_role 关系表中存储多条数据
        userDao.saveUserRoleRel(userId,roleIds);
    }

    public void del(Long userId) {
        //1、删除sys_user_role关系表
        userDao.delUserRoleRel(userId);
        //2、删除sys_user表
        userDao.del(userId);
    }

    public User login(String username, String password) throws EmptyResultDataAccessException  {
        try {
            User user = userDao.findByUsernameAndPassword(username, password);
            return user;
        }catch(EmptyResultDataAccessException e1){
            return null;
        }
    }


}
