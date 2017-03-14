package com.bitbucket.heybeach.domain;

import com.bitbucket.heybeach.model.UserJsonModelConverter;
import com.bitbucket.heybeach.model.api.users.LoginResult;
import com.bitbucket.heybeach.model.api.users.RegistrationResult;
import com.bitbucket.heybeach.model.api.users.UsersApiClient;
import java.util.concurrent.ExecutorService;

public class AuthenticationUseCase {

  private final UsersApiClient apiClient;
  private final AccountManager accountManager;
  private final ExecutorService executorService;

  public AuthenticationUseCase(UsersApiClient apiClient, AccountManager accountManager, ExecutorService executorService) {
    this.apiClient = apiClient;
    this.accountManager = accountManager;
    this.executorService = executorService;
  }

  public void register(String email, String password) throws UseCaseException {
    try {
      RegistrationResult registrationResult = executorService.submit(() -> apiClient.register(email, password)).get();
      String authToken = registrationResult.getAuthToken();
      User user = UserJsonModelConverter.convert(registrationResult.getUserJson());
      accountManager.storeAuthenticatedUserAndToken(user, authToken);
    } catch (Exception e) {
      throw new UseCaseException("Failed to register user.", e);
    }
  }

  public void login(String email, String password) throws UseCaseException {
    try {
      LoginResult loginResult = executorService.submit(() -> apiClient.login(email, password)).get();
      String authToken = loginResult.getAuthToken();
      User user = UserJsonModelConverter.convert(loginResult.getUserJson());
      accountManager.storeAuthenticatedUserAndToken(user, authToken);;
    } catch (Exception e) {
      throw new UseCaseException("Failed to login user.", e);
    }
  }

  public void logout() throws UseCaseException {
    try {
      executorService.submit(() -> apiClient.logout(accountManager.getAuthToken())).get();
      accountManager.clearAuthenticatedUser();
    } catch (Exception e) {
      throw new UseCaseException("Failed to logout user.", e);
    }
  }

}
