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
public class ReqBO extends ReqBaseBO {
    /*
    指令id
     */
    private Integer directiveId;
    /*
    命令字,当type是1时，0普通，1摇摆，2动作响应。当type=2时，0为心跳或成功响应。当type=3时，0查询靶机，255上报注册信息
     */
    private Integer command;
    /*
    器材动作1 起， 2 倒，3 摇摆，4 旋转显 ，5旋转隐,224 红外触发
     */
    private Integer action;

    /*
    部位（1头，2胸，3肩，4腹，5腿）,如果是环靶，例如160是10环10点，145是9环1点
     */
    private Short parts;
    /*
    0在线 ，2离线
     */
    private Integer state;
    /*
    百分比 64=100%
     */
    private Integer electric;
    /*
    信号强度 50=80%
     */
    private Integer dBm;
}
