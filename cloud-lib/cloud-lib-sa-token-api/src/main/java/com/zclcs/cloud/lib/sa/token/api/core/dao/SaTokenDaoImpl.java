package com.zclcs.cloud.lib.sa.token.api.core.dao;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.util.StrUtil;
import com.zclcs.common.jackson.starter.util.JsonUtil;
import com.zclcs.common.redis.starter.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sa-Token持久层接口(使用框架自带RedisUtils实现 协议统一)
 *
 * @author zclcs
 */
@Service
@RequiredArgsConstructor
public class SaTokenDaoImpl implements SaTokenDao {

    private final RedisService redisService;

    /**
     * 获取Value，如无返空
     */
    @Override
    public String get(String key) {
        return (String) redisService.get(key);
    }

    /**
     * 写入Value，并设定存活时间 (单位: 秒)
     */
    @Override
    public void set(String key, String value, long timeout) {
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            redisService.set(key, value);
        } else {
            redisService.set(key, value, timeout);
        }
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除Value
     */
    @Override
    public void delete(String key) {
        redisService.del(key);
    }

    /**
     * 获取Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getTimeout(String key) {
        return redisService.getExpire(key);
    }

    /**
     * 修改Value的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        redisService.expire(key, timeout);
    }

    /**
     * 获取Object，如无返空
     */
    @Override
    public Object getObject(String key) {
        return redisService.get(key);
    }

    /**
     * 写入Object，并设定存活时间 (单位: 秒)
     */
    @SneakyThrows
    @Override
    public void setObject(String key, Object object, long timeout) {
        var redisValue = "";
        if (object instanceof SaSession saSession) {
            redisValue = JsonUtil.toJson(saSession);
        } else {
            redisValue = (String) object;
        }
        if (timeout == 0 || timeout <= SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        // 判断是否为永不过期
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            redisService.set(key, redisValue);
        } else {
            redisService.set(key, redisValue, timeout);
        }
    }

    /**
     * 更新Object (过期时间不变)
     */
    @SneakyThrows
    @Override
    public void updateObject(String key, Object object) {
        var redisValue = "";
        if (object instanceof SaSession saSession) {
            redisValue = JsonUtil.toJson(saSession);
        } else {
            redisValue = (String) object;
        }
        long expire = getObjectTimeout(key);
        // -2 = 无此键
        if (expire == SaTokenDao.NOT_VALUE_EXPIRE) {
            return;
        }
        this.setObject(key, redisValue, expire);
    }

    /**
     * 删除Object
     */
    @Override
    public void deleteObject(String key) {
        redisService.del(key);
    }

    /**
     * 获取Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public long getObjectTimeout(String key) {
        return redisService.getExpire(key);
    }

    /**
     * 修改Object的剩余存活时间 (单位: 秒)
     */
    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 判断是否想要设置为永久
        if (timeout == SaTokenDao.NEVER_EXPIRE) {
            long expire = getObjectTimeout(key);
            if (expire == SaTokenDao.NEVER_EXPIRE) {
                // 如果其已经被设置为永久，则不作任何处理
            } else {
                // 如果尚未被设置为永久，那么再次set一次
                this.setObject(key, this.getObject(key), timeout);
            }
            return;
        }
        redisService.expire(key, timeout);
    }

    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        Collection<String> keys = redisService.keys(prefix + "*" + keyword + "*");
        List<String> list = new ArrayList<>(keys);
        return SaFoxUtil.searchList(list, start, size, sortType);
    }

    @SneakyThrows
    @Override
    public SaSession getSession(String sessionId) {
        String s = (String) getObject(sessionId);
        if (StrUtil.isBlank(s)) {
            return null;
        }
        return JsonUtil.readValue(s, SaSession.class);
    }
}
