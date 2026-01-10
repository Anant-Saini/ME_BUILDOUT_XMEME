package com.crio.starter.service.implementations;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exception.MemeAlreadyExistsException;
import com.crio.starter.exception.NoMemeFoundException;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.repository.MemesRepository;
import com.crio.starter.service.interfaces.MemesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class MemesServiceImpl implements MemesService {

  @Autowired private MemesRepository memesRepository;

  @Override
  public List<MemeEntity> getRecentMemes() {

    List<MemeEntity> recentHundredMemes = memesRepository.findFirst100ByOrderByIdDesc();

    return recentHundredMemes;
  }

  @Override
  public MemeEntity getMemeById(String id) {

    return memesRepository
        .findById(id)
        .orElseThrow(() -> new NoMemeFoundException("No Meme with Id: " + id + " exists!"));
  }

  @Override
  public MemeCreationResponseDto createMeme(MemeEntity meme) {

    try {
      MemeEntity savedMeme = memesRepository.save(meme);
      MemeCreationResponseDto memeCreationResponseDto =
          new MemeCreationResponseDto(savedMeme.getId());
      return memeCreationResponseDto;

    } catch (DuplicateKeyException ex) {
      throw new MemeAlreadyExistsException("This meme already exists!", ex);
    } catch (DataIntegrityViolationException ex) {
      throw new MemeAlreadyExistsException("This meme already exists!", ex);
    }
  }
}
