package dubbo.wk.service.impl;

import com.rabbitmq.client.Channel;
import dubbo.wk.model.DirectRabbitModel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.util.SerializationUtils;

/**
 * Created by wgp on 2018/6/23.
 */
public class RabbitMqConsumer implements MessageListener {

//    @Override
//    public void onMessage(Message message, Channel channel)  {
//        try{
//            DirectRabbitModel model = (DirectRabbitModel) SerializationUtils.deserialize(message.getBody());
//            if(model != null){
//                System.out.println(model.getId());
//                System.out.println(model.getUuid());
//                System.out.println(model.getFavorite());
//                System.out.println(model.getDislike());
//            }
//            long deliveryTag = message.getMessageProperties().getDeliveryTag();
//            channel.basicAck(deliveryTag,false);
//            System.out.println(deliveryTag);
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("发生异常");
//        }
//
//    }


    @Override
    public void onMessage(Message message) {
        DirectRabbitModel model = (DirectRabbitModel) SerializationUtils.deserialize(message.getBody());
            if(model != null){
                System.out.println(model.getId());
                System.out.println(model.getUuid());
                System.out.println(model.getFavorite());
                System.out.println(model.getDislike());
            }
        System.out.println("为空了");
    }
}
