package com.webservice.restful;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.webservice.restful.repo.TodoRepository;
import com.webservice.restful.service.TodoService;
import com.webservice.restful.todo.Todo;


@CrossOrigin(origins="http://localhost:4200")
@RestController
public class TodoResource {
	
	@Autowired
	private TodoRepository todoRepository;
	private TodoService todoService;
	
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username){
		return todoRepository.findAll();
		
	}
	@GetMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> getByid(@PathVariable String username,@PathVariable int id){
		try {
		Todo todo =todoRepository.findById(id).get();
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("Delete/users/{username}/todos/{id}")
	public void deleteTodo(@PathVariable String username,@PathVariable int id) {
		todoRepository.deleteById(id);
		
	}
	
	@PutMapping("Update/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable String username,@PathVariable int id,@RequestBody Todo todo) {
		try {
//			Todo todo1=todoRepository.findById(id).get();
			
			Todo todo1=todoRepository.save(todo);
			return new ResponseEntity<Todo>(todo,HttpStatus.OK);
		}
		catch(NoSuchElementException e)  {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/users/{username}/todos")
	public ResponseEntity<?> saveTodo(@PathVariable String username,@RequestBody Todo todo) {
		try {
			Todo createdTodo=todoRepository.save(todo);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		catch(NoSuchElementException e)  {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
