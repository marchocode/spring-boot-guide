package xyz.chaobei.error.exceptions;

import xyz.chaobei.error.enums.ErrorCodeMessageEnum;

public abstract class AbstractException extends RuntimeException {

    private final ErrorCodeMessageEnum info;

    public AbstractException(ErrorCodeMessageEnum info) {
        super(info.getMessage());
        this.info = info;
    }

    public ErrorCodeMessageEnum getInfo() {
        return info;
    }

}