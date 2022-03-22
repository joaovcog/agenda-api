package com.jv.estudos.agenda.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jv.estudos.agenda.model.entity.Contato;
import com.jv.estudos.agenda.model.repository.ContatoRepository;

@RestController
@RequestMapping("/contatos")
@CrossOrigin("http://localhost:4200")
public class ContatoController {

	@Autowired
	private ContatoRepository contatoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Contato salvar(@RequestBody Contato contato) {
		return contatoRepository.save(contato);
	}
	
	@GetMapping
	public Page<Contato> listar(@RequestParam(value = "page", defaultValue = "0") Integer pagina, @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina) {
		PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina);
		
		return contatoRepository.findAll(pageRequest);
	}
	
	@PatchMapping("/{id}/favorito")
	public void favoritar(@PathVariable Integer id) {
		Optional<Contato> optContato = contatoRepository.findById(id);
		
		optContato.ifPresent(c -> {
			boolean favorito = c.getFavorito() == Boolean.TRUE;
			
			c.setFavorito(!favorito);
			contatoRepository.save(c);
		});
	}
	
	@PutMapping("/{id}/foto")
	public byte[] adicionarFoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo) {
		Optional<Contato> optContato = contatoRepository.findById(id);
		
		return optContato.map(c -> {
			try {
				InputStream is = arquivo.getInputStream();
				byte[] bytes = new byte[(int) arquivo.getSize()];
				IOUtils.readFully(is, bytes);
				c.setFoto(bytes);
				
				contatoRepository.save(c);
				is.close();
				
				return bytes;
			} catch (IOException e) {
				return null;
			}
		}).orElse(null);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Integer id) {
		contatoRepository.deleteById(id);
	}
	
}
