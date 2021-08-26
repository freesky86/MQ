package com.freesky.jms.ptp;

import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

/*
 * Run the Receiver class first then Sender class.
 * 
 * JMS tutorial
 * https://www.javatpoint.com/jms-tutorial
 * 
 * How to install and start glassfish
 * https://blog.csdn.net/qq_36883987/article/details/82183053
 * 
 * download glasshfish 5.1.0
 * https://jakarta.ee/compatibility/download/
 * https://www.eclipse.org/downloads/download.php?file=/glassfish/glassfish-5.1.0.zip
 * 
 * start and stop glassfish
 * 
	D:\>cd D:\Software\glassfish\glassfish5\bin
	
	D:\Software\glassfish\glassfish5\bin>asadmin start-domain
	Waiting for domain1 to start .....
	Successfully started the domain : domain1
	domain  Location: D:\Software\glassfish\glassfish5\glassfish\domains\domain1
	Log File: D:\Software\glassfish\glassfish5\glassfish\domains\domain1\logs\server.log
	Admin Port: 4848
	Command start-domain executed successfully.
	
	D:\Software\glassfish\glassfish5\bin>asadmin stop-domain
	Waiting for the domain to stop .
	Command stop-domain executed successfully.
 * 
 * 
 */
public class MyReceiver {

	public static void main(String[] args) {
		try {
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.enterprise.naming.impl.SerialInitContextFactory");
			props.setProperty(Context.URL_PKG_PREFIXES,
			        "com.sun.enterprise.naming");
//			props.setProperty("org.omg.CORBA.ORBInitialPort","4848");
//			props.setProperty("org.omg.CORBA.ORBInitialHost","localhost");
			props.setProperty(Context.PROVIDER_URL,"http://localhost:4848");
			// 1) Create and start connection
			InitialContext ctx = new InitialContext(props);
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();
			// 2) create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("myQueue");
			// 4)create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);

			// 5) create listener object
			MyListener listener = new MyListener();

			// 6) register the listener object with receiver
			receiver.setMessageListener(listener);

			System.out.println("Receiver1 is ready, waiting for messages...");
			System.out.println("press Ctrl+c to shutdown...");
			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
