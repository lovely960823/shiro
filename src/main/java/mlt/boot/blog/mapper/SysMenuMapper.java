package mlt.boot.blog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import mlt.boot.blog.entity.SysMenuEntity;
import tk.mybatis.mapper.common.Mapper;

public interface SysMenuMapper extends Mapper<SysMenuEntity>{
	

	/**
	 * 根据用户id查找对应菜单
	 * @param userId
	 * @return
	 */
	@Select(" select distinct m.* from sys_menu m inner join sys_role_menu rm on m.id=rm.menu_id inner join sys_role r on  r.id = rm.role_id inner join sys_user_role ur on ur.role_id = r.id inner join sys_user  u on u.id= ur.user_id where m.type='1' and u.id=#{userId} order by m.sort desc ")
	List<SysMenuEntity> findMenuByUid(@Param("userId")Integer userId);

	/**
	 * 根据用户id查找对应权限
	 * @param id
	 * @return
	 */
	@Select(" select distinct m.* from sys_menu m inner join sys_role_menu rm on m.id=rm.menu_id inner join sys_role r on  r.id = rm.role_id inner join sys_user_role ur on ur.role_id = r.id inner join sys_user  u on u.id= ur.user_id where m.type='2' and u.id=#{userId} order by m.sort desc ")
	List<SysMenuEntity> findPermsByUid(Integer id);

	

}
