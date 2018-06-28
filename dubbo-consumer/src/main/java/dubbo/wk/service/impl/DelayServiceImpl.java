package dubbo.wk.service.impl;

import com.rabbitmq.client.Channel;
import dubbo.wk.model.DirectRabbitModel;
import dubbo.wk.service.MessageQueueService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.Objects;

/**
 * Created by wgp on 2018/6/27.
 */
@Service("delayServiceImpl")
public class DelayServiceImpl implements MessageQueueService {

    @Autowired
    private AmqpTemplate delayTemplate;
    @Override
    public void sendDirectRabbitMessage() {
        DirectRabbitModel model = new DirectRabbitModel();
        model.setDislike("花里胡哨，浓妆艳抹");
        model.setFavorite("钱，钱，钱，这是延迟队列");
        model.setId(9999999L);
        model.setUuid("独一无二的UUID");
        Message message = MessageBuilder.withBody(
                Objects.requireNonNull(SerializationUtils
                        .serialize(model))).build();
        delayTemplate.convertAndSend("delay_queue", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(30000);
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                System.out.println("消息延后30秒");
                return message;
            }
        });
    }

}
