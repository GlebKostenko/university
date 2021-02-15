package com.foxminded.repository;

import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.SubjectDTO;
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
public class SubjectRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void whenFindById_thenReturnSubject() {
        SubjectDTO subjectDTO = new SubjectDTO("Design");
        entityManager.persist(subjectDTO);
        entityManager.flush();
        List<SubjectDTO> subjects = (List<SubjectDTO>) subjectRepository.findAll();
        assertTrue(!subjects.stream().filter(x->x.getSubjectName().equals("Design")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenUpdate_thenShouldBeNewSubjectName() {
        SubjectDTO subjectDTO = new SubjectDTO("Calculus");
        entityManager.persist(subjectDTO);
        entityManager.flush();
        List<SubjectDTO> subjects = (List<SubjectDTO>) subjectRepository.findAll();
        subjects.stream().filter(x->x.getSubjectName().equals("Calculus")).forEach(x -> subjectRepository.save(new SubjectDTO(x.getSubjectId(),"English")));
        subjects = (List<SubjectDTO>) subjectRepository.findAll();
        assertTrue(subjects.stream().filter(x->x.getSubjectName().equals("Calculus")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoSubject() {
        SubjectDTO subjectDTO = new SubjectDTO("Chemistry");
        entityManager.persist(subjectDTO);
        entityManager.flush();
        subjectRepository.delete(new SubjectDTO(subjectDTO.getSubjectId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> subjectRepository.findById(subjectDTO.getSubjectId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        SubjectDTO subjectDTO = new SubjectDTO("Economics");
        entityManager.persist(subjectDTO);
        entityManager.flush();
        List<SubjectDTO> subjects = (List<SubjectDTO>) subjectRepository.findAll();
        assertTrue(!subjects.isEmpty());
    }
}
