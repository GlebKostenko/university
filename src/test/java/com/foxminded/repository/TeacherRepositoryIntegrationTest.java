package com.foxminded.repository;

import com.foxminded.model.Teacher;
import com.foxminded.service.dto.SubjectDTO;
import com.foxminded.service.dto.TeacherDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TeacherRepositoryIntegrationTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void whenFindById_thenReturnTeacher() {
        Teacher teacher = teacherRepository.save(new Teacher("Lev","Landau"));
        List<Teacher> teachers = (List<Teacher>) teacherRepository.findAll();
        assertTrue(teachers.contains(teacher));
    }

    @Test
    public void whenUpdate_thenShouldBeNewTeacherName() {
        Teacher teacher = teacherRepository.save(new Teacher("Nikolay","Semenov"));
        List<Teacher> teachers = (List<Teacher>) teacherRepository.findAll();
        teachers.stream().filter(x->x.getTeacherId().equals(teacher.getTeacherId())).forEach(x -> teacherRepository.save(new Teacher(x.getTeacherId(),"Alexander","Evgeniev")));
        teachers = (List<Teacher>) teacherRepository.findAll();
        assertTrue(teachers.stream().filter(x ->  x.getTeacherId().equals(teacher.getTeacherId())).findAny().get().getFirstName().equals("Alexander"));
    }

    @Test
    public void whenDeleteById_thenShouldBeNoTeacher() {
        Teacher teacher = teacherRepository.save(new Teacher("Petr","Kapronov"));
        teacherRepository.delete(new Teacher(teacher.getTeacherId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> teacherRepository.findById(teacher.getTeacherId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        Teacher teacher = teacherRepository.save(new Teacher("Anton","Kovalev"));
        List<Teacher> teachers = (List<Teacher>) teacherRepository.findAll();
        assertTrue(!teachers.isEmpty());
    }
}
