package com.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: TODO
 * @Author yanlin
 * @Date 2020/5/31
 * @Version V1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ReqBaseBO {
    /*
    靶机编码
     */
    private Integer equipmentCode;
    /*
    靶板类别 1 环靶，2半身靶，3侧身靶，4人质靶，5红外
     */
    private Integer targetType;
}
