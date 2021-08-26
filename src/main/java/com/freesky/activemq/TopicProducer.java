package com.freesky.activemq;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 主题发布者
 * 
 * 先运行订阅者，后运行发布者。
 * 
 * @author Max Zhang
 *
 */
public class TopicProducer {

	// 默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	// 发送的消息数量
	private static final int SENDNUM = 10;

	public static void main(String[] args) {
		// 连接工厂
		TopicConnectionFactory connectionFactory;
		// 连接
		TopicConnection connection = null;
		// 会话 接受或者发送消息的线程
		TopicSession session;
		// 消息主题
		Topic topic;
		// 消息生产者
		TopicPublisher publisher;
		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(TopicProducer.USERNAME, TopicProducer.PASSWORD,
				TopicProducer.BROKEURL);

		try {
			// 通过连接工厂获取连接
			connection = connectionFactory.createTopicConnection();
			// 启动连接
			connection.start();
			// 创建session
			session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
			// 创建一个名称为HelloWorld的消息队列
			topic = session.createTopic("Hello-Topic");
			// 创建消息生产者
			publisher = session.createPublisher(topic);
			// 发送消息
			sendMessage(session, publisher);

			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param publisher 消息发布者
	 * @throws Exception
	 */
	public static void sendMessage(Session session, TopicPublisher publisher) throws Exception {
		for (int i = 0; i < TopicProducer.SENDNUM; i++) {
			// 创建一条文本消息
			TextMessage message = session.createTextMessage("ActiveMQ 发送Topic消息" + i);
			System.out.println("发送消息：Activemq 发送Topic消息" + i);
			// 通过消息生产者发出消息
			publisher.publish(message);
		}

	}
}
