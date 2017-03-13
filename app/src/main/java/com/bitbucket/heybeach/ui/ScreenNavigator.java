package com.bitbucket.heybeach.ui;

import android.content.Context;

public class ScreenNavigator {

  private final Context context;

  public ScreenNavigator(Context context) {
    this.context = context;
  }

  void navigateToAccountScreen() {
    AccountActivity.startWithAccountScreen(context);
  }

  void navigateToLoginScreen() {
    AccountActivity.startWithLoginScreen(context);
  }

  void navigateToRegistrationScreen() {
    AccountActivity.startWithRegistrationScreen(context);
  }

}
