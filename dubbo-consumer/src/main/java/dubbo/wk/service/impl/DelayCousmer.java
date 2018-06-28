package dubbo.wk.service.impl;

import com.rabbitmq.client.Channel;
import dubbo.wk.model.DirectRabbitModel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

/**
 * Created by wgp on 2018/6/28.
 */
@Component
public class DelayCousmer implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        DirectRabbitModel model = (DirectRabbitModel) SerializationUtils.deserialize(message.getBody());
        if(model != null){
            System.out.println(model.getId());
            System.out.println(model.getUuid());
            System.out.println(model.getFavorite());
            System.out.println(model.getDislike());
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,false);
        System.out.println(deliveryTag);
        System.out.println("延迟消费的消费者");
    }
}
