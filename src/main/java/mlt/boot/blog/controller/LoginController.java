package mlt.boot.blog.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import mlt.boot.blog.entity.SysUserEntity;
import mlt.boot.blog.mapper.SysUserMapper;
import mlt.boot.blog.result.Result;
import mlt.boot.blog.utils.JwtUtil;
import mlt.boot.blog.utils.RedisUtil;

@RestController
public class LoginController {
	
	@Autowired
	SysUserMapper sysUserMapper;
	
	@Autowired
	RedisUtil redisUtil;

	@PostMapping("/login")
	public Result userLogin(@RequestBody SysUserEntity user) {
		if(StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())) {
			return Result.fail("账号密码错误");
		}
		SysUserEntity dbUser = sysUserMapper.findByUserName(user.getUsername());
		if(dbUser==null) {
			return Result.fail("用户不存在");
		}
		String token = JwtUtil.createToken(dbUser.getUsername());
		redisUtil.set("LOGIN" + dbUser.getId(), token, 60*30);
		return Result.ok("Bearer "+token,"登录成功");
	}
	
	/**
	 * 不需要任何权限
	 * @return
	 */
	@GetMapping("/noPer")
	public Result noPer() {
		return Result.ok("可以直接访问");
	}
	
	@GetMapping("/per0")
	public Result per0() {
		return Result.ok("不需要访问权限");
	}
	
	
	/**
	 * 需要sys:user:per权限
	 * @return
	 */
	@GetMapping("/per1")
	@RequiresPermissions("sys:user:per")
	public Result per1() {
		return Result.ok("Per1访问成功");
	}
	
	/**
	 * 需要sys:user:list权限
	 * @return
	 */
	@GetMapping("/per2")
	@RequiresPermissions("sys:user:list")
	public Result per2() {
		return Result.ok("Per2访问成功");
	}
	
	
}
