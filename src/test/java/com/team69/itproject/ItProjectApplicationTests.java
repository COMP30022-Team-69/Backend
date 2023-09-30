package com.team69.itproject;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.team69.itproject.entities.dto.SongDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team69.itproject.dao.SongDAO;
import com.team69.itproject.entities.vo.SongVO;
import org.springframework.http.MediaType;
@SpringBootTest
@ActiveProfiles("test")
class ItProjectApplicationTests {
    public class SongControllerTests {
        private MockMvc mockMvc;
        private SongDAO songDAO = mock(SongDAO.class);
        @Test
        public void testGetSongList() throws Exception {
            Page<SongDTO> mockPage = mock(Page.class);
            when(songDAO.getSongList(anyInt(), anyInt())).thenReturn(mockPage);

            mockMvc.perform(get("/song/list")
                            .param("page", "1")
                            .param("size", "10"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists());
        }
        public void testGetSongByCategory() throws Exception {
            Page<SongDTO> mockPage = mock(Page.class);
            when(songDAO.getSongByCategory(anyInt(), anyInt(), anyString())).thenReturn(mockPage);

            mockMvc.perform(get("/song/list/category")
                            .param("page", "1")
                            .param("size", "10")
                            .param("category", "testCategory"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists());
        }
        public void testGetSongById() throws Exception {
            SongDTO mockSong = mock(SongDTO.class);
            when(songDAO.getSongById(anyLong())).thenReturn(mockSong);

            mockMvc.perform(get("/song/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").exists());
        }
        public void testAddSong() throws Exception {
            when(songDAO.addSong(any())).thenReturn(true);

            SongVO songVO = new SongVO();
            // 设置 songVO 的字段
            mockMvc.perform(post("/song/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(songVO)))
                    .andExpect(status().isOk());
        }

        public static String asJsonString(final Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
