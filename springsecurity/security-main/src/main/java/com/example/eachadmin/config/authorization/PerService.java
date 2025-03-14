package com.example.eachadmin.config.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class PerService {

    @Autowired
    private JdbcTemplate jdbcTemplate; // 假设用 JdbcTemplate，也可以用 JPA/MyBatis

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 根据用户获取其可访问的路径集合
    public Set<String> getAllowedUrlsForUser(String username) {
        // 先查 Redis 缓存
        Set<String> cachedUrls = (Set<String>) redisTemplate.opsForValue().get("user_urls:" + username);
        if (cachedUrls != null) {
            return cachedUrls;
        }
        // 从数据库查询用户角色对应的路径
        String sql = "SELECT DISTINCT p.url_pattern " +
                "FROM permissions p " +
                "JOIN role_permissions rp ON p.id = rp.permission_id " +
                "JOIN user_roles ur ON rp.role_id = ur.role_id " +
                "JOIN users u ON ur.user_id = u.id " +
                "WHERE u.username = ?";
        List<String> urls = jdbcTemplate.queryForList(sql, String.class, username);

        // 存入 Redis，设置过期时间
        Set<String> allowedUrls = new HashSet<>(urls);
        redisTemplate.opsForValue().set("user_urls:" + username, allowedUrls, 1, TimeUnit.HOURS);
        return allowedUrls;
    }
    // 刷新用户缓存（当权限或角色变化时调用）
    public void refreshCache(String username) {
        redisTemplate.delete("user_urls:" + username);
        getAllowedUrlsForUser(username); // 重新加载
    }
}
