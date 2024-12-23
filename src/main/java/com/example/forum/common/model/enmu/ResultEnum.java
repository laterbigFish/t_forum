package com.example.forum.common.model.enmu;
import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
public enum ResultEnum {
    USCCESS(200),
    FAIL(-1);
    @Getter
    int code;
}