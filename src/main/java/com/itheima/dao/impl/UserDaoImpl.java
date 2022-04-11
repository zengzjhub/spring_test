package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.Role;
import com.itheima.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    public List<User> findAll() {
        //new BeanPropertyRowMapper<自定义封装类型A>(A.class)
        List<User> userList = jdbcTemplate.query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    public Long save(final User user) {
        //创建PreparedStatementCreator
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                //使用原始jdbc完成有个PreparedStatement的组建
                PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_user values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);//RETURN_GENERATED_KEYS返回生成主键
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };
        //创建keyHolder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        //将creator生成的主键封装进keyHolder中去
        jdbcTemplate.update(creator,keyHolder);
        //从keyHolder中获得生成的主键
        long userId = keyHolder.getKey().longValue();
        return userId; //返回当前保存用户的id 该id是数据库自动生成的
    }

    public void saveUserRoleRel(Long userId, Long[] roleIds) {
        //roleIds有多个，语句需要执行多次，对roleIds执行多次
        for (Long roleId : roleIds) {
            jdbcTemplate.update("insert into sys_user_role values(?,?)",userId,roleId);
        }
    }

    public void delUserRoleRel(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId=?",userId);
    }

    public void del(Long userId) {
        jdbcTemplate.update("delete from sys_user where id=?",userId);
    }

    public User findByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException {
        //queryForObject常用于聚合函数的统计，查询结果，将结果封装为对象
        User user = jdbcTemplate.queryForObject("select * from sys_user where username=? and password=?",new BeanPropertyRowMapper<User>(User.class),username,password);
        return user;
    }


}
