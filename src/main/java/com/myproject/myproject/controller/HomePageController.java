package com.myproject.myproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.myproject.myproject.error.DepartmentNotFoundException;
import com.myproject.myproject.model.Department;
import com.myproject.myproject.service.DepartmentService;

@Controller
public class HomePageController {
	
	@Autowired
	private DepartmentService service;
	
	@GetMapping("/")
	public String viewHomePage(Model model)
	{
		model.addAttribute("departmentList", service.getAllDepartments());
		return "index";
	}
	
	@GetMapping("/error/403")
	public String error_403()
	{
		return "403";
	}
	
	@PreAuthorize(value = "ADMIN")
	@GetMapping("/departmentForm/new")
	public String showNewDepartmentForm(Model model)
	{
		Department dept = new Department();
		model.addAttribute("department", dept);
		return "newDepartmentForm";
	}
	
	@PostMapping("/saveDepartment")
	public String saveDepartment(@ModelAttribute Department department)
	{
		service.saveDepartment(department);
		return "redirect:/";
	}
	
	@PreAuthorize(value = "ADMIN")
	@GetMapping(("/departmentForm/update/{id}"))
	public String updateDepartment(@PathVariable(value = "id") Long id, Model model) throws DepartmentNotFoundException
	{
		Department dept = service.getDepartmentById(id);
		model.addAttribute("department", dept);
		return "update_department";
	}
	
	@PostMapping("/updateDepartment")
	public String updateDepartment(@ModelAttribute Department department)
	{
		service.updateDepartment(department.getDepartmentId(), department);
		return "redirect:/";
	}
	
	@PreAuthorize(value = "ADMIN")
	@GetMapping("/departmentForm/delete/{id}")
	public String deleteDepartment(@PathVariable("id") Long id)
	{
		service.deleteDepartmentById(id);
		return "redirect:/";
	}
}
