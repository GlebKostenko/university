package com.foxminded.repository;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StudentRepositoryIntegrationTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void whenFindById_thenReturnStudent() {
        Group group = groupRepository.save(new Group("b07-897"));
        Student student = new Student("Sergey",
                "Brin",
                new Group(group.getGroupId()));
        studentRepository.save(student);
        List<Student> students = (List<Student>) studentRepository.findAll();
        assertTrue(students.contains(student));
    }

    @Test
    public void whenUpdate_thenShouldBeNewStudentName() {
        Group group = groupRepository.save(new Group("b07-103"));
        Group groupNew = groupRepository.save(new Group("bo3-987"));
        Student student = new Student("Alexey",
                "Romanov",
                new Group(group.getGroupId()));
        studentRepository.save(student);
        List<Student> students = (List<Student>) studentRepository.findAll();
        students.stream().filter(x->x.getStudentId().equals(student.getStudentId()))
                .forEach(x -> studentRepository.save(
                        new Student(student.getStudentId(),"Victor","Dubrov",new Group(groupNew.getGroupId()))
                        )
                );
        students = (List<Student>) studentRepository.findAll();
        assertTrue(students.stream()
                .filter(x->x.getStudentId().equals(student.getStudentId()))
                .findAny().get().getGroup().getGroupName().equals("bo3-987"));
    }

    @Test
    public void whenDeleteById_thenShouldBeNoStudent() {
        Group group = groupRepository.save(new Group("b01-001"));
        Student student = new Student("Uriy",
                "Abramov",
                new Group(group.getGroupId()));
        studentRepository.save(student);
        studentRepository.delete(new Student(student.getStudentId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> studentRepository.findById(student.getStudentId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        Group group = groupRepository.save(new Group("b01-202"));
        Student student = new Student("Egor",
                "Kalinin",
                new Group(group.getGroupId()));
        studentRepository.save(student);
        List<Student> students = (List<Student>) studentRepository.findAll();
        assertTrue(!students.isEmpty());
    }
}
