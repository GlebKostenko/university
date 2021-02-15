package com.foxminded.repository;

import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.LectureHallDTO;
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
public class HallRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private LectureHallRepository lectureHallRepository;
    @Test
    public void whenFindById_thenReturnHall() {
        LectureHallDTO lectureHallDTO = new LectureHallDTO("202");
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        List<LectureHallDTO> halls = (List<LectureHallDTO>) lectureHallRepository.findAll();
        assertTrue(!halls.stream().filter(x->x.getHallName().equals("202")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenUpdate_thenShouldBeNewHallName() {
        LectureHallDTO lectureHallDTO = new LectureHallDTO("GK");
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        List<LectureHallDTO> halls = (List<LectureHallDTO>) lectureHallRepository.findAll();
        halls.stream().filter(x->x.getHallName().equals("GK")).forEach(x -> lectureHallRepository.save(new LectureHallDTO(x.getHallId(),"LK")));
        halls = (List<LectureHallDTO>) lectureHallRepository.findAll();
        assertTrue(halls.stream().filter(x->x.getHallName().equals("GK")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoHall() {
        LectureHallDTO lectureHallDTO = new LectureHallDTO("Glavnaya");
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        lectureHallRepository.delete(new LectureHallDTO(1L));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> lectureHallRepository.findById(1L).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        LectureHallDTO lectureHallDTO = new LectureHallDTO("Bolishaya");
        entityManager.persist(lectureHallDTO);
        entityManager.flush();
        List<LectureHallDTO> halls = (List<LectureHallDTO>) lectureHallRepository.findAll();
        assertTrue(!halls.isEmpty());
    }
}
