package com.itheima.controller;

import com.itheima.domain.Role;
import com.itheima.domain.User;
import com.itheima.service.RoleService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
//  public void setRoleService(RoleService roleService){
//    this.roleService = roleService;
//  }

    @Resource(name = "userService")
    private UserService userService;

    @RequestMapping("/save")
    public String save(Role role){
        roleService.save(role);
        return "redirect:/role/list";
    }
    @RequestMapping("/del/{roleId}")
    public String del(@PathVariable("roleId") Long roleId){
        roleService.del(roleId);
        return "redirect:/role/list";
    }

    @RequestMapping("list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roleList = roleService.list();
        //设置模型
        modelAndView.addObject("roleList",roleList);
        //设置视图
        modelAndView.setViewName("role-list");
        System.out.println(roleList);
        return modelAndView;
    }

}
