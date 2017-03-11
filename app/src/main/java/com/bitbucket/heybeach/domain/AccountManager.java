package com.bitbucket.heybeach.domain;

public class AccountManager {

  private static AccountManager instance;

  private String authToken;
  private User authenticatedUser;

  public static AccountManager getInstance() {
    if (instance == null) {
      synchronized (AccountManager.class) {
        instance = new AccountManager();
      }
    }
    return instance;
  }

  public boolean isUserAuthenticated() {
    return authToken != null;
  }

  public void storeAuthenticatedUserAndToken(User user, String authToken) {
    this.authenticatedUser = user;
    this.authToken = authToken;
  }

  public void clearAuthenticatedUser() {
    this.authToken = null;
    this.authenticatedUser = null;
  }

  public String getAuthToken() {
    return authToken;
  }

}
