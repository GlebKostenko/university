package com.foxminded.controller;

import com.foxminded.model.Group;
import com.foxminded.model.Student;
import com.foxminded.repository.GroupRepository;
import com.foxminded.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentsRestControllerST {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    StudentRepository studentRepository;

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("Fizicheskaya"));
        Group groupForUpdate = groupRepository.save(new Group("Chimicheskaya"));
        Student student = studentRepository.save(new Student("Alexey","Romanov",new Group(group.getGroupId())));
        String URL = "/students-rest/" + student.getStudentId();
        mockMvc.perform(patch(URL)
                .param("first-name","Alexey")
                .param("last-name","Kovalev")
                .param("group","Chimicheskaya"))
                .andExpect(status().isOk());
        assertTrue(studentRepository.findById(student.getStudentId()).get().getGroup().equals(groupForUpdate));
    }
    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("VMK"));
        mockMvc.perform(post("/students-rest")
                .param("first-name","Igor")
                .param("last-name","Artamonov")
                .param("group","VMK"))
                .andExpect(jsonPath("$.firstName").value("Igor"))
                .andExpect(jsonPath("$.lastName").value("Artamonov"))
                .andExpect(jsonPath("$.group.groupId").value(group.getGroupId()));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        Group group = groupRepository.save(new Group("Economic"));
        Student student = studentRepository.save(new Student("Uriy","Shapkin",new Group(group.getGroupId())));
        String URL = "/students-rest/" + student.getStudentId();
        mockMvc.perform(delete(URL))
                .andExpect(status().isOk());
        assertTrue(studentRepository.findById(student.getStudentId()).isEmpty());
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Group group = groupRepository.save(new Group("jurisprudence"));
        Student student = studentRepository.save(new Student("Anton","Konavalov",new Group(group.getGroupId())));
        mockMvc.perform(get("/students-rest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]",hasSize(greaterThan(0))));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        Group group = groupRepository.save(new Group("foreign languages"));
        Student student = studentRepository.save(new Student("Maria","Savelieva",new Group(group.getGroupId())));
        String URL =  "/students-rest/" + student.getStudentId();
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Maria"))
                .andExpect(jsonPath("$.lastName").value("Savelieva"))
                .andExpect(jsonPath("$.group.groupName").value("foreign languages"));
    }

}
