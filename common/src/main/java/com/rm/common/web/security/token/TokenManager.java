package com.rm.common.web.security.token;


import com.rm.common.cache.CacheService;
import com.rm.common.utils.DateUtils;
import com.rm.common.utils.IdGenerator;
import com.rm.common.web.security.config.EPlatform;
import com.rm.common.web.security.config.RmSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/14.
 * Token管理器
 *
 * @see Token
 */
@Component
public class TokenManager {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private RmSecurityProperties rmSecurityProperties;

    /**
     * 创建token，token会在配置的时间之后失效
     *
     * @param userId 用户Id
     * @return 创建好的token
     */
    public Token create(String userId, EPlatform platform, Set<String> signSet) {
        String key = getKey(userId, platform);
        Token token = new Token(userId, IdGenerator.uuid(), platform, signSet);
        cacheService.set(key, token);
        return token;
    }

    /**
     * 根据用户id删除token
     *
     * @param token 用户id
     */
    public void delete(Token token) {
        String key = getKey(token.getUserId(), token.getPlatform());
        cacheService.remove(key);
    }

    /**
     * 将校验字符串解析为Token
     *
     * @param tokenString 校验字符串
     * @return Token
     */
    public Token parse(String tokenString) {
        if (StringUtils.hasText(tokenString)) {
            String[] csv = tokenString.split(Token.TOKEN_SPLIT);
            if (csv.length >= 3) {
                return new Token(csv[0], csv[1], EPlatform.valueOf(csv[2]), null);
            }
        }
        return null;
    }

    /**
     * 检验token
     *
     * @param token token
     * @return 检验是否通过
     */
    public Token check(Token token) {
        if (token != null) {
            String key = getKey(token.getUserId(), token.getPlatform());
            Token cached = (Token) cacheService.get(key);
            //  过期时间判断 过期删除  延长有效时间
            if (cached != null && cached.equals(token)) {
                LocalDateTime lastDate = cached.getCtime().plus(rmSecurityProperties.getExpireTime(), DateUtils.convert(rmSecurityProperties.getTimeUnit()));
                //  token过期 删除token
                if (LocalDateTime.now().isAfter(lastDate)) {
                    cacheService.remove(key);
                    return null;
                }
                //延长过期时间
                cached.setCtime(LocalDateTime.now());
                cacheService.set(key, cached);
                return cached;//登陆验证成功，返回所缓存的token
            }
        }
        return null;
    }

    /**
     * 包装token  key
     *
     * @param userId
     * @return
     */
    private String getKey(String userId, EPlatform platform) {
        return Token.HEADER_TOKEN + ":" + platform.getDescription() + ":" + userId;
    }

}
