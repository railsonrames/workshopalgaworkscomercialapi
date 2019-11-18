package com.algaworks.comercial.controller;

//import java.math.BigDecimal;
//import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.comercial.model.Oportunidade;
import com.algaworks.comercial.repository.OportunidadeRepository;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

	@Autowired
	private OportunidadeRepository oportunidadeRepository;

	@GetMapping
	public List<Oportunidade> listar() {
		return oportunidadeRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		Optional<Oportunidade> oportunidade = oportunidadeRepository.findById(id);

		if (oportunidade.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(oportunidade.get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidadeRepository
				.findByDescricaoAndNomeProspecto(oportunidade.getDescricao(), oportunidade.getNomeProspecto());
		
		if (oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma oportunidade para este prospecto com a mesma descrição");
		}
		return oportunidadeRepository.save(oportunidade);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long id) {
		Boolean oportunidadeExistente = oportunidadeRepository.existsById(id);
		
		if (!oportunidadeExistente) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe oportunidade cadastrada com o id informado.");
		}
		
		oportunidadeRepository.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Oportunidade alterar(@PathVariable Long id, @Valid @RequestBody Oportunidade oportunidade) {
		Oportunidade oportunidadeExistente = oportunidadeRepository.getOne(id);
		if (oportunidadeExistente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe oportunidade cadastrada com o id informado.");
		}
		
		BeanUtils.copyProperties(oportunidade, oportunidadeExistente, "id");
		
		return oportunidadeRepository.save(oportunidadeExistente);		
	}

}
