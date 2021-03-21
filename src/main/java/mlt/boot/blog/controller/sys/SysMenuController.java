package mlt.boot.blog.controller.sys;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mlt.boot.blog.entity.SysMenuEntity;
import mlt.boot.blog.mapper.SysMenuMapper;
import mlt.boot.blog.result.Result;

@RestController
@RequestMapping("/sys")
public class SysMenuController {
	
	@Autowired
	SysMenuMapper sysMenuMapper;
	
	/**
	 * 根据用户id加载对应菜单列表树
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/menus/user/{id}")
    public Result findMenuByUserId(@PathVariable("id") Integer id){
		List<SysMenuEntity> list = sysMenuMapper.findMenuByUid(id);//查询到所有菜单
		list.forEach(menu->{
			menu.setCode(menu.getName());
		});
		List<SysMenuEntity> parentMenu = list.stream().filter(menu->menu.getParentId().equals("0")).collect(Collectors.toList());//一级菜单
		parentMenu.forEach(parent->{
			List<SysMenuEntity> childs = list.stream().filter(menu->menu.getParentId().equals(parent.getId().toString())).collect(Collectors.toList());
			parent.setChildren(childs);
		});
        return Result.ok(parentMenu);
    }
	 
	/**
	 * 根据用户id加载对应权限
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/perms/user/{id}")
    public Result findPermsByUserId(@PathVariable("id") Integer id){
		List<SysMenuEntity> list = sysMenuMapper.findPermsByUid(id);//查询到所有权限
		List<String> perms = list.stream().map(SysMenuEntity::getPermission).collect(Collectors.toList());
        return Result.ok(perms);
     
    }
}
