package com.crio.starter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.repository.MemesRepository;
import com.crio.starter.service.implementations.MemesServiceImpl;
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
}
