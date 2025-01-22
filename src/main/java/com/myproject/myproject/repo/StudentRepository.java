package com.myproject.myproject.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.myproject.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
