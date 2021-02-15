package com.foxminded.repository;

import com.foxminded.model.LectureHall;
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
    private LectureHallRepository lectureHallRepository;
    @Test
    public void whenFindById_thenReturnHall() {
        LectureHall lectureHall = new LectureHall("202");
        lectureHallRepository.save(lectureHall);
        List<LectureHall> halls = (List<LectureHall>) lectureHallRepository.findAll();
        assertTrue(halls.contains(lectureHall));
    }

    @Test
    public void whenUpdate_thenShouldBeNewHallName() {
        LectureHall lectureHall= new LectureHall("GK");
        lectureHallRepository.save(lectureHall);
        List<LectureHall> halls = (List<LectureHall>) lectureHallRepository.findAll();
        halls.stream().filter(x->x.getHallName().equals("GK")).forEach(x -> lectureHallRepository.save(new LectureHall(x.getHallId(),"LK")));
        halls = (List<LectureHall>) lectureHallRepository.findAll();
        assertTrue(halls.stream().filter(x->x.getHallName().equals("GK")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoHall() {
        LectureHall lectureHall = lectureHallRepository.save(new LectureHall("Glavnaya"));
        lectureHallRepository.delete(new LectureHall(lectureHall.getHallId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> lectureHallRepository.findById(lectureHall.getHallId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        LectureHall lectureHall = new LectureHall("Bolishaya");
        lectureHallRepository.save(lectureHall);
        List<LectureHall> halls = (List<LectureHall>) lectureHallRepository.findAll();
        assertTrue(!halls.isEmpty());
    }
}
