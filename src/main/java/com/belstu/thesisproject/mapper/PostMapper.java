package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.dto.post.PostDto;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(source = "dto.psychologistId", target = "psychologist.id")
  Post map(PostDto dto);

  @InheritInverseConfiguration
  PostDto map(Post entity);

  List<Post> mapToEntityList(List<PostDto> dtos);

  List<PostDto> mapToDtoList(List<Post> entities);
}
