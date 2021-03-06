package com.bitbucket.heybeach.domain;

import java.util.Objects;

public class User {

  private String id;
  private String email;

  public User(String id, String email) {
    this.id = id;
    this.email = email;
  }

  public String getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) && Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email);
  }

  @Override
  public String toString() {
    return "User{" + "id='" + id + '\'' + ", email='" + email + '\'' + '}';
  }

}
