package com.learningcenter.miaosha.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 比较重要的概念:
 *
 *  虚拟主机：一个虚拟主机持有一组交换机、队列和绑定。为什么需要多个虚拟主机呢？很简单，RabbitMQ当中，用户只能在虚拟主机的粒度进行权限控制。 因此，如果需要禁止A组访问B组的交换机/队列/绑定，必须为A和B分别创建一个虚拟主机。每一个RabbitMQ服务器都有一个默认的虚拟主机“/”。
 *
 *  交换机：Exchange 用于转发消息，但是它不会做存储 ，如果没有 Queue bind 到 Exchange 的话，它会直接丢弃掉 Producer 发送过来的消息。
 *    交换机的功能主要是接收消息并且转发到绑定的队列，交换机不存储消息，在启用ack模式后，交换机找不到队列会返回错误。交换机有四种类型：Direct, topic, Headers and Fanout
 *      Direct：消息直接发送到指定队列中
 *
 *      Topic： 按规则转发消息（最灵活），消息先被发送到交换机上，然后交换机会通过指定的路由key转发到对应的队列中
 *
 *      Headers：设置header attribute参数类型的交换机，headers 也是根据规则匹配, 相较于 direct 和 topic 固定地使用 routing_key , headers 则是一个自定义匹配规则的类型.
 * 在队列与交换器绑定时, 会设定一组键值对规则, 消息中也包括一组键值对( headers 属性), 当这些键值对有一对, 或全部匹配时, 消息被投送到对应队列.
 *
 *      Fanout：转发消息到所有绑定队列，Fanout Exchange 消息广播的模式，不管路由键或者是路由模式，会把消息发给绑定给它的全部队列，如果配置了routing_key会被忽略。
 *
 *  路由键:消息到交换机的时候，交互机会转发到对应的队列中，那么究竟转发到哪个队列，就要根据该路由键。
 *  路由键必须是一串字符，用句号（.） 隔开，比如说 agreements.us，或者 agreements.eu.stockholm 等。
 *  路由模式必须包含一个 星号（*），主要用于匹配路由键指定位置的一个单词，
 *  比如说，一个路由模式是这样子：agreements..b.*，
 *  那么就只能匹配路由键是这样子的：第一个单词是 agreements，第四个单词是 b。 井号（#）就表示相当于一个或者多个单词，
 *  例如一个匹配模式是agreements.eu.berlin.#，那么，以agreements.eu.berlin开头的路由键都是可以的
 *
 *  绑定：也就是交换机需要和队列相绑定，这其中如上图所示，是多对多的关系。
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-22 13:15
 **/
@Configuration
public class RabbitMQConfiguration {
    public final static String message = "topic.message";
    public final static String messages = "topic.messages";
    public final static String queue = "my_queue";
    public static final String HEADERS_QUEUE = "headers_queue";

    @Bean
    public Queue myQueue(){
        return new Queue(queue,true);
    }

//----------------------------------- 下面是Topic Exchange----------------------------------------------------------

    @Bean
    public Queue queueMessage() {
        return new Queue(message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(messages);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        //使用topic.message这个路由key将queueMessage这个队列绑定到exchange这个交换机
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    /**
     * 广播模式，会发送到所有的队列
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingMyQueue(Queue myQueue,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(myQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingQueueMessage(Queue queueMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueMessage).to(fanoutExchange);
    }

    /**
     * Headers模式
     */
    @Bean
    public Queue headersQueue() {
        return new Queue(HEADERS_QUEUE);
    }

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange("headersExchange");
    }

    @Bean
    Binding bindingHeadersMessage(Queue headersQueue,HeadersExchange headersExchange) {
        Map<String,Object> conditions = new HashMap<String,Object>();
        conditions.put("header1","value1");
        conditions.put("header2","value2");
        return BindingBuilder.bind(headersQueue).to(headersExchange).whereAll(conditions).match();
    }



}
