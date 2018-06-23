package dubbo.wk.service.impl;

import com.rabbitmq.client.Channel;
import dubbo.wk.model.DirectRabbitModel;
import dubbo.wk.service.MessageQueueService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 * Created by wgp on 2018/6/23.
 */
@Service
public class RabbitServiceImpl implements MessageQueueService,ChannelAwareMessageListener {

    @Autowired
    private AmqpTemplate template;
    @Override
    public void sendDirectRabbitMessage() {
//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        AmqpTemplate template = (AmqpTemplate) wac.getBean("template");
        DirectRabbitModel model = new DirectRabbitModel();
        model.setDislike("花里胡哨，浓妆艳抹");
        model.setFavorite("钱，钱，钱，有钱了才可以做好多事情");
        model.setId(9999999L);
        model.setUuid("独一无二的UUID");
        Message message = MessageBuilder.withBody(
                Objects.requireNonNull(SerializationUtils
                .serialize(model))).build();
        template.convertAndSend(message);
    }


    public void onMessage(Message message) {
        DirectRabbitModel model = (DirectRabbitModel)SerializationUtils.deserialize(message.getBody());
        if(model != null){
            System.out.println(model.getId());
            System.out.println(model.getUuid());
            System.out.println(model.getFavorite());
            System.out.println(model.getDislike());
        }

    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        DirectRabbitModel model = (DirectRabbitModel)SerializationUtils.deserialize(message.getBody());
        if(model != null){
            System.out.println(model.getId());
            System.out.println(model.getUuid());
            System.out.println(model.getFavorite());
            System.out.println(model.getDislike());
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag,false);
        System.out.println(deliveryTag);
    }
}
