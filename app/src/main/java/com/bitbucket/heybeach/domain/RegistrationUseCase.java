package com.bitbucket.heybeach.domain;

import com.bitbucket.heybeach.model.UserJsonModelConverter;
import com.bitbucket.heybeach.model.api.ApiClientException;
import com.bitbucket.heybeach.model.api.users.UserJson;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;

public class RegistrationUseCase {

  private final UsersApiClient apiClient;

  public RegistrationUseCase(UsersApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public User register(String email, String password) throws UseCaseException {
    try {
      UserJson userJson = apiClient.register(email, password);
      return UserJsonModelConverter.convert(userJson);
    } catch (ApiClientException e) {
      throw new UseCaseException("Failed to register user.", e);
    }
  }

}
