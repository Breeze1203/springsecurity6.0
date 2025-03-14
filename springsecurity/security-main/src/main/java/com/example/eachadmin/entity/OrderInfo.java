package com.example.eachadmin.entity;

/**
 * @ClassName OrderInfo
 * @Author pt
 * @Description
 * @Date 2024/11/5 09:12
 **/
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单信息实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单渠道：1-乘客端下单，2-第三方下单
     */
    private Integer channel;

    /**
     * 应用类型：1-App，2-微信小程序，3-H5
     */
    private Integer appType;

    /**
     * 出发地地址
     */
    private String departAddr;

    /**
     * 出发地城市
     */
    private String departCity;

    /**
     * 出发地经度
     */
    private Double departLng;

    /**
     * 出发地纬度
     */
    private Double departLat;

    /**
     * 目的地地址
     */
    private String destAddr;

    /**
     * 目的地城市
     */
    private String destCity;

    /**
     * 目的地经度
     */
    private Double destLng;

    /**
     * 目的地纬度
     */
    private Double destLat;

    /**
     * 最早出行时间
     */
    private String earliestDepartTime;

    /**
     * 最晚出行时间
     */
    private String latestDepartTime;

    /**
     * 乘车人数；乘客端必传
     */
    private Integer passengerNum;

    /**
     * 请求时间，timestamp单位毫秒
     */
    private Long timestamp;
}
