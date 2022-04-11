package com.itheima.dao.impl;

import com.itheima.dao.RoleDao;
import com.itheima.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
@Repository("roleDao")
public class RoleDaoImpl implements RoleDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    public List<Role> findAll() {
        List<Role> roleList = jdbcTemplate.query("select * from sys_role", new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }

    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role values(?,?,?)",null,role.getRoleName(),role.getRoleDesc());
    }

    public List<Role> findRoleByUserId(Long id) {
        //根据userId在sys_user_role表中查询对应的多个roleId，再对应其roleId名称
        List<Role> roles = jdbcTemplate.query("select * from sys_user_role ur,sys_role r where ur.roleId=r.id and ur.userId=?", new BeanPropertyRowMapper<Role>(Role.class), id);
        return roles;
    }
    public void del(Long roleId){jdbcTemplate.update("delete from sys_role where id=?",roleId);}
}
