package mlt.boot.blog.entity;

import java.io.Serializable;

import javax.persistence.Table;

import lombok.Data;
/**
 * 角色菜单表
 * @author ljw 时间
 *
 */
@Data
@Table(name="sys_role_menu")
public class SysRoleMenuEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer roleId; //角色id
	
	private Integer menuId; //菜单id
}
