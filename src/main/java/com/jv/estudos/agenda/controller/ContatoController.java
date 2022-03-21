package com.jv.estudos.agenda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jv.estudos.agenda.model.entity.Contato;
import com.jv.estudos.agenda.model.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Contato salvar(@RequestBody Contato contato) {
		return contatoRepository.save(contato);
	}
	
	@GetMapping
	public List<Contato> listar() {
		return contatoRepository.findAll();
	}
	
	@PatchMapping("/{id}/favorito")
	public void favoritar(@PathVariable Integer id, @RequestBody Boolean favorito) {
		Optional<Contato> optContato = contatoRepository.findById(id);
		
		optContato.ifPresent(c -> {
			c.setFavorito(favorito);
			contatoRepository.save(c);
		});
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Integer id) {
		contatoRepository.deleteById(id);
	}
	
}
