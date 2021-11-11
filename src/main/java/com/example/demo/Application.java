package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    // Update 이후 Configuration에서의 Bean 등록 뿐 아니라 일반 클래스에서의 Bean 등록이 가능해짐.
    // Return 에서 바로 실행 혹은 함수 호출 가능
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args -> {
            Student kang = new Student(
                    "Kang",
                    "woosung",
                    "spring@study",
                    27);

            Student maria = new Student(
                    "Maria",
                    "Jones",
                    "maria.jones@amigoscode.edu",
                    21
            );

            Student maria2 = new Student(
                    "Maria",
                    "Jones",
                    "maria2.jones@amigoscode.edu",
                    25
            );

            Student ahmed = new Student(
                    "Ahmed",
                    "Ali",
                    "ahmed.ali@amigoscode.edu",
                    18
            );

            // List의 형태로 저장
            studentRepository.saveAll(List.of(kang, maria, maria2, ahmed));

            studentRepository
                    .findStudentByEmail("spring@study")
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Student with emailspring@study not found"));

            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual(
                    "Maria",
                    21
            ).forEach(System.out::println);

            studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
                    "Maria",
                    21
            ).forEach(System.out::println);

            System.out.println("Deleting Ahmed");
            System.out.println(studentRepository.deleteStudentById(4L));
        };
    }

}
