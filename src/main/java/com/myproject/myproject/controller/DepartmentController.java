package com.myproject.myproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.myproject.error.DepartmentNotFoundException;
import com.myproject.myproject.model.Department;
import com.myproject.myproject.service.DepartmentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@CacheConfig(cacheNames = "department")
public class DepartmentController {
	
	
	@Autowired
	private DepartmentService service;
	
	private Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@PostMapping("/departments")
	public Department saveDepartment(@Valid @RequestBody Department department)
	{
		logger.info("Inside saveDepartMent method");
		return service.saveDepartment(department);
	}
	
	
	@Cacheable(cacheNames = "department")
	@GetMapping("/departments")
	public List<Department> getAllDepartments()
	{
		logger.info("Inside getAllDepartments method");
		return service.getAllDepartments();
	}
	
	
	@Cacheable(key = "#id")
	@GetMapping("/departments/{id}")
	public Department getDepartmentById(@PathVariable Long id) throws DepartmentNotFoundException
	{
		return service.getDepartmentById(id);
	}
	
	
	@CacheEvict(key = "#id")
	@DeleteMapping("/departments/{id}")
	public String deleteDepartmentById(@PathVariable Long id)
	{
		service.deleteDepartmentById(id);
		return "Department deleted successfully.";
	}
	
	@PutMapping("/departments/{id}")
	public Department updateDepartment(@PathVariable Long id, @RequestBody Department department)
	{
		return service.updateDepartment(id,department);
	}
	
	@GetMapping("/departments/name/{name}")
	public Department getDepartmentByName(@PathVariable String name)
	{
		return service.getDepartmentByName(name);
	}
	
	@GetMapping("/csrf")
	public CsrfToken getToken(HttpServletRequest request)
	{
		return (CsrfToken)request.getAttribute("_csrf");
	}
	{
		
	}
	
}
