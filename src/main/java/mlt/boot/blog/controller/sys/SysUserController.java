package mlt.boot.blog.controller.sys;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import cn.hutool.core.collection.CollUtil;
import mlt.boot.blog.entity.SysMenuEntity;
import mlt.boot.blog.mapper.SysMenuMapper;
import mlt.boot.blog.mapper.SysUserMapper;
import mlt.boot.blog.result.Result;
import mlt.boot.blog.utils.UserUtil;



@RestController
@RequestMapping("sys")
public class SysUserController {

	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	SysMenuMapper sysMenuMapper;
	
	@GetMapping(value = "/users/info")
    public Result getUserInfo(HttpServletRequest request){
		String username = UserUtil.getUsername();
		
		Map<String, Object> rs = CollUtil.newHashMap();
  
		List<SysMenuEntity> findMenuByUid = sysMenuMapper.findMenuByUid(1);
        rs.put("name", username);
        rs.put("menus", findMenuByUid);

        return Result.ok(rs);
    }
}
