package com.xt.yde.job.message.listener;

import com.rabbitmq.client.Channel;
import com.xt.yde.job.Application;
import com.xt.yde.job.message.RetryProcessMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.json.Json;
import org.sunyata.octopus.message.ComplexMessageInfo;

/**
 * Created by leo on 17/5/8.
 */
@Component("quarkMessageListener")
public class QuarkMessageListener implements ChannelAwareMessageListener {
    final Logger logger = LoggerFactory.getLogger(QuarkMessageListener.class);
    final Logger loggerApplication = LoggerFactory.getLogger(Application.class);
    @Autowired
    RetryProcessMessageService retryProcessMessageService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody());
        logger.info("消息来啦:" + msg);
        ComplexMessageInfo jobInfo = null;
        try {
            jobInfo = Json.decodeValue(msg, ComplexMessageInfo.class);
            logger.info("job任务序列化成功:" + msg);
        } catch (Exception e) {//序列化失败,任务被抛弃
            logger.info("外层消息序列化失几:" + msg);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        try {
            retryProcessMessageService.process(jobInfo);
            //}
//        catch (DecodeException ex) {
////            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
////            logger.error("消息序列化失败:" + msg);
        } catch (Exception ex) {
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error(msg);
            loggerApplication.error("消息处理失败:" + msg);
        }
    }
}
