package com.server.session;

import lombok.Data;

/**
 * @ClassName Session
 * @Description: TODO
 * @Author yanlin
 * @Date 2019/11/4
 * @Version V1.0
 **/
@Data
public class Session {
    // 客户端唯一性标识
    private String clientId;
}
