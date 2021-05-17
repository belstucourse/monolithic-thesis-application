package com.belstu.thesisproject.domain.user;

import com.belstu.thesisproject.domain.chat.Chat;
import com.belstu.thesisproject.domain.post.Post;
import com.belstu.thesisproject.domain.workday.Event;
import com.belstu.thesisproject.domain.workday.PsychoWorkday;
import com.belstu.thesisproject.updater.UserUpdater;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "psychologist")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Psychologist extends User<Psychologist> {

  @Column(name = "is_verified", nullable = false)
  private Boolean verified;

  @Column(name = "verified_date")
  private LocalDate verifiedDate;

  @Column(name = "mobile", length = 20)
  private String mobile;

  @Column(name = "avatar_url", length = 100)
  private String avatarUrl;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "psychologist_client",
      joinColumns = @JoinColumn(name = "psychologist_id"),
      inverseJoinColumns = @JoinColumn(name = "client_id"))
  private Set<Client> clients;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "psychologist_tag",
      joinColumns = @JoinColumn(name = "psychologist_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;

  @OneToMany(
      mappedBy = "psychologist",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Post> posts;

  @OneToMany(
      mappedBy = "psychologist",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<PsychoWorkday> workdays;

  @OneToMany(
      mappedBy = "psychologist",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Event> events;

  @OneToMany(
      mappedBy = "psychologist",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<Chat> chats;

  @Override
  public Psychologist update(final UserUpdater userUpdater, final Psychologist newUser) {
    return userUpdater.update(this, newUser);
  }

  @PrePersist
  public void onCreate() {
    verified = false;
    setDeactivated(false);
    setRegisterDate(LocalDate.now());
  }
}
