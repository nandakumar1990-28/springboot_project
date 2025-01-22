package com.myproject.myproject.service;

import java.util.List;

import com.myproject.myproject.error.DepartmentNotFoundException;
import com.myproject.myproject.model.Department;

public interface DepartmentService {

	public Department saveDepartment(Department department);

	public List<Department> getAllDepartments();

	public Department getDepartmentById(Long id) throws DepartmentNotFoundException;

	public void deleteDepartmentById(Long id);

	public Department updateDepartment(Long id, Department department);

	public Department getDepartmentByName(String name);

}
