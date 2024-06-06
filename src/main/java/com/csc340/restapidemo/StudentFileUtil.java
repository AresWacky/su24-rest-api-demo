package com.csc340.restapidemo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentFileUtil {
    private static final String FILE_PATH = "src/main/resources/students.txt";

    public static List<Student> readStudentsFromFile() throws IOException {
        File file = new File(FILE_PATH);
        List<Student> students = new ArrayList<>();
        if (!file.exists()) {
            return students;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student();
                student.setId(Integer.parseInt(data[0]));
                student.setName(data[1]);
                student.setMajor(data[2]);
                student.setGpa(Double.parseDouble(data[3]));
                students.add(student);
            }
        }

        return students;
    }

    public static void writeStudentsToFile(List<Student> students) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Student student : students) {
                bw.write(student.getId() + "," + student.getName() + "," + student.getMajor() + "," + student.getGpa());
                bw.newLine();
            }
        }
    }
}

