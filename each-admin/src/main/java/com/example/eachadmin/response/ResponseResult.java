package com.example.eachadmin.response;


import com.example.eachadmin.config.Authentication.CustomizeAuthenticationFailureHandler;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ResponseResult <T> implements Serializable{

    /**
     * response timestamp.
     */
    private Long timestamp;

    /**
     * response code, 200 -> OK.
     */
    private String status;

    /**
     * response message.
     */
    private String message;

    /**
     * response data.
     */
    private T data;
    public static <T extends Serializable> ResponseResult<T> success() {
        return success(null);
    }

    /**
     * response success result wrapper.
     */
    public static <T extends Serializable> ResponseResult<T> success(T data) {
        return ResponseResult.<T>builder().data(data)
                .message(ResponseStatus.HTTP_STATUS_200.getDescription())
                .status(ResponseStatus.HTTP_STATUS_200.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * response error result wrapper.
     */
    public static <T extends Serializable> ResponseResult<T> fail(String message) {
        return fail(null, message);
    }

    /**
     * response error result wrapper.
     */
    public static <T extends Serializable> ResponseResult<T> fail(T data, String message) {
        return ResponseResult.<T>builder().data(data)
                .message(message)
                .status(ResponseStatus.HTTP_STATUS_500.getResponseCode())
                .timestamp(System.currentTimeMillis())
                .build();
    }

}