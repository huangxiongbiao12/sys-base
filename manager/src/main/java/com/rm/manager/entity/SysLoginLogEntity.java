package com.rm.manager.entity;

import java.io.Serializable;
import com.rm.common.jooq.BasicEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 *	Created by admin on Wed Jun 02 17:06:26 CST 2021
 *	用户表
 */
@Data
public class SysLoginLogEntity extends BasicEntity implements Serializable{

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
	 *	用户id
	 */
	private String userId;

	/**
	 *	电话号码
	 */
	private String phone;

	/**
	 *	系统信息
	 */
	private String sysInfo;

	/**
	 *	手机设备信息
	 */
	private String deviceInfo;

}

