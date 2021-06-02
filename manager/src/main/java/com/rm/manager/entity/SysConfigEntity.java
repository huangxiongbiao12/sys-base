package com.rm.manager.entity;

import java.io.Serializable;
import com.rm.common.jooq.BasicEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *	Created by admin on Wed Jun 02 17:06:26 CST 2021
 *	系统配置表
 */
@Data
public class SysConfigEntity extends BasicEntity implements Serializable{

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
	 *	配置项
	 */
	private String key;

	/**
	 *	配置值
	 */
	private String value;

	/**
	 *	备注信息
	 */
	private String remark;

}

