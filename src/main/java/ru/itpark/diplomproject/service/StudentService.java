package ru.itpark.diplomproject.service;

import org.springframework.stereotype.Service;
import ru.itpark.diplomproject.domain.Student;
import ru.itpark.diplomproject.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAllStudents(){
        return studentRepository.findAllStudents();
    }
}
