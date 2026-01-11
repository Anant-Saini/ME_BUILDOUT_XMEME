package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exception.NoMemeFoundException;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.repository.MemesRepository;
import com.crio.starter.service.implementations.MemesServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

@ExtendWith(MockitoExtension.class)
public class MemesServiceTest {

  @Mock private MemesRepository memesRepository;

  @InjectMocks private MemesServiceImpl memesServiceImpl;

  @Test
  public void createMeme_ReturnsResponseDto() {
    // Arrange
    MemeCreationResponseDto expectedResponseDto = new MemeCreationResponseDto("001");
    MemeEntity inputMeme = new MemeEntity();
    MemeEntity savedMeme = new MemeEntity();
    savedMeme.setId("001");
    when(memesRepository.save(any(MemeEntity.class))).thenReturn(savedMeme);
    // Act
    MemeCreationResponseDto actualResponseDto = memesServiceImpl.createMeme(inputMeme);
    // Assert

    assertEquals(expectedResponseDto, actualResponseDto);
    verify(memesRepository, times(1)).save(any(MemeEntity.class));
  }

  @Test
  public void createMeme_ThrowsMemeAlreadyExistsException() {
    // Arrange
    MemeEntity inputMeme = new MemeEntity();
    when(memesRepository.save(any(MemeEntity.class))).thenThrow(DuplicateKeyException.class);

    // Act&Assert
    assertThrows(MemeAlreadyExistsException.class, () -> memesServiceImpl.createMeme(inputMeme));
  }

  @Test
  public void getRecentMemes_ReturnsListOfMemeEntity() {
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

    when(memesRepository.findFirst100ByOrderByIdDesc()).thenReturn(expectedList);
    // Act
    List<MemeEntity> actualList = memesServiceImpl.getRecentMemes();

    // Assert
    assertEquals(expectedList, actualList);
    verify(memesRepository, times(1)).findFirst100ByOrderByIdDesc();
  }

  @Test
  public void getRecentMemes_ReturnsEmptyList() {
    // Arrange
    List<MemeEntity> expectedList = new ArrayList<>();
    when(memesRepository.findFirst100ByOrderByIdDesc()).thenReturn(expectedList);

    // Act
    List<MemeEntity> actualList = memesServiceImpl.getRecentMemes();

    // Assert
    assertEquals(expectedList, actualList);
    verify(memesRepository, times(1)).findFirst100ByOrderByIdDesc();
  }

  @Test
  public void getMemeById_ReturnsMemeEntity() {
    // Arrange
    MemeEntity expectedMeme = new MemeEntity();
    expectedMeme.setId("001");
    expectedMeme.setName("Amas");
    expectedMeme.setUrl("https://www.google.com");
    expectedMeme.setCaption("The curse of the Black Pearl!");

    when(memesRepository.findById(any(String.class))).thenReturn(Optional.of(expectedMeme));

    // Act
    MemeEntity actualMeme = memesServiceImpl.getMemeById("001");

    // Assert
    assertEquals(expectedMeme.getId(), actualMeme.getId());
    verify(memesRepository, times(1)).findById(any(String.class));
  }

  @Test
  public void getMemeById_ThrowsMemeNotFoundException() {
    // Arrange
    String inputId = "001";
    when(memesRepository.findById(any(String.class))).thenReturn(Optional.empty());

    // Act&Assert
    assertThrows(NoMemeFoundException.class, () -> memesServiceImpl.getMemeById(inputId));
    verify(memesRepository, times(1)).findById(inputId);
  }
}
