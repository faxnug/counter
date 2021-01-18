package com.why.counter.bean.res;

import lombok.*;

/**
 * @Author WHY
 * @Date 2021-01-15
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CaptchaRes {
    private String id;
    private String imageBase64;
}
