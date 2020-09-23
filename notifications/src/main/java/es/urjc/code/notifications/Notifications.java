package es.urjc.code.notifications;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Notifications {

	private static final boolean NON_DURABLE = false;

	public static void main(String[] args) {
		SpringApplication.run(Notifications.class, args);
	}

	@Bean
	public Queue myQueue() {
		return new Queue("myQueue", NON_DURABLE);
	}

	@RabbitListener(queues = "myQueue")
	public void listen(String in) {
		System.out.println("Notification read read from myQueue : " + in);
	}

}
