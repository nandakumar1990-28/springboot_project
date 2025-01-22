package com.myproject.myproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private Long departmentId;
	
	@NotBlank(message = "Department Name should not be blank")
	@Getter @Setter
	public String departmentName;
	
	@Getter @Setter
	private String departmentAddress;
	
	@Getter @Setter
	private String departmentCode;
	
}
