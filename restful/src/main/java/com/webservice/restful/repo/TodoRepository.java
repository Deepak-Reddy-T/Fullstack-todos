package com.webservice.restful.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webservice.restful.todo.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

}
