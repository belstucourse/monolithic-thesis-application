package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.dto.post.PostDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post map(PostDto dto);

    @InheritInverseConfiguration
    PostDto map(Post entity);

    List<Post> mapToEntityList(List<PostDto> dtos);

    List<PostDto> mapToDtoList(List<Post> entities);
}
