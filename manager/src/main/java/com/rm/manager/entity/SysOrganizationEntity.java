package com.rm.manager.entity;

import java.io.Serializable;
import com.rm.common.jooq.BasicEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *	Created by admin on Wed Jun 02 17:06:26 CST 2021
 *	系统组织机构表
 */
@Data
public class SysOrganizationEntity extends BasicEntity implements Serializable{

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
	 *	组织名称
	 */
	private String name;

	/**
	 *	父id
	 */
	private String pid;

	/**
	 *	全id
	 */
	private Integer fullId;

	/**
	 *	机构描述信息
	 */
	private String remark;

	/**
	 *	位置信息
	 */
	private String location;

}

