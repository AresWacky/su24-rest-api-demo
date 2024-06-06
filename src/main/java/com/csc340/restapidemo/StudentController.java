package com.csc340.restapidemo;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping("/all")
    public Object getAllStudents() {
        try {
            List<Student> students = StudentFileUtil.readStudentsFromFile();
            return students;
        } catch (IOException e) {
            return "Error reading student data.";
        }
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        try {
            List<Student> students = StudentFileUtil.readStudentsFromFile();
            Optional<Student> student = students.stream().filter(s -> s.getId() == id).findFirst();
            return student.orElse(null); // Return null if student not found
        } catch (IOException e) {

            return null;
        }
    }

    @PostMapping("/create")
    public Object createStudent(@RequestBody Student student) {
        try {
            List<Student> students = StudentFileUtil.readStudentsFromFile();
            students.add(student);
            StudentFileUtil.writeStudentsToFile(students);
            return students;
        } catch (IOException e) {
            return "Error writing student data.";
        }
    }

    @PutMapping("/update/{id}")
    public Object updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        try {
            List<Student> students = StudentFileUtil.readStudentsFromFile();
            Optional<Student> existingStudentOpt = students.stream().filter(s -> s.getId() == id).findFirst();
            if (existingStudentOpt.isPresent()) {
                Student existingStudent = existingStudentOpt.get();
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setMajor(updatedStudent.getMajor());
                existingStudent.setGpa(updatedStudent.getGpa());
                StudentFileUtil.writeStudentsToFile(students);
                return existingStudent;
            } else {
                return "Student not found.";
            }
        } catch (IOException e) {
            return "Error updating student data.";
        }
    }

    @DeleteMapping("/delete/{id}")
    public Object deleteStudent(@PathVariable int id) {
        try {
            List<Student> students = StudentFileUtil.readStudentsFromFile();
            boolean removed = students.removeIf(s -> s.getId() == id);
            if (removed) {
                StudentFileUtil.writeStudentsToFile(students);
                return students;
            } else {
                return "Student not found.";
            }
        } catch (IOException e) {
            return "Error deleting student data.";
        }
    }
}
