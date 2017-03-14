package com.bitbucket.heybeach.model.api.users;

public class RegistrationResult {

  private final String authToken;
  private final UserJson userJson;

  public RegistrationResult(String authToken, UserJson userJson) {
    this.authToken = authToken;
    this.userJson = userJson;
  }

  public String getAuthToken() {
    return authToken;
  }

  public UserJson getUserJson() {
    return userJson;
  }

}
