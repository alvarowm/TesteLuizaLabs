package br.com.alvaro.desafio.amq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JMSConfiguration {

	private static final String TCP_LOCALHOST_61616 = "tcp://localhost:61616";

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(TCP_LOCALHOST_61616);
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public JmsListenerContainerFactory jmsFactoryTopic(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(connectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplateTopic() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}

}
