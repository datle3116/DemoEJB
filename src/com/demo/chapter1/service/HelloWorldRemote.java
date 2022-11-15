package com.demo.chapter1.service;

import java.util.List;

import javax.ejb.Remote;

import com.demo.chapter1.entity.Todo;

@Remote
public interface HelloWorldRemote {
	public List<Todo> findAll();
	public Todo findById(int id);
	public boolean add(Todo todo);
	public boolean edit(Todo todo);
	public boolean delete(Todo todo);
}
