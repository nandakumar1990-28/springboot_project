package com.myproject.myproject.helper;


import org.springframework.batch.item.ItemProcessor;

import com.myproject.myproject.model.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item) throws Exception {
		String firstName = item.getFirstName().toUpperCase();
		String lastName = item.getLastName().toUpperCase();
		return new Student(firstName,lastName);
	}

}
