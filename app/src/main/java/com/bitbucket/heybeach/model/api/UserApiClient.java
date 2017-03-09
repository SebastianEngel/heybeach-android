package com.bitbucket.heybeach.model.api;

public class UserApiClient {

  private static final String REGISTRATION_ENDPOINT = "user/register";

  private final String baseUrl;

  public UserApiClient(String baseUrl) {
    this.baseUrl = baseUrl;
  }
  
}
