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
import android.widget.Toast;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.AuthenticationUseCase;

public class RegistrationFragment extends Fragment implements RegistrationPresenter.RegistrationView {

  private Toolbar toolbar;
  private EditText emailField;
  private EditText passwordField;
  private Button registerButton;
  private RegistrationPresenter presenter;

  static RegistrationFragment newInstance() {
    return new RegistrationFragment();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Fragment lifecycle
  ///////////////////////////////////////////////////////////////////////////

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_registration, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    bindViews();
    setupToolbar();
    setupRegisterButton();
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
  public void showFailureMessage() {
    Toast.makeText(getActivity(), R.string.registration_failure_message, Toast.LENGTH_SHORT).show();
  }

  ///////////////////////////////////////////////////////////////////////////
  // Private functionality
  ///////////////////////////////////////////////////////////////////////////

  private void bindViews() {
    toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
    emailField = (EditText) getView().findViewById(R.id.email_field);
    passwordField = (EditText) getView().findViewById(R.id.password_field);
    registerButton = (Button) getView().findViewById(R.id.register_button);
  }

  private void setupToolbar() {
    toolbar.setTitle(R.string.registration_toolbar_title);
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
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(getActivity());
    presenter = new RegistrationPresenter(authenticationUseCase, screenNavigator);
  }

}
