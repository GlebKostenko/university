package com.foxminded.repository;

import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.StudentDTO;
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
public class StudentRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    StudentRepository studentRepository;

    @Test
    public void whenFindById_thenReturnStudent() {
        GroupDTO group = new GroupDTO("fupm");
        entityManager.persist(group);
        entityManager.flush();
        StudentDTO studentDTO = new StudentDTO("Sergey",
                "Brin",
                new GroupDTO(group.getGroupId()));
        entityManager.persist(studentDTO);
        entityManager.flush();
        List<StudentDTO> students = (List<StudentDTO>) studentRepository.findAll();
        assertTrue(students.contains(studentDTO));
    }

    @Test
    public void whenUpdate_thenShouldBeNewStudentName() {
        GroupDTO group = new GroupDTO("faki");
        entityManager.persist(group);
        entityManager.flush();
        GroupDTO group1 = new GroupDTO("fbmf");
        entityManager.persist(group1);
        entityManager.flush();
        StudentDTO studentDTO = new StudentDTO("Alexey",
                "Romanov",
                new GroupDTO(group.getGroupId()));
        entityManager.persist(studentDTO);
        entityManager.flush();
        List<StudentDTO> students = (List<StudentDTO>) studentRepository.findAll();
        students.stream().filter(x->x.getStudentId().equals(studentDTO.getStudentId())).forEach(x -> studentRepository.save(new StudentDTO(studentDTO.getStudentId(),"Victor","Dubrov",new GroupDTO(group1.getGroupId()))));
        students = (List<StudentDTO>) studentRepository.findAll();
        assertTrue(students.stream().filter(x->x.getGroup().getGroupName().equals("faki")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoStudent() {
        GroupDTO group = new GroupDTO("mehmat");
        entityManager.persist(group);
        entityManager.flush();
        StudentDTO studentDTO = new StudentDTO("Anton",
                "Kovalev",
                new GroupDTO(group.getGroupId()));
        entityManager.persist(studentDTO);
        entityManager.flush();
        studentRepository.delete(new StudentDTO(studentDTO.getStudentId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> studentRepository.findById(studentDTO.getStudentId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        GroupDTO group = new GroupDTO("frtk");
        entityManager.persist(group);
        entityManager.flush();
        StudentDTO studentDTO = new StudentDTO("Alexender",
                "Kostylev",
                new GroupDTO(group.getGroupId()));
        entityManager.persist(studentDTO);
        entityManager.flush();
        List<StudentDTO> students = (List<StudentDTO>) studentRepository.findAll();
        assertTrue(!students.isEmpty());
    }
}
