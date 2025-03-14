package com.example.eachadmin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    HTTP_STATUS_200("200", "success"),
    HTTP_STATUS_400("400", "request error"),
    HTTP_STATUS_401("401", "尚未登录，请登录"),
    HTTP_STATUS_403("403", "暂无访问权限"),
    HTTP_STATUS_500("500", "failed");

    /**
     * response code
     */
    private final String responseCode;
    /**
     * description.
     */
    private final String description;

}
