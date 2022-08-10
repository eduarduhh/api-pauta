package br.com.eduardo.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import br.com.eduardo.exception.BusinessException;
import br.com.eduardo.exception.IntegrationException;

@Service
public class CpfService {
   
	
    @Autowired
	private RestTemplate restTemplate;

    private final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    
    @Value("${service.cpf.url}") 
    private String urlCpf;

   
    
    public boolean isAbleToVote(String cpf) {
        try {
            String url = urlCpf+ cpf;
            ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            return response.getBody().equals(ABLE_TO_VOTE);
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode()== HttpStatus.NOT_FOUND)
                throw new BusinessException("CPF Inválido");

            throw new IntegrationException("Ocorreu um erro na integração");
        }
    }

}