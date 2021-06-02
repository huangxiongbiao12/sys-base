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
public class SysUserEntity extends BasicEntity implements Serializable{

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
	 *	组织机构id
	 */
	private String orgId;

	/**
	 *	0-本级 1-本级及下级
	 */
	private String dataLevel;

	/**
	 *	姓名
	 */
	private String name;

	/**
	 *	头像
	 */
	private String avatar;

	/**
	 *	身份证
	 */
	private String idcard;

	/**
	 *	性别 0-男 1-女
	 */
	private String sex;

	/**
	 *	电话号码
	 */
	private String phone;

	/**
	 *	出生年月
	 */
	private String birthday;

	/**
	 *	密码
	 */
	private String password;

}

