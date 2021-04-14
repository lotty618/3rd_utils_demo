package com.ltnet.shopdemo.service.impl;

import com.ltnet.shopdemo.dao.OrderDao;
import com.ltnet.shopdemo.dao.ProductDao;
import com.ltnet.shopdemo.entity.MQMessage;
import com.ltnet.shopdemo.entity.Order;
import com.ltnet.shopdemo.entity.Product;
import com.ltnet.shopdemo.service.ShopService;
import com.ltnet.shopdemo.utils.RedisUtil;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    ProductDao productDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RocketMQTemplate rocketMQTemplate;


    @Override
    public String buyProduct(String id) {
        Integer pid = Integer.parseInt(id);
        Product product = productDao.getProductById(pid);

        if (null == product) {
            return "Product Not Found!";
        }

        if (product.getSaled() >= product.getCount()) {
            return "The product has sold out!";
        }

        //1. 多线程超卖问题
//        product.setSaled(product.getSaled() + 1);
//        productDao.updateProduct(product);

        //2. 多线程超卖问题，在最后剩一个商品的时候同时多并发会导致超卖
//        if (productDao.saleProduct(pid, 1) == 0) {
//            return "Update saled number failed!";
//        }

        //3. 使用乐观锁，可以解决超卖问题，但是失败率较高
//        if (productDao.saleProductByLock(pid, 1, product.getVersion()) == 0) {
//            return "Update saled number failed!";
//        }

        //4. 将该方法设为同步方法，但是效率会很低，线程需要排队等待
        //public synchronized String buyProduct(String id)

        //5. 使用Redis，通过原子计数器自增取值判断，将成功的放入消息队列处理（此处省略消息队列）
        Long incr = redisUtil.incrBy("SHOPDEMO_PRODUCT_" + pid, 1);
        //incr返回值即为该Key的值，所以判断如果数量超过总数即提示已卖完
        System.out.println("Redis Value: " + incr);
        if (incr < 1) {
            return "Update saled number failed!";
        }
        if (incr > product.getCount()) {
            return "The product has sold out!";
        }

        Order order = new Order();
        order.setPid(pid);
        order.setName(product.getName());
        order.setCount(1);
        order.setStatus(1);
        order.setAddtime(String.valueOf(System.currentTimeMillis()));
        if (orderDao.addOrder(order) == 0) {
            return "Add order failed!";
        }

        //6. 使用Redis，并将创建订单放入消息队列（不实时写入数据库）
//        rocketMQTemplate.convertAndSend("shopdemo-topic", order);
//        sendMsg(order);

        return "Success!";
    }

    private void sendMsg(Object o) {
        MQMessage msg = new MQMessage();
        msg.setId(UUID.randomUUID().toString());
        msg.setVersion("1.0.0");
        msg.setData(o);
        msg.setStatus(0);
        msg.setTime(System.currentTimeMillis());

        System.out.println("Send MQ Message: " + msg.toString());

        rocketMQTemplate.convertAndSend("shopdemo-topic", msg);
//        async(msg);
    }

    private void sendMqMsg(Order order) {
        rocketMQTemplate.setMessageQueueSelector((List<MessageQueue> mqs, Message msg, Object arg) -> {
            /**
             * mqs：要发送消息的topic下的所有消息队列集合
             * msg：发送的消息
             * arg：发送消息时传递的参数 通过该参数指定发送到哪个队列
             */
            int queueNum = Integer.valueOf(String.valueOf(arg)) % mqs.size();
            System.out.println("Queue ID: " + queueNum + " Msg: " + new String(msg.getBody()));
            return mqs.get(queueNum);
        });

        rocketMQTemplate.syncSendOrderly("shopdemo-topic", order, "1");
    }

    /**
     * 异步发送消息
     */
    private void async(MQMessage msg) {
        rocketMQTemplate.asyncSend("shopdemo-topic", msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("MQ异步发送成功！");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("MQ异步发送失败！");
            }
        });
    }
}
