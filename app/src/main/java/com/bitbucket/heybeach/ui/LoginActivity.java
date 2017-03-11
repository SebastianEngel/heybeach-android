package com.bitbucket.heybeach.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.LoginView {

  private EditText emailField;
  private EditText passwordField;
  private Button loginButton;
  private Button registrationButton;
  private LoginPresenter presenter;

  public static void start(Context context) {
    context.startActivity(new Intent(context, LoginActivity.class));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    bindViews();
    setupLoginButton();
    setupRegistrationButton();
    createPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onAttach(this);
  }

  @Override
  protected void onStop() {
    presenter.onDetach();
    super.onStop();
  }

  @Override
  public void showFailureMessage() {
    Toast.makeText(this, R.string.login_failure_message, Toast.LENGTH_SHORT).show();
  }

  private void bindViews() {
    emailField = (EditText) findViewById(R.id.email_field);
    passwordField = (EditText) findViewById(R.id.password_field);
    loginButton = (Button) findViewById(R.id.login_button);
    registrationButton = (Button) findViewById(R.id.registration_button);
  }

  private void setupLoginButton() {
    loginButton.setOnClickListener(view -> {
      String email = emailField.getText().toString();
      String password = passwordField.getText().toString();
      presenter.onLoginAction(email, password);
    });
  }

  private void setupRegistrationButton() {
    registrationButton.setOnClickListener(view -> {
      presenter.onNavigateToRegistrationScreen();
    });
  }

  private void createPresenter() {
    AuthenticationUseCase authenticationUseCase = DependencyProvider.provideAuthenticationUseCase();
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(this);
    presenter = new LoginPresenter(authenticationUseCase, screenNavigator);
  }

}
