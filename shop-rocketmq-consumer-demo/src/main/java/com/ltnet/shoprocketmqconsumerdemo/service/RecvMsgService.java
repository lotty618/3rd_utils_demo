package com.ltnet.shoprocketmqconsumerdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ltnet.shoprocketmqconsumerdemo.entity.MQMessage;
import com.ltnet.shoprocketmqconsumerdemo.entity.Order;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "shopdemo-group", topic = "shopdemo-topic")
public class RecvMsgService implements RocketMQListener<MQMessage> {
    @Override
    public void onMessage(MQMessage msg) {
        System.out.println("[" + System.currentTimeMillis() + "]Recv Msg: " + msg.toString());
        String str = JSONObject.toJSONString(msg.getData());
        Order order = (Order) JSONObject.parseObject(str, Order.class);
        System.out.println(order.toString());
        if (order.getStatus() == 1) {
            System.out.println("Bought successfully and updated to database!");
        } else {
            System.out.println("Failed!");
        }
    }
}
