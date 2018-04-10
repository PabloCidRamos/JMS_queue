package cola_jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable, ExceptionListener{

	@Override
	public void onException(JMSException arg0) {
		System.out.println("Ha ocurrido un error con la cola. Se destruirá el cliente");
		
	}

	@Override
	public void run() {
		try {
			//Introducir la direccion adecuada en la que escucha la cola al ejecutar
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://DESKTOP-Q5D6MG2:61616");

            Connection connection = connectionFactory.createConnection();
            connection.start();

            connection.setExceptionListener(this);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Cogemos la queue o el topic del que queremos leer
            Destination destination = session.createQueue("Cola.requerida");

            MessageConsumer consumer = session.createConsumer(destination);

            //Le indicamos cuanto estamos dispuestos a esperar por el mensaje en la cola
            Message message = consumer.receive(1000);

            if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("El texto recibido fue: " + text);
            } else {
                System.out.println("El mensaje recibido fue: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
		}catch(Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

}
