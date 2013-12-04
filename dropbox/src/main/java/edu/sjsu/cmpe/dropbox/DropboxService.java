package edu.sjsu.cmpe.dropbox;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsDestination;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import edu.sjsu.cmpe.dropbox.api.resources.DropboxResource;
import edu.sjsu.cmpe.dropbox.api.resources.RootResource;
import edu.sjsu.cmpe.dropbox.config.LibraryServiceConfiguration;


public class DropboxService extends Service<LibraryServiceConfiguration> {
    public static void main(String[] args) throws Exception {
	new DropboxService().run(args);
    }

    @Override
    public void initialize(Bootstrap<LibraryServiceConfiguration> bootstrap) {
	bootstrap.setName("library-service");
	bootstrap.addBundle(new ViewBundle());
    bootstrap.addBundle(new AssetsBundle());
    final String user = "admin";
	final String password ="password";
		final String host ="127.0.0.1";
		final int port = Integer.parseInt("61613");
		final String queueName =  "/queue/dropbox";
	Thread thread1 = new Thread () {
		  public void run () {
			  StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
    			factory.setBrokerURI("tcp://" + host + ":" + port);
				Connection connection;          			
				try {
					connection = factory.createConnection(user, password);
				    			
			connection.start();
			javax.jms.Session session = connection.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);
			Destination destComptopicName = null;
			 destComptopicName = new StompJmsDestination(queueName);
   			MessageConsumer consumerComptopic = session.createConsumer(destComptopicName);
   			System.currentTimeMillis();
   			System.out.println("Waiting for messages...");
   			while(true) {
   				javax.jms.Message msg = consumerComptopic.receive();
  			    if( msg instanceof  TextMessage ) {
  				String body = ((TextMessage) msg).getText();
  				
  				System.out.println("Received message = " + body);
  				String[] data = body.split(";");
  				
			final String username="dropboxigniters@gmail.com";
			final String password="test@123#";
			
			Properties props = new Properties();
			
			props.setProperty("mail.smtp.host", "smtp.gmail.com");

			props.setProperty("mail.smtp.port", "587");

			props.setProperty("mail.smtp.auth", "true");

			props.setProperty("mail.smtp.starttls.enable", "true");

			Session session1 = Session.getInstance(props,new javax.mail.Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(username, password);
				}
			});
					
			try {
				
				String SharedWithUserName=data[1]; 
				String SharedByUserName=data[2];
				String fileName = data[3];
				MimeMessage message = new MimeMessage(session1);
				message.setFrom(new InternetAddress("dropboxigniters@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(data[0]));
				message.setSubject("Dropbox - New file shared with you");
				Long second=System.currentTimeMillis();
				Long third=System.currentTimeMillis()-18;
				//String con1=uname.substring(0, 2)+uname.substring(uname.length()-2, uname.length());
				//String unicodegen=second.toString()+con1+third;
				String body1= "<html> <body> Dear "+SharedWithUserName+", <br /> <br /> This is an email from dropbox application. <br />"+SharedByUserName+" has shared file with you in dropbox. <br />The file name is "+fileName+"</body> </html>";
			//	String body="<a href=\"http://localhost:98/MyTacks/UserServlet?action=activateUser&num=\">Click here to register</a>";
				message.setText(body1, "UTF-8", "html");
				
				Transport.send(message);
			
			     System.out.println("Sent message successfully....");
			
			  }catch (MessagingException mex) {
			     mex.printStackTrace();
			  }}} } catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  }
	};
	thread1.start();
    }

    @Override
    public void run(LibraryServiceConfiguration configuration,
	    Environment environment) throws Exception {
	/** Root API */
	environment.addResource(RootResource.class);
	/** Books APIs */
	environment.addResource(DropboxResource.class);

    }
}
