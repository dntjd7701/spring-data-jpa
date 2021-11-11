package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.email=?1")
    Optional<Student> findStudentByEmail(String email);

    @Query("select s from Student s where s.firstName=?1 and s.age >= ?2")
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(String firstName, Integer age);


    @Query(value = "select s from Student s where s.firstName=:firstName and s.age >= :age")
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqualNative(
            @Param("firstName") String firstName,
            @Param("age") Integer age);

    //@Modifying은  @Query Annotation으로 작성 된 변경, 삭제 쿼리 메서드를 사용할 때 필요합니다.
    // 즉, 조회 쿼리를 제외하고, 데이터에 변경이 일어나는 INSERT, UPDATE, DELETE, DDL 에서 사용합니다. 주로 벌크 연산 시에 사용됩니다.
    //(기본적으로 제공되는 쿼리 메서드나 메서드 네이밍으로 파생되는 쿼리 메서드에서는 적용이 되지 않습니다.)
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);

}
