package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;

public class LoginFragment extends Fragment implements LoginPresenter.LoginView {

  private Toolbar toolbar;
  private EditText emailField;
  private EditText passwordField;
  private Button loginButton;
  private ProgressBar progressIndicator;
  private Button registrationButton;
  private LoginPresenter presenter;

  static LoginFragment newInstance() {
    return new LoginFragment();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Fragment lifecycle
  ///////////////////////////////////////////////////////////////////////////

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindViews();
    setupToolbar();
    setupLoginButton();
    setupRegistrationButton();
    createPresenter();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.onAttach(this);
  }

  @Override
  public void onStop() {
    presenter.onDetach();
    super.onStop();
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view implementation
  ///////////////////////////////////////////////////////////////////////////

  @Override
  public void showProgressIndicator() {
    progressIndicator.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideProgressIndicator() {
    progressIndicator.setVisibility(View.INVISIBLE);
  }

  @Override
  public void enableFormElements() {
    emailField.setEnabled(true);
    passwordField.setEnabled(true);
    loginButton.setEnabled(true);
    registrationButton.setEnabled(true);
  }

  @Override
  public void disableFormElements() {
    emailField.setEnabled(false);
    passwordField.setEnabled(false);
    loginButton.setEnabled(false);
    registrationButton.setEnabled(false);
  }

  @Override
  public void showFailureMessage() {
    Toast.makeText(getActivity(), R.string.login_failure_message, Toast.LENGTH_SHORT).show();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Private functionality
  ///////////////////////////////////////////////////////////////////////////

  private void bindViews() {
    toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
    emailField = (EditText) getView().findViewById(R.id.email_field);
    passwordField = (EditText) getView().findViewById(R.id.password_field);
    progressIndicator = (ProgressBar) getView().findViewById(R.id.progress_indicator);
    loginButton = (Button) getView().findViewById(R.id.login_button);
    registrationButton = (Button) getView().findViewById(R.id.registration_button);
  }

  private void setupToolbar() {
    toolbar.setTitle(R.string.login_toolbar_title);
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
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(getActivity());
    presenter = new LoginPresenter(authenticationUseCase, screenNavigator);
  }

}
