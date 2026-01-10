package com.crio.starter.controller;

import com.crio.starter.data.MemeEntity;
import com.crio.starter.exchange.MemeCreationResponseDto;
import com.crio.starter.service.interfaces.MemesService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class MemesController {

  @Autowired private MemesService memesService;

  @PostMapping("/memes/")
  public ResponseEntity<MemeCreationResponseDto> createMeme(@Valid @RequestBody MemeEntity meme) {

    MemeCreationResponseDto memeCreationResponseDto = memesService.createMeme(meme);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("{id}")
            .buildAndExpand(memeCreationResponseDto.getId())
            .toUri();

    return ResponseEntity.created(location).body(memeCreationResponseDto);
  }

  @GetMapping("/memes/")
  public ResponseEntity<List<MemeEntity>> getMemes() {

    List<MemeEntity> memeList = memesService.getRecentMemes();

    return ResponseEntity.ok().body(memeList);
  }

  @GetMapping("/memes/{id}")
  public ResponseEntity<MemeEntity> getMemeById(@PathVariable String id) {

    MemeEntity foundMeme = memesService.getMemeById(id);

    return ResponseEntity.ok().body(foundMeme);
  }
}
