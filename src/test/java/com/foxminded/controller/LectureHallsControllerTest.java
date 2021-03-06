package com.foxminded.controller;

import com.foxminded.service.LectureHallService;
import com.foxminded.service.dto.GroupDTO;
import com.foxminded.service.dto.LectureHallDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(LectureHallsController.class)
class LectureHallsControllerTest {
    @MockBean
    private LectureHallService lectureHallService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveEmptyHall_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(post("/halls").flashAttr("hall",new LectureHallDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void updateHallWithEmptyName_thenShouldBeRedirectToErrorPage() throws Exception{
        mockMvc.perform(patch("/halls/1").flashAttr("hall",new LectureHallDTO("")))
                .andExpect(view().name("redirect:/exceptions/validation"));
    }

    @Test
    void update_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(lectureHallService).update(new LectureHallDTO(1L,"GK"));
        mockMvc.perform(patch("/halls/1").flashAttr("hall",new LectureHallDTO("GK")));
        verify(lectureHallService,times(1)).update(new LectureHallDTO(1L,"GK"));
    }

    @Test
    void post_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        when(lectureHallService.save(new LectureHallDTO("bolishaya"))).thenReturn(new LectureHallDTO(1L,"bolishaya"));
        mockMvc.perform(post("/halls").flashAttr("hall",new LectureHallDTO("bolishaya")));
        verify(lectureHallService,times(1)).save(new LectureHallDTO("bolishaya"));
    }

    @Test
    void delete_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        doNothing().when(lectureHallService).delete(new LectureHallDTO(1L));
        mockMvc.perform(delete("/halls/1"));
        verify(lectureHallService,times(1)).delete(new LectureHallDTO(1L));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeOneCallWithoutError() throws Exception{
        given(lectureHallService.findById(new LectureHallDTO(1L))).willReturn(new LectureHallDTO(1L,"202"));
        doNothing().when(lectureHallService).update(new LectureHallDTO(1L,"404"));
        mockMvc.perform(get("/halls/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("halls/edit"));
    }

    @Test
    void findAll_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        List<LectureHallDTO> halls = new ArrayList<>();
        halls.add(new LectureHallDTO());
        halls.add(new LectureHallDTO());
        when(lectureHallService.findAll()).thenReturn((List) halls);
        mockMvc.perform(get("/halls"))
                .andExpect(status().isOk())
                .andExpect(view().name("halls/find-all"))
                .andExpect(model().attribute("halls",hasSize(2)));
    }

    @Test
    void findById_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        LectureHallDTO lectureHallDTO = new LectureHallDTO(1L);
        when(lectureHallService.findById(lectureHallDTO)).thenReturn(new LectureHallDTO());
        mockMvc.perform(get("/halls/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("halls/find-by-id"))
                .andExpect(model().attribute("hall",instanceOf(LectureHallDTO.class)));
    }

    @Test
    void newHall_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        verifyZeroInteractions(lectureHallService);
        mockMvc.perform(get("/halls/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("halls/new"))
                .andExpect(model().attribute("hall",instanceOf(LectureHallDTO.class)));
    }

    @Test
    void edit_WhenAllIsOk_thenShouldBeRightStatus() throws Exception{
        LectureHallDTO lectureHallDTO = new LectureHallDTO(1L);
        when(lectureHallService.findById(lectureHallDTO)).thenReturn(new LectureHallDTO());
        mockMvc.perform(get("/halls/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("halls/edit"))
                .andExpect(model().attribute("hall",instanceOf(LectureHallDTO.class)));
    }
}