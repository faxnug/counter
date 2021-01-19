package com.why.counter.bean.res;

import lombok.*;

/**
 * @Author WHY
 * @Date 2021-01-19
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Account {
    @NonNull
    private int id;
    @NonNull
    private long uid;
    @NonNull
    private String lastLoginDate;
    @NonNull
    private String lastLoginTime;

    private String token;

}
