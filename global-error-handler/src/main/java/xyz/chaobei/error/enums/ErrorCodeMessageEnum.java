package xyz.chaobei.error.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeMessageEnum {

    OK(1, "请求成功"),
    DEFAULT_ERROR(0, "请求失败");

    ErrorCodeMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;

    private final String message;

}