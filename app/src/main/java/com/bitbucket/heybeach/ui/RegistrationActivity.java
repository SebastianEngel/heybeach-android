package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;

public class RegistrationActivity extends AppCompatActivity implements RegistrationPresenter.RegistrationView {

  private EditText emailField;
  private EditText passwordField;
  private Button registerButton;
  private RegistrationPresenter registrationPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration);

    emailField = (EditText) findViewById(R.id.email_field);
    passwordField = (EditText) findViewById(R.id.password_field);
    registerButton = (Button) findViewById(R.id.register_button);

    registrationPresenter = new RegistrationPresenter(DependencyProvider.provideRegistrationUseCase());

    setupRegisterButton();
  }

  @Override
  protected void onStart() {
    super.onStart();
    registrationPresenter.onAttach(this);
  }

  @Override
  protected void onStop() {
    registrationPresenter.onDetach();
    super.onStop();
  }

  @Override
  public void showSuccessMessage() {
    Toast.makeText(this, "User registered successfully.", Toast.LENGTH_SHORT).show();
  }

  private void setupRegisterButton() {
    registerButton.setOnClickListener(view -> {
      String email = emailField.getText().toString();
      String password = passwordField.getText().toString();
      registrationPresenter.onRegistrationAction(email, password);
    });
  }

}