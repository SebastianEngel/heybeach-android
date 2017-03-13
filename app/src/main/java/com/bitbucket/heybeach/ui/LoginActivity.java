package com.bitbucket.heybeach.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class LoginActivity extends AppCompatActivity {

  private static final String LOG_TAG = LoginActivity.class.getName();
  private static final String EXTRA_START_WITH_SCREEN = "EXTRA_START_WITH_SCREEN";
  private static final String ACCOUNT_SCREEN = "ACCOUNT_SCREEN";
  private static final String LOGIN_SCREEN = "LOGIN_SCREEN";
  private static final String REGISTRATION_SCREEN = "REGISTRATION_SCREEN";

  public static void startWithAccountScreen(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(EXTRA_START_WITH_SCREEN, ACCOUNT_SCREEN);
    context.startActivity(intent);
  }

  public static void startWithLoginScreen(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(EXTRA_START_WITH_SCREEN, LOGIN_SCREEN);
    context.startActivity(intent);
  }

  public static void startWithRegistrationScreen(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    intent.putExtra(EXTRA_START_WITH_SCREEN, REGISTRATION_SCREEN);
    context.startActivity(intent);
  }

  ///////////////////////////////////////////////////////////////////////////
  // Activity lifecycle
  ///////////////////////////////////////////////////////////////////////////

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    displayScreen(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    displayScreen(intent);
  }

  ///////////////////////////////////////////////////////////////////////////
  // Fragment swapping
  ///////////////////////////////////////////////////////////////////////////

  private void displayScreen(Intent intent) {
    if (intent.hasExtra(EXTRA_START_WITH_SCREEN)) {
      String screenToStart = intent.getStringExtra(EXTRA_START_WITH_SCREEN);
      if (screenToStart != null) {
        switch (screenToStart) {
          case ACCOUNT_SCREEN:
            displayAccountScreen();
            break;
          case LOGIN_SCREEN:
            displayLoginView();
            break;
          case REGISTRATION_SCREEN:
            displayRegistrationView();
            break;
        }
      }
    } else {
      Log.w(LOG_TAG, "Activity started without expected intent extra: " + EXTRA_START_WITH_SCREEN);
    }
  }

  private void displayAccountScreen() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
      FragmentManager.BackStackEntry firstFragment = getSupportFragmentManager().getBackStackEntryAt(0);
      getSupportFragmentManager().popBackStack(firstFragment.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(android.R.id.content, AccountFragment.newInstance());
    fragmentTransaction.commit();
  }

  private void displayLoginView() {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(android.R.id.content, LoginFragment.newInstance());
    fragmentTransaction.commit();
  }

  private void displayRegistrationView() {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(android.R.id.content, RegistrationFragment.newInstance());
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }
}
