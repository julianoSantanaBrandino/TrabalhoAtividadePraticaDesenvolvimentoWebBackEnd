package com.example.demorest.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demorest.entity.Tarefa;
import com.example.demorest.repository.TarefaRepository;

@RestController
@RequestMapping({ "/tarefas" }) 
public class TarefaController {
	
	
	private final TarefaRepository repository;

	
	TarefaController(TarefaRepository tarefaRepository) {
		this.repository = tarefaRepository;
	}

	
	@PostMapping
	public ResponseEntity<Tarefa> create(@RequestBody Tarefa tarefa) {
		Tarefa novaTarefa = repository.save(tarefa);
		return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
	}


	@GetMapping
	public List<Tarefa> findAll() {
		return repository.findAll();
	}

	@GetMapping(path = { "/{id}" })
	public ResponseEntity<Tarefa> findById(@PathVariable long id) {
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record)) 
				.orElse(ResponseEntity.notFound().build()); 
	}


	@PutMapping(value = "/{id}")
	public ResponseEntity<Tarefa> update(@PathVariable("id") long id, @RequestBody Tarefa tarefa) {
		return repository.findById(id).map(record -> {
			
			record.setNome(tarefa.getNome());
			record.setDataEntrega(tarefa.getDataEntrega());
			record.setResponsavel(tarefa.getResponsavel()); 
			
			Tarefa updated = repository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	
	@DeleteMapping(path = { "/{id}" })
	public ResponseEntity<?> delete(@PathVariable long id) {
		return repository.findById(id).map(record -> {
			repository.deleteById(id);
			return ResponseEntity.ok().build(); 
		}).orElse(ResponseEntity.notFound().build()); 
	}
}