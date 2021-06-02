package com.rm.manager.entity;

import java.io.Serializable;
import com.rm.common.jooq.BasicEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *	Created by admin on Wed Jun 02 17:06:26 CST 2021
 *	系统权限表(菜单、按钮)
 */
@Data
public class SysMenuEntity extends BasicEntity implements Serializable{

	/**
	 *	
	 */
	private String id;

	/**
	 *	创建时间
	 */
	private LocalDateTime createDate;

	/**
	 *	修改时间
	 */
	private LocalDateTime updateDate;

	/**
	 *	菜单权限名称
	 */
	private String name;

	/**
	 *	菜单跳转的route
	 */
	private String path;

	/**
	 *	父级id
	 */
	private String pid;

	/**
	 *	0-菜单 1-按钮
	 */
	private String type;

	/**
	 *	权限标识符
	 */
	private String code;

}

