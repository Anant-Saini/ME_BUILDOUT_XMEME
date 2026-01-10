package com.crio.starter.service.interfaces;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeCreationResponseDto;
import java.util.List;

public interface MemesService {

  List<MemeEntity> getRecentMemes();

  MemeEntity getMemeById(String id);

  MemeCreationResponseDto createMeme(MemeEntity memeRequestDto);
}
