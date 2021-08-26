package com.freesky.activemq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.freesky.jms.pubsub.MyListener;

/**
 * 主题订阅者
 * 
 * 先运行订阅者，后运行发布者。
 * 
 * @author Max Zhang
 *
 */
public class TopicConsumer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// 默认连接用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// 默认连接密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// 默认连接地址

	public static void main(String[] args) {
		TopicConnectionFactory connectionFactory;// 连接工厂
		TopicConnection connection = null;// 连接

		TopicSession session;// 会话 接受或者发送消息的线程
		Topic topic;// 消息的目的地

		TopicSubscriber subscriber;// 消息的消费者

		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(TopicConsumer.USERNAME, TopicConsumer.PASSWORD,
				TopicConsumer.BROKEURL);

		try {
			// 通过连接工厂获取连接
			connection = connectionFactory.createTopicConnection();
			// 启动连接
			connection.start();
			// 创建session
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			// 创建一个连接HelloWorld的消息队列
			topic = session.createTopic("Hello-Topic");
			// 创建消息消费者
			subscriber = session.createSubscriber(topic);
			
			MyListener listener = new MyListener();
			subscriber.setMessageListener(listener);
			
			try {
		        System.in.read();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
