package mlt.boot.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import mlt.boot.blog.entity.SysUserEntity;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUserEntity>{

	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	@Select(" select * from sys_user where username = #{username} and password=#{password} ")
	SysUserEntity findByUserNameAndPwd(@Param("username")String username,@Param("password")String password);

	/**
	 * 根据用户名查找该用户
	 * @param username
	 * @return
	 */
	@Select(" select * from sys_user where username = #{username} ")
	SysUserEntity findByUserName(@Param("username")String username);
	
	/**
	 * 根据openID查找该用户
	 * @param openid
	 * @return
	 */
	@Select(" select * from sys_user where openid = #{openid} ")
	SysUserEntity findByOpenid(@Param("openid")Object openid);

	/**
	 * 查询所有用户
	 * @return
	 */
	@Select(" select * from sys_user where account !='ljw' ")
	List<SysUserEntity> selectAllUser();

}
