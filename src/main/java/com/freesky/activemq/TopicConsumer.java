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
 * ���ⶩ����
 * 
 * �����ж����ߣ������з����ߡ�
 * 
 * @author Max Zhang
 *
 */
public class TopicConsumer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;// Ĭ�������û���
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;// Ĭ����������
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;// Ĭ�����ӵ�ַ

	public static void main(String[] args) {
		TopicConnectionFactory connectionFactory;// ���ӹ���
		TopicConnection connection = null;// ����

		TopicSession session;// �Ự ���ܻ��߷�����Ϣ���߳�
		Topic topic;// ��Ϣ��Ŀ�ĵ�

		TopicSubscriber subscriber;// ��Ϣ��������

		// ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(TopicConsumer.USERNAME, TopicConsumer.PASSWORD,
				TopicConsumer.BROKEURL);

		try {
			// ͨ�����ӹ�����ȡ����
			connection = connectionFactory.createTopicConnection();
			// ��������
			connection.start();
			// ����session
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			// ����һ������HelloWorld����Ϣ����
			topic = session.createTopic("Hello-Topic");
			// ������Ϣ������
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
