package cola_jms;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer implements Runnable{

	@Override
	public void run() {
		try {
			
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://DESKTOP-Q5D6MG2:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("Cola.requerida");

            MessageProducer producer = session.createProducer(destination);
            //DeliveryMode.PERSISTENT me permite acumular el mensaje en el producer en caso de pérdida
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = "Mensaje a introducir en la cola";
            //Session tambien me permite crear mensajes de objetos, streams y muchas otras cosas
            TextMessage message = session.createTextMessage(text);

            System.out.println("Mensaje mandado: "+text);
            producer.send(message);

            
            session.close();
            connection.close();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

}
