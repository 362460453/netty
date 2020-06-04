package com.server.utils;

import com.server.entity.ReqBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description: 数据包转对象
 * @Author yanlin
 * @Date 2020/5/31
 * @Version V1.0
 **/
@Component
@Slf4j
public class PacketToPOJOUtil {

    public ReqBO convert(Packet packet) {
        int type = packet.getType();
        byte[] data = packet.getData();
        switch (type) {
            case 0: {
                return controlConvert(data);
            }
            case 1: {
                return interruptConvert(data);
            }
            case 2: {
                return repConvert(data);
            }
            case 3: {
                return queryConvert(data);
            }
            default: {
                log.error("未知类型信号类型:type{}", type);
                Arrays.asList(data).forEach(z -> log.error("未知类型信号数据:data{}", z));
                return null;
            }
        }
    }

    private ReqBO controlConvert(byte[] data) {
        ReqBO reqBO = new ReqBO();
        reqBO.setEquipmentCode((int) data[0] & 0xff);
        reqBO.setDirectiveId((int) data[1] & 0xff);
        reqBO.setTargetType((int) data[2] & 0xff);
        reqBO.setCommand((int) data[3] & 0xff);
        reqBO.setAction((int) data[4] & 0xff);
        return reqBO;
    }

    private ReqBO interruptConvert(byte[] data) {
        ReqBO reqBO = new ReqBO();
        reqBO.setEquipmentCode((int) data[0] & 0xff);
        reqBO.setTargetType((int) data[1] & 0xff);
        reqBO.setParts((short) (data[2] & 0xff));
        return reqBO;
    }

    private ReqBO queryConvert(byte[] data) {
        ReqBO reqBO = new ReqBO();
        reqBO.setCommand((int) data[1] & 0xff);
        if (reqBO.getCommand() == 0) {//机器信息查询,请求项是0表示查询机器全部状态
            reqBO.setState((int) data[2] & 0xff);
            reqBO.setElectric((int) data[3] & 0xff);
            reqBO.setDBm((int) data[4] & 0xff);
        } else {//机器注册
            reqBO.setEquipmentCode((int) data[2] & 0xff);
            reqBO.setTargetType((int) data[3] & 0xff);
        }
        return reqBO;
    }

    /*
    客户端心跳
     */
    private ReqBO repConvert(byte[] data) {
        ReqBO reqBO = new ReqBO();
        reqBO.setCommand((int) data[0] & 0xff);
        return reqBO;
    }
}
