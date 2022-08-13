package br.com.eduardo.scheduller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.eduardo.config.RabbitMQConfig;
import br.com.eduardo.dto.ResultadoVotoDTO;
import br.com.eduardo.entity.Sessao;
import br.com.eduardo.service.impl.SessaoService;
import br.com.eduardo.service.impl.VotacaoService;

@Component
public class EnviarVotosFinalizados {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private SessaoService sessaoService;

	@Autowired
	private VotacaoService votacaoService;

	@Scheduled(cron = "0 0/1 * * * *")
	public void executar() {

		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fim = LocalDateTime.now().minusDays(1);

		List<Sessao> findAllByFimBetween = new ArrayList(); //.findAllByFimBetween(inicio, fim);

		for (Sessao s : findAllByFimBetween) {

			ResultadoVotoDTO resultadoVotacao = votacaoService.resultadoVotacao(s.getId());

			rabbitTemplate.convertAndSend(RabbitMQConfig.NOME_EXCHANGE, RabbitMQConfig.ROUTING_KEY, resultadoVotacao);
		}

	}
}
