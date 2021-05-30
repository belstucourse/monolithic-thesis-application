package com.belstu.thesisproject.mapper;

import com.belstu.thesisproject.domain.post.Comment;
import com.belstu.thesisproject.domain.user.User;
import com.belstu.thesisproject.dto.post.CommentDto;
import com.belstu.thesisproject.exception.NotFoundException;
import com.belstu.thesisproject.service.UserService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserService.class)
public abstract class CommentMapper {
  protected UserService userService;

  @Mapping(source = "commentDto.postId", target = "post.id")
  public abstract Comment map(CommentDto commentDto);

  @InheritInverseConfiguration
  public abstract CommentDto map(Comment comment);

  @BeforeMapping
  protected void convertUserToId(Comment comment, @MappingTarget CommentDto commentDto) {
    commentDto.setSenderId(comment.getSender().getId());
  }

  @BeforeMapping
  protected void convertUserToId(CommentDto commentDto, @MappingTarget Comment comment) {
    final String senderId = commentDto.getSenderId();
    final User user;
    try {
      user = userService.getUserById(senderId);
      comment.setSender(user);
    } catch (NotFoundException e) {
      e.printStackTrace();
    }
  }
}
