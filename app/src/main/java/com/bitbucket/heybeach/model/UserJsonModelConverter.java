package com.bitbucket.heybeach.model;

import com.bitbucket.heybeach.domain.User;
import com.bitbucket.heybeach.model.api.users.UserJson;

public class UserJsonModelConverter {

  private UserJsonModelConverter() {}

  public static User convert(UserJson userJson) {
    return new User(userJson.id, userJson.email);
  }

}
