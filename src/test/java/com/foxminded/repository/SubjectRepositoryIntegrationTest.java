package com.foxminded.repository;

import com.foxminded.model.Subject;
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
public class SubjectRepositoryIntegrationTest {
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void whenFindById_thenReturnSubject() {
        Subject subject = subjectRepository.save(new Subject("Design"));
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        assertTrue(subjects.contains(subject));
    }

    @Test
    public void whenUpdate_thenShouldBeNewSubjectName() {
        Subject subject = subjectRepository.save(new Subject("Calculus"));
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        subjects.stream().filter(x->x.getSubjectId().equals(subject.getSubjectId()))
                .forEach(x -> subjectRepository.save(new Subject(x.getSubjectId(),"English")));
        subjects = (List<Subject>) subjectRepository.findAll();
        assertTrue(subjects.stream()
                .filter(x->x.getSubjectId().equals(subject.getSubjectId()))
                .findAny().get().getSubjectName().equals("English"));
    }

    @Test
    public void whenDeleteById_thenShouldBeNoSubject() {
        Subject subject = subjectRepository.save(new Subject("Chemistry"));
        subjectRepository.delete(new Subject(subject.getSubjectId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> subjectRepository.findById(subject.getSubjectId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        Subject subject = subjectRepository.save(new Subject("Economics"));
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        assertTrue(!subjects.isEmpty());
    }
}
