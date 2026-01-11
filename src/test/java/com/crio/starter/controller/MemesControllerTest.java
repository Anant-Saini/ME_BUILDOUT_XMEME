package com.crio.starter.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exception.NoMemeFoundException;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.service.interfaces.MemesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

  @Test
  public void getMemes_ReturnOkStatusAndListOfMemes() throws Exception {

    // Arrange
    MemeEntity meme1 = new MemeEntity();
    meme1.setId("001");
    meme1.setName("Anant");
    meme1.setCaption("Dummy caption");
    meme1.setUrl("https://www.google.com");
    MemeEntity meme2 = new MemeEntity();
    meme2.setId("002");
    meme2.setName("Aadya");
    meme2.setCaption("Dummy caption");
    meme2.setUrl("https://www.amazon.com");
    List<MemeEntity> expectedList = List.of(meme1, meme2);

    doReturn(expectedList).when(memeService).getRecentMemes();

    // Act & Assert
    mvc.perform(get("/memes/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name").value("Anant"))
        .andExpect(jsonPath("$[1].name").value("Aadya"));

    verify(memeService, times(1)).getRecentMemes();
  }

  @Test
  public void getMemeById_ThrowsNoMemeFoundException() throws Exception {

    // Arrange
    String inputId = "xyz";
    String errorMessage = "No Meme with Id: " + inputId + " exists!";
    doThrow(new NoMemeFoundException(errorMessage)).when(memeService).getMemeById(anyString());

    // Act & Assert
    mvc.perform(get("/memes/{id}", inputId).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message").value(errorMessage))
        .andExpect(jsonPath("$.path").value("/memes/" + inputId));

    verify(memeService, times(1)).getMemeById(inputId);
  }

  public void getMemeById_ReturnOkStatusAndMeme() throws Exception {

    // Arrange
    String inputId = "001";
    MemeEntity expectedMeme = new MemeEntity();
    expectedMeme.setId(inputId);
    expectedMeme.setName("Anant");
    expectedMeme.setCaption("Dummy caption");
    expectedMeme.setUrl("https://www.google.com");

    doReturn(expectedMeme).when(memeService).getMemeById(anyString());

    // Act & Assert
    mvc.perform(get("/memes/{id}", inputId).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Anant"))
        .andExpect(jsonPath("$.caption").value("Dummy caption"))
        .andExpect(jsonPath("$.url").value("https://www.google.com"));

    verify(memeService, times(1)).getMemeById(inputId);
  }
}
