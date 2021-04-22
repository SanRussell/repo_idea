package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {

        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());

        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);

        PageInfo<User> pageInfo = new PageInfo<>(allUserByPage);

        return pageInfo;
    }

    @Override
    public User login(User user) throws Exception {

        //  调用mapper
        User user2 = userMapper.login(user);
        if(user2 != null && Md5.verify(user.getPassword(),"lagou",user2.getPassword())) {
            return user2;
        } else {
            return null;
        }
    }

    /*
        分配角色回显
     */
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {

        List<Role> list = userMapper.findUserRelationRoleById(id);

        return list;
    }

    @Override
    public void userContextRole(UserVo userVo) {

        //  根据用户ID清空关联表
        userMapper.deleteUserContextRole(userVo.getUserId());


        //  建立新关联系关系
        for (Integer roleId : userVo.getRoleIdList()) {

            //  封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVo.getUserId());
            user_role_relation.setRoleId(roleId);

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }

    }

    /*
       获取用户权限 进行动态展示
    */
    @Override
    public ResponseResult getUserPermissions(Integer userid) {
        //userid可以打印出来吗
        //  1. 获取当前用户拥有的角色(此返回结果包含所有信息)
        List<Role> roleList = userMapper.findUserRelationRoleById(userid);

        //  2. 获取角色ID保存到List集合中
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
            System.out.println("测试：" + roleIds);
        }

        //  3.根据角色ID查询父级菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(roleIds);

        //  4.查询父级菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenu);
        }

        //  5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        //  6.封装数据并返回
        Map<String, Object> map = new HashMap<>();

        map.put("menuList",parentMenu);
        map.put("resourceList",resourceList);


        return new ResponseResult(true,200,"获取用户权限信息成功!",map);
    }
}
