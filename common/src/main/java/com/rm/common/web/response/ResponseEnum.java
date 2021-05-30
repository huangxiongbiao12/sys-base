package com.rm.common.web.response;

import com.rm.common.web.exception.ResponseException;
import lombok.Getter;

@Getter
public enum ResponseEnum {
    //权限异常
    AUTH_ERROR(401, "权限校验失败"),

    //--------------- 公共异常 -------------------------
    SYSTEM_ERROR(500, "系统异常"),
    SYSTEM_OPER_ERROR(1000, "操作异常"),
    SYSTEM_ADD_ERROR(1001, "添加失败"),
    SYSTEM_UPDATE_ERROR(1002, "修改失败"),
    SYSTEM_DELETE_ERROR(1003, "删除失败"),
    SYSTEM_BEGINTIME_NULL(1004, "开始时间不能为空"),
    SYSTEM_ENDTIME_NULL(1005, "结束时间不能为空"),
    SYSTEM_TIMECHANGE_NULL(1006, "时间转换失败"),
    SYSTEM_USER_NOTLOGIN(1007, "用户未登陆"),
    SYSTEM_CONNECTIONTIMEOUT(1008, "查询超时，请重新查询"),
    SYSTEM_INFOID_NULL(1009, "Id不能为空"),
    SYSTEM_PARM_NULL(1010, "必填参数不能为空"),
    SYSTEM_TIMETYPE_ERROR(1011, "时间格式错误"),
    SYSTEM_SENDMSGURL_NULL(1012, "发送消息地址未配置"),
    SYSTEM_FILE_ERROR(1013, "图片格式不正确"),
    SYSTEM_FILEBIG_ERROR(1014, "文件过大"),
    SYSTEM_FILEUEL_ERROR(1015, "路径配置错误"),
    SYSTEM_FILENUll_ERROR(1016, "上传的图片为空"),

    //--------------- 用户自定义自定义异常（10000+） -------------------------
    SYSTEM_ADD_ERROR_RESON(100001, "该用户账号已存在"),
    SYSTEM_LOGIN_ERROR_RESON(100002, "用户名或密码不正确"),
    SYSTEM_LOGIN_ERROR_RESON2(100003, "该用户账号没有登录权限"),
    SYSTEM_LOGIN_ERROR_DJ(100004, "该用户已被冻结，不能登陆"),
    SYSTEM_UPDATE_ERROR_RESON(100005, "请输入正确的旧密码"),
    USER_ROLE_NAMEEXIST(10006, "新增失败，该角色已存在"),
    USER_ROLE_DISTRIBUTION(10007, "删除失败，该角色已被分配"),
    USER_ROLE_NOPOWER(10008, "未分配权限"),
    USER_ID_NULL(10009, "用户Id不能为空"),
    USER_ACCOUNT_NULL(10010, "用户账号不能为空"),
    USER_REALNAME_NULL(10011, "用户名不能为空"),
    USER_ROLEID_NULL(10012, "角色不能为空"),
    USER_UPDATE_NEEDLOGIN(10013, "您的用户信息已更改，请重新登录！"),
    USER_NOT_ASCRIPTION(10014, "登陆失败，该用户没有归属"),
    USER_STATUS_NULL(10015, "用户状态不能为空"),
    USER_OLDPASSWORD_NULL(10016, "旧密码不能为空"),
    USER_NEWPASSWORD_NULL(10017, "新密码不能为空"),
    USER_ROLEIDNAME_NULL(10018, "角色名称不能为空"),

    //--------------- 系统配置相关错误 20000+ -----------------
    SYSTEM_PARAMSID_NULL(20001, "参数Id不能为空"),
    SYSTEM_PARAMSNAME_NULL(20002, "参数名称不能为空"),
    SYSTEM_PARAMSKEY_NULL(20003, "参数KEY不能为空"),
    SYSTEM_PARAMSVALUE_NULL(20004, "参数值不能为空"),
    SYSTEM_PARAMSNOTEXITS_NULL(20005, "该参数不存在"),
    SYSTEM_ISENABLE_NULL(20006, "是否启用不能为空"),
    SYSTEM_SORT_NULL(20007, "排序字段不能为空"),
    SYSTEM_DICID_NULL(20008, "字典类别Id不能为空"),
    SYSTEM_DICNOTEXIST_NULL(20009, "字典类别不存在"),
    SYSTEM_DICSUBEXIST_NULL(20010, "字典类别分配字典，请先删除下级字典"),
    SYSTEM_PARAMSIDS_NULL(20011, "参数Ids不能为空"),
    SYSTEM_PARAMSKEY_EXIST(20012, "参数编码已存在"),
    SYSTEM_DICKEY_EXIST(20013, "字典编码已存在"),
    SYSTEM_AREAID_NOTEXIST(20014, "区域Id不存在"),
    SYSTEM_AREAID_NULL(20015, "区域Id不能为空"),
    SYSTEM_PARENTID_NULL(20016, "上级Id不能为空"),
    SYSTEM_AREANAME_NULL(20017, "区域名称不能为空"),
    SYSTEM_AREASHORTNAME_NULL(20018, "区域别名不能为空"),
    SYSTEM_LONGITUDE_NULL(20019, "经度不能为空"),
    SYSTEM_LATITUDE_NULL(20020, "纬度不能为空"),
    SYSTEM_LEVEL_NULL(20021, "区域级别不能为空"),

    //--------------- 权限相关错误 30000+ --------------------
    POWER_CHILD_EXIST(30001, "删除失败，存在子节点"),
    POWER_NAME_NULL(30002, "权限名称不能为空"),
    POWER_SIGN_NULL(30003, "权限标识不能为空"),
    POWER_PARENTID_NULL(30004, "权限上级Id不能为空"),
    POWER_TYPE_NULL(30005, "权限类型不能为空"),
    POWER_ID_NULL(30006, "权限Id不能为空"),


    //--------------- 学习相关 40000+ --------------------
    STUDY_HAS_POINT(40001, "该课程已获得积分，本次将不获得积分"),


    //FILE
    FILE_THUMBNAIL_FAIL_TO_CREATE(50001, "创建缩略图失败，请检查图片文件"),

    FILE_FRAME_FAIL_TO_CAPTURE(50002, "无法捕获所需的帧，请检查视频文件"),

    //COMMON
    COMMON_DATA_EXISTS(90001, "数据已存在"),

    COMMON_ARGUMENTS_INVALID(90002, "参数非法"),

    COMMON_DATA_NOT_ACCESSIBLE(90003, "数据不允许修改"),

    ;
    ResponseEnum(Integer code, String msg) {
        this.e = new ResponseException(this.code = code, this.msg = msg);
    }

    public final Integer code;
    public final String msg;
    public final ResponseException e;

    public ResponseException newInstance(){
        return new ResponseException(code, msg);
    }

    public ResponseException newInstance(String msg){
        return new ResponseException(code, msg);
    }

    public ResponseException throwEx(Throwable e){
        return new ResponseException(code, msg, e);
    }

    public ResponseException throwEx(String msg, Throwable e){
        return new ResponseException(code, msg, e);
    }

}
