package br.com.eduardo.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.config.RabbitMQConfig;
import br.com.eduardo.dto.ResponseDTO;

@RestController
@RequestMapping("/queue")
public class QueueController {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@GetMapping
	public  ResponseEntity<?>  enviarMsg() {

		String mensagem = "test message";

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("ultima", "sim");
		Message message = new Message(mensagem.getBytes(), messageProperties);

		rabbitTemplate.convertAndSend(RabbitMQConfig.NOME_EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);

		return new ResponseEntity<ResponseDTO>(new ResponseDTO("Enviado para fila"), HttpStatus.BAD_REQUEST);

	}

}
