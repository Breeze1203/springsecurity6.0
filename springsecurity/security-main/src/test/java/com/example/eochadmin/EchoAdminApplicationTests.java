package com.example.eochadmin;


import com.example.eachadmin.config.remember.RememberService;
import com.example.eachadmin.entity.OrderInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.HashMap;


@SpringBootTest(classes = EchoAdminApplicationTests.class)
class EchoAdminApplicationTests {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345"));
        String[] a = RememberService.decodeCookie("QnV4Q1JxTEtVQzVZeWUyY2tPUDZmUSUzRCUzRDp1bFp6Q3dPdG9mS1Q4bG4wekN4eUJBJTNEJTNE");
        System.out.println(a[0]);
        System.out.println(a[1]);
    }

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("12345"));
    }

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void redisTemple() {
        OrderInfo orderInfo = new OrderInfo(
                1, // channel
                1, // appType
                "出发地地址", // departAddr
                "出发地城市", // departCity
                116.397128, // departLng
                39.916527, // departLat
                "目的地地址", // destAddr
                "目的地城市", // destCity
                117.283042, // destLng
                31.861191, // destLat
                "2024-05-01 08:00", // earliestDepartTime
                "2024-05-01 18:00", // latestDepartTime
                4, // passengerNum
                System.currentTimeMillis() // timestamp
        );
        storeOrderInfo(orderInfo);
    }


    // 存储实体类对象到Redis
    public void storeOrderInfo(OrderInfo orderInfo) {
        // 使用请求时间作为散列的主键
        String key = "order:" + orderInfo.getTimestamp();
        // 使用出发地经度和纬度作为复合键
        String field = "departLng:" + orderInfo.getDepartLng() + ",departLat:" + orderInfo.getDepartLat();
        HashMap<String, Object> map = new HashMap<>();
        map.put("k1",orderInfo.getChannel());
        map.put("k2",orderInfo.getDestAddr());
        // 存储实体类对象
        redisTemplate.opsForHash().put(key, map, orderInfo);
    }

    // 从Redis获取实体类对象
    public OrderInfo getOrderInfo(Long timestamp, Double departLng, Double departLat) {
        // 构建散列的主键
        String key = "order:" + timestamp;
        // 构建复合键
        String field = "departLng:" + departLng + ",departLat:" + departLat;
        // 从Redis获取实体类对象
        Object orderInfoObject = redisTemplate.opsForHash().get(key, field);
        if (orderInfoObject != null) {
            return (OrderInfo) orderInfoObject;
        }
        return null;
    }
}
