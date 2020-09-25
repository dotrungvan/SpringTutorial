package com.example.SpringTutorial.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringTutorial.Model.Student;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	private static List<Student> lstStudents = Arrays.asList(new Student("1", "Student1"), new Student("2", "Student2"),
			new Student("3", "Student3"));

	@GetMapping(value = "", produces = "application/json")
	public List<Student> getAllStudents() {
		return lstStudents;
	}
}
