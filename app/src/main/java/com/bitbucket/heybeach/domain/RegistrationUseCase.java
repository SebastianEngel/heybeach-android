package com.bitbucket.heybeach.domain;

import com.bitbucket.heybeach.model.UserJsonModelConverter;
import com.bitbucket.heybeach.model.api.ApiClientException;
import com.bitbucket.heybeach.model.api.users.RegistrationResult;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;

public class RegistrationUseCase {

  private final UsersApiClient apiClient;
  private final AccountManager accountManager;

  public RegistrationUseCase(UsersApiClient apiClient, AccountManager accountManager) {
    this.apiClient = apiClient;
    this.accountManager = accountManager;
  }

  public void register(String email, String password) throws UseCaseException {
    try {
      RegistrationResult registrationResult = apiClient.register(email, password);
      String authToken = registrationResult.getAuthToken();
      User user = UserJsonModelConverter.convert(registrationResult.getUserJson());
      accountManager.storeAuthenticatedUserAndToken(user, authToken);
    } catch (ApiClientException e) {
      throw new UseCaseException("Failed to register user.", e);
    }
  }

}
