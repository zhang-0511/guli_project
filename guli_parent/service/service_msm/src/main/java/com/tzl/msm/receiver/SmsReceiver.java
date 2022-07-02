/*
package com.tzl.msm.receiver;


import com.rabbitmq.client.Channel;
import com.zyf.constant.MqConst;
import com.zyf.service.MsmService;
import com.zyf.vo.msm.MsmVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

*/
/** mq消息发送
 * @author zyf
 * @date 2021年05月31日 17:53
 *//*

@Component
public class SmsReceiver {

    @Autowired
    private MsmService msmService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_MSM_ITEM, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_MSM),
            key = {MqConst.ROUTING_MSM_ITEM}
    ))
    public void send(MsmVo msmVo, Message message, Channel channel){
        msmService.sendMq(msmVo);
    }
}
*/
