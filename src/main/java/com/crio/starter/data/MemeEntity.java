package com.crio.starter.data;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "memes")
@CompoundIndex(
    name = "meme_unique_idx",
    def = "{ 'name': 1, 'url': 1, 'caption': 1}",
    unique = true)
@NoArgsConstructor
@AllArgsConstructor
public class MemeEntity {

  @Id String id;
  @NotBlank String name;
  @NotBlank String url;
  @NotBlank String caption;
}
