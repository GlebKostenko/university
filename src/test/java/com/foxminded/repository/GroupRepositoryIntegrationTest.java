package com.foxminded.repository;

import com.foxminded.service.dto.GroupDTO;
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
public class GroupRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void whenFindById_thenReturnGroup() {
        GroupDTO group = new GroupDTO("fupm");
        entityManager.persist(group);
        entityManager.flush();
        List<GroupDTO> groups = (List<GroupDTO>) groupRepository.findAll();
        assertTrue(!groups.stream().filter(x->x.getGroupName().equals("fupm")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenUpdate_thenShouldBeNewGroupName() {
        GroupDTO group = new GroupDTO("faki");
        entityManager.persist(group);
        entityManager.flush();
        List<GroupDTO> groups = (List<GroupDTO>) groupRepository.findAll();
        groups.stream().filter(x->x.getGroupName().equals("faki")).forEach(x -> groupRepository.save(new GroupDTO(x.getGroupId(),"fopf")));
        groups = (List<GroupDTO>) groupRepository.findAll();
        assertTrue(groups.stream().filter(x->x.getGroupName().equals("faki")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoGroup() {
        GroupDTO group = new GroupDTO("fbmf");
        entityManager.persist(group);
        entityManager.flush();
        groupRepository.delete(new GroupDTO(1L));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> groupRepository.findById(1L).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        GroupDTO group = new GroupDTO("frtk");
        entityManager.persist(group);
        entityManager.flush();
        List<GroupDTO> groups = (List<GroupDTO>) groupRepository.findAll();
        assertTrue(!groups.isEmpty());
    }
}