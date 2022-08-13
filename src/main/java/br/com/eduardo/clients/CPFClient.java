package br.com.eduardo.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="cpfClient", url="https://user-info.herokuapp.com/users/")
public interface CPFClient {
	
	@GetMapping("/{cpf}")
	CpfResponse getCpf(@PathVariable String cpf);

}
