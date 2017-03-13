package com.bitbucket.heybeach.ui;

import android.content.Context;

public class ScreenNavigator {

  private final Context context;

  public ScreenNavigator(Context context) {
    this.context = context;
  }

  void navigateToAccountScreen() {
    LoginActivity.startWithAccountScreen(context);
  }

  void navigateToLoginScreen() {
    LoginActivity.startWithLoginScreen(context);
  }

  void navigateToRegistrationScreen() {
    LoginActivity.startWithRegistrationScreen(context);
  }

}
