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
 * ���ⷢ����
 * 
 * �����ж����ߣ������з����ߡ�
 * 
 * @author Max Zhang
 *
 */
public class TopicProducer {

	// Ĭ�������û���
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// Ĭ����������
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// Ĭ�����ӵ�ַ
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	// ���͵���Ϣ����
	private static final int SENDNUM = 10;

	public static void main(String[] args) {
		// ���ӹ���
		TopicConnectionFactory connectionFactory;
		// ����
		TopicConnection connection = null;
		// �Ự ���ܻ��߷�����Ϣ���߳�
		TopicSession session;
		// ��Ϣ����
		Topic topic;
		// ��Ϣ������
		TopicPublisher publisher;
		// ʵ�������ӹ���
		connectionFactory = new ActiveMQConnectionFactory(TopicProducer.USERNAME, TopicProducer.PASSWORD,
				TopicProducer.BROKEURL);

		try {
			// ͨ�����ӹ�����ȡ����
			connection = connectionFactory.createTopicConnection();
			// ��������
			connection.start();
			// ����session
			session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
			// ����һ������ΪHelloWorld����Ϣ����
			topic = session.createTopic("Hello-Topic");
			// ������Ϣ������
			publisher = session.createPublisher(topic);
			// ������Ϣ
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
	 * ������Ϣ
	 * 
	 * @param session
	 * @param publisher ��Ϣ������
	 * @throws Exception
	 */
	public static void sendMessage(Session session, TopicPublisher publisher) throws Exception {
		for (int i = 0; i < TopicProducer.SENDNUM; i++) {
			// ����һ���ı���Ϣ
			TextMessage message = session.createTextMessage("ActiveMQ ����Topic��Ϣ" + i);
			System.out.println("������Ϣ��Activemq ����Topic��Ϣ" + i);
			// ͨ����Ϣ�����߷�����Ϣ
			publisher.publish(message);
		}

	}
}
