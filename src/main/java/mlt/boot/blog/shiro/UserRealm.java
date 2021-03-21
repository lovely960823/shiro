package mlt.boot.blog.shiro;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;


import mlt.boot.blog.entity.SysMenuEntity;
import mlt.boot.blog.entity.SysUserEntity;
import mlt.boot.blog.mapper.SysMenuMapper;
import mlt.boot.blog.mapper.SysUserMapper;
import mlt.boot.blog.utils.JwtUtil;
import mlt.boot.blog.utils.RedisUtil;

public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private RedisUtil redisUtil;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();//获取到用户
		SysUserEntity dbUser = sysUserMapper.selectByPrimaryKey(user.getId());
		if(dbUser==null) {
			throw new AuthenticationException(" user is not exists!!! ");
		}
		List<SysMenuEntity> list = sysMenuMapper.findPermsByUid(dbUser.getId());//查询到所有权限
		List<String> perms = list.stream().map(SysMenuEntity::getPermission).collect(Collectors.toList());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		perms.forEach(str->{
	    	if(!StringUtils.isEmpty(str)){
	    		info.addStringPermission(str);
	    	}
	    });
		if(user.getUsername().equals("ljw")){
		    info.addStringPermission("*:*");
		    info.addRole("ljw");
		}
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		JwtToken jwtToken = (JwtToken)authenticationToken;
		String token = (String) jwtToken.getPrincipal();
		//token是否存在
		if (StringUtils.isEmpty(token)||!token.startsWith("Bearer")) {
            throw new AuthenticationException(" token is empty ");
        }
		token = token.replace("Bearer ", "");
		//token是否有效
		String username = JwtUtil.getUsername(token);
		if(StringUtils.isEmpty(username)) {
			throw new AuthenticationException(" token is valid! ");
		}
		SysUserEntity user = sysUserMapper.findByUserName(username);
		if(user==null) {
			throw new AuthenticationException(" current User is not existed! ");
		}
		//验证token是否合法
		if(!JwtUtil.verify(token, username)) {
			throw new AuthenticationException(" token is valid! ");
		}
		//验证token 是否过期
        String redisToken = (String) redisUtil.get("LOGIN" + user.getId());
        if (StringUtils.isEmpty(redisToken)) {
            throw new AuthenticationException(" redisToken is empty or token incorrect ");
        }
        //刷新token
        redisUtil.expire("LOGIN" + user.getId(), 60 * 30);
		return new SimpleAuthenticationInfo(user,jwtToken.getPrincipal(),getName());
	}

}
