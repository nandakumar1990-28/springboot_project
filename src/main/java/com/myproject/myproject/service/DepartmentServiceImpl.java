package com.myproject.myproject.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.myproject.error.DepartmentNotFoundException;
import com.myproject.myproject.model.Department;
import com.myproject.myproject.repo.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository repo;
	
	@Override
	public Department saveDepartment(Department department) {
		return repo.save(department);
	}

	@Override
	public List<Department> getAllDepartments() {
		return repo.findAll();
	}

	@Override
	
	public Department getDepartmentById(Long id) throws DepartmentNotFoundException {
		Optional<Department> dept = repo.findById(id);
		if(!dept.isPresent())
		{
			throw new DepartmentNotFoundException("Department not found");
		}
		return dept.get();
	}

	@Override
	public void deleteDepartmentById(Long id) {
		repo.deleteById(id);
		
	}

	@Override
	public Department updateDepartment(Long id, Department department) {
		Department dept = repo.findById(id).get();
		
		if(Objects.nonNull(department.getDepartmentName()) || "".equalsIgnoreCase(department.getDepartmentName()))
		{
			dept.setDepartmentName(department.getDepartmentName());
		}
		
		if(Objects.nonNull(department.getDepartmentAddress()) || "".equalsIgnoreCase(department.getDepartmentAddress()))
		{
			dept.setDepartmentAddress(department.getDepartmentAddress());
		}
		
		if(Objects.nonNull(department.getDepartmentCode()) || "".equalsIgnoreCase(department.getDepartmentCode()))
		{
			dept.setDepartmentCode(department.getDepartmentCode());
		}
		return repo.save(dept);
	}

	@Override
	public Department getDepartmentByName(String name) {
		return repo.findByDepartmentName(name);
	}


}
