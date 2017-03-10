package com.bitbucket.heybeach.ui;

import android.content.Context;

public class ScreenNavigator {

  private final Context context;

  public ScreenNavigator(Context context) {
    this.context = context;
  }

  void navigateToAccountScreen() {
    AccountActivity.start(context);
  }

  void navigateToLoginScreen() {
    LoginActivity.start(context);
  }

  void navigateToRegistrationScreen() {
    RegistrationActivity.start(context);
  }

}
