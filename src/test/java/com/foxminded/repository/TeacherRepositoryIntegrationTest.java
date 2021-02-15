package com.foxminded.repository;

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
    private TestEntityManager entityManager;
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void whenFindById_thenReturnTeacher() {
        TeacherDTO teacherDTO = new TeacherDTO("Lev","Landau");
        entityManager.persist(teacherDTO);
        entityManager.flush();
        List<TeacherDTO> teachers = (List<TeacherDTO>) teacherRepository.findAll();
        assertTrue(!teachers.stream().filter(x -> x.getFirstName().equals("Lev") && x.getLastName().equals("Landau")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenUpdate_thenShouldBeNewTeacherName() {
        TeacherDTO teacherDTO = new TeacherDTO("Nikolay","Semenov");
        entityManager.persist(teacherDTO);
        entityManager.flush();
        List<TeacherDTO> teachers = (List<TeacherDTO>) teacherRepository.findAll();
        teachers.stream().filter(x->x.getFirstName().equals("Nikolay") && x.getLastName().equals("Semenov")).forEach(x -> teacherRepository.save(new TeacherDTO(x.getTeacherId(),"Alexander","Evgeniev")));
        teachers = (List<TeacherDTO>) teacherRepository.findAll();
        assertTrue(teachers.stream().filter(x ->  x.getFirstName().equals("Nikolay") && x.getLastName().equals("Semenov")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoTeacher() {
        TeacherDTO teacherDTO = new TeacherDTO("Petr","kapronov");
        entityManager.persist(teacherDTO);
        entityManager.flush();
        teacherRepository.delete(new TeacherDTO(teacherDTO.getTeacherId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> teacherRepository.findById(teacherDTO.getTeacherId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        TeacherDTO teacherDTO = new TeacherDTO("Anton","Kovalev");
        entityManager.persist(teacherDTO);
        entityManager.flush();
        List<TeacherDTO> teachers = (List<TeacherDTO>) teacherRepository.findAll();
        assertTrue(!teachers.isEmpty());
    }
}
