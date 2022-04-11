package com.itheima.service.impl;

import com.itheima.dao.RoleDao;
import com.itheima.domain.Role;
import com.itheima.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource(name = "roleDao")
    private RoleDao roleDao;
//    public void setRoleDao(RoleDao roleDao) {
//        this.roleDao = roleDao;
//    }

    public List<Role> list() {
        List<Role> roleList = roleDao.findAll();
       // System.out.println(roleList);
        return roleList;
    }

    public void save(Role role) {
        roleDao.save(role);
    }

    public void del(Long roleId){
        roleDao.del(roleId);

    }
}
