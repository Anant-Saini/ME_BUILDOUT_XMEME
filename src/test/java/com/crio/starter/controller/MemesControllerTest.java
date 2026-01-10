package com.crio.starter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.service.interfaces.MemesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class MemesControllerTest {

  @MockBean private MemesService memeService;

  @Autowired private MockMvc mvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  public void createMeme_ReturnCreatedStatusAndId() throws Exception {

    // Arrange
    MemeEntity memeEntity = new MemeEntity();
    memeEntity.setName("Anant Saini");
    memeEntity.setUrl("https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg");
    memeEntity.setCaption("A unique meme indeed!");

    MemeCreationResponseDto responseDto = new MemeCreationResponseDto("001");

    doReturn(responseDto).when(memeService).createMeme(any(MemeEntity.class));

    // Act & Assert
    mvc.perform(
            post("/memes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memeEntity)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("001"));

    verify(memeService, times(1)).createMeme(memeEntity);
  }

  @Test
  public void createMeme_ReturnConflictStatus() throws Exception {
    // Arrange
    MemeEntity memeDuplicate = new MemeEntity();
    memeDuplicate.setName("Anant Saini");
    memeDuplicate.setUrl("https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg");
    memeDuplicate.setCaption("A unique meme indeed!");

    when(memeService.createMeme(any(MemeEntity.class)))
        .thenThrow(new MemeAlreadyExistsException("Meme already exists!"));

    // Act&Assert
    mvc.perform(
            post("/memes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memeDuplicate)))
        .andExpect(status().isConflict());

    verify(memeService, times(1)).createMeme(memeDuplicate);
  }
}
