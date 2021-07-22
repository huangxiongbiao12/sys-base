package com.rm.common.web.security.token;


import com.rm.common.web.security.annotation.Access;
import com.rm.common.web.security.annotation.RefuseAccess;
import com.rm.common.web.security.config.EPlatform;
import com.rm.common.web.security.config.RmSecurityProperties;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Set;


/**
 * Created by Administrator on 2017/8/14.
 */
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = -7195818219149330529L;

    /**
     * Header中存储token的字段的key
     */
    public static final String HEADER_TOKEN = "token";

    /**
     * token分割符  asccii 分组符
     */
    public static final String TOKEN_SPLIT = "_";

    private String userId;

    private String uuid;

    private LocalDateTime ctime;

    private EPlatform platform = EPlatform.Any;

    private Set<String> signSet;

    public Token() {
    }

    public Token(String userId, String uuid, EPlatform platform, Set<String> signSet) {
        this.userId = userId;
        this.uuid = uuid;
        this.signSet = signSet;
        this.ctime = LocalDateTime.now();
        this.platform = platform;
    }


    /**
     * 校验权限，即所请求的接口是否在当前token所拥有的权限列表中
     * 包含其中一个标识，即可访问
     *
     * @param signArr 权限标识
     * @return 校验结果
     */
    public boolean confirmSign(String... signArr) {
        if (signArr == null) return true;
        for (String sign : signArr) {
            if (this.signSet != null && this.signSet.contains(sign)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 包含其中一个标识,及不可访问
     *
     * @param signArr 权限标识
     * @return
     */
    public boolean refuseSign(String... signArr) {
        if (signArr == null) return true;
        return !confirmSign(signArr);
    }

    /**
     * 两者冲突一sign内可访问为准
     *
     * @param sign    拥有即可访问
     * @param disSign 拥有即不能访问
     * @return
     */
    public boolean confirm(String[] sign, String[] disSign) {
        if (sign == null && disSign == null) {
            return true;
        } else if (sign == null && disSign != null) {
            return refuseSign(disSign);
        } else if (sign != null && disSign == null) {
            return confirmSign(sign);
        } else {
            return confirmSign(sign);
        }
    }

    /**
     * 根据请求的path和方法，判断是都拥有改方法权限
     *
     * @param path
     * @param method
     * @param rmSecurityProperties
     * @return
     */
    public boolean confirm(String path, Method method, RmSecurityProperties rmSecurityProperties) {
        Access accessAnnotation = method.getAnnotation(Access.class);
        RefuseAccess refuseAccessAnnotation = method.getAnnotation(RefuseAccess.class);
        String[] permissions = accessAnnotation != null ? accessAnnotation.identify() : null;
        String[] refusePermissions = refuseAccessAnnotation != null ? refuseAccessAnnotation.identify() : null;
        String[] permissionsProperties = rmSecurityProperties.getPerssions().get(path);
        String[] refusePermissionsProperties = rmSecurityProperties.getRefusePerssions().get(path);
        permissions = ArrayUtils.addAll(permissions, permissionsProperties);
        refusePermissions = ArrayUtils.addAll(refusePermissions, refusePermissionsProperties);
        return confirm(permissions, refusePermissions);
    }


    public boolean equals(Token token) {
        return this.getUuid().equals(token.getUuid())
                && this.getUserId().equals(token.getUserId())
                && this.getPlatform().equals(token.getPlatform());
    }

    @Override
    public String toString() {
        return this.userId + TOKEN_SPLIT + this.uuid + TOKEN_SPLIT + this.platform.toString();
    }

}
