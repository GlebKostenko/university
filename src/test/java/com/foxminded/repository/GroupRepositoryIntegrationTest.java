package com.foxminded.repository;

import com.foxminded.model.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupRepositoryIntegrationTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void whenFindById_thenReturnGroup() {
        Group group = new Group("fupm");
        groupRepository.save(group);
        List<Group> groups = (List<Group>) groupRepository.findAll();
        assertTrue(groups.contains(group));
    }

    @Test
    public void whenUpdate_thenShouldBeNewGroupName() {
        Group group = new Group("faki");
        groupRepository.save(group);
        List<Group> groups = (List<Group>) groupRepository.findAll();
        groups.stream().filter(x->x.getGroupName().equals("faki")).forEach(x -> groupRepository.save(new Group(x.getGroupId(),"fopf")));
        groups = (List<Group>) groupRepository.findAll();
        assertTrue(groups.stream().filter(x->x.getGroupName().equals("faki")).collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void whenDeleteById_thenShouldBeNoGroup() {
        Group group = groupRepository.save(new Group("fbmf"));
        groupRepository.delete(new Group(group.getGroupId()));
        Throwable exception = assertThrows(NoSuchElementException.class, () -> groupRepository.findById(group.getGroupId()).get());
    }

    @Test
    public void whenFindAll_thenShouldBeNotEmptyResultList() {
        Group group = new Group("frtk");
        groupRepository.save(group);
        List<Group> groups = (List<Group>) groupRepository.findAll();
        assertTrue(!groups.isEmpty());
    }

    @Test
    public void findByNameAndUpdate(){
        Group group = groupRepository.save(new Group("FKN"));
        Map<String,String> dataForUpdate = new HashMap<>();
        dataForUpdate.put("group_name","Fivt");
        groupRepository.findByNameAndUpdate(group.getGroupName(),dataForUpdate);
        List<Group> groups = (List<Group>) groupRepository.findAll();
        assertTrue(groups.stream().filter(x -> x.getGroupName().equals("FKN")).collect(Collectors.toList()).isEmpty());
    }
}