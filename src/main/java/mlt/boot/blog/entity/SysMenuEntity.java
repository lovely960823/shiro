package mlt.boot.blog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
/**
 * 菜单表
 * @author ljw 2020年11月25日15:17:03
 *
 */
@Data
@Table(name="sys_menu")
public class SysMenuEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private String parentId;//父亲id
	
	private String name;//名字
	
	private String type;//1 菜单 2权限(按钮)
	
	private String permission; //对应操作权限
	
	private String icon;//图标
	
	private String sort; //排序
	
	private String path;//路由地址
	
	private String component;//组件路径
	
	private String hidden;//0 隐藏  1 显示
	
	private String createBy;//创建人
	
	private String updateBy;//修改人
	
	private String createTime;//创建时间
	
	private String updateTime;//修改时间
	
	private String code;
	
	@Transient
	private List<SysMenuEntity> children = new ArrayList<SysMenuEntity>();//加载树形表格使用
}
