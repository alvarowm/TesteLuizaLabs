package br.com.alvaro.desafio.amq;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alvaro.desafio.entidade.Campanha;

@Component
public class CampanhaProducer {
	
	private static final String TCP_LOCALHOST_61616 = "tcp://localhost:61616";

	public CampanhaProducer() throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector(TCP_LOCALHOST_61616);
		broker.start();
	}
	
	@Autowired 
	private JmsTemplate jmsTemplate;
	
	public void enviarCampanha(Campanha campanha) throws JmsException, JsonProcessingException {
		jmsTemplate.convertAndSend("CAMPANHA_QUEUE", converterObject(campanha));
	}
	
	
	private String converterObject (Campanha campanha) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(campanha);
	}


}
