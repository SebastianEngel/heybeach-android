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

public class RegistrationActivity extends AppCompatActivity implements RegistrationPresenter.RegistrationView {

  private EditText emailField;
  private EditText passwordField;
  private Button registerButton;
  private RegistrationPresenter presenter;

  public static void start(Context context) {
    context.startActivity(new Intent(context, RegistrationActivity.class));
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);

    bindViews();
    setupRegisterButton();
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
    Toast.makeText(this, R.string.registration_failure_message, Toast.LENGTH_SHORT).show();
  }

  private void bindViews() {
    emailField = (EditText) findViewById(R.id.email_field);
    passwordField = (EditText) findViewById(R.id.password_field);
    registerButton = (Button) findViewById(R.id.register_button);
  }

  private void setupRegisterButton() {
    registerButton.setOnClickListener(view -> {
      String email = emailField.getText().toString();
      String password = passwordField.getText().toString();
      presenter.onRegistrationAction(email, password);
    });
  }

  private void createPresenter() {
    AuthenticationUseCase authenticationUseCase = DependencyProvider.provideAuthenticationUseCase();
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(this);
    presenter = new RegistrationPresenter(authenticationUseCase, screenNavigator);
  }

}
