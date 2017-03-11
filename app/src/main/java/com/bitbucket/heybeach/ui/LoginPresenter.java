package com.bitbucket.heybeach.ui;

import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;

class LoginPresenter extends MvpPresenter<LoginPresenter.LoginView> {

  private final AuthenticationUseCase authenticationUseCase;
  private final ScreenNavigator screenNavigator;

  LoginPresenter(AuthenticationUseCase authenticationUseCase, ScreenNavigator screenNavigator) {
    this.authenticationUseCase = authenticationUseCase;
    this.screenNavigator = screenNavigator;
  }

  void onLoginAction(String email, String password) {
    backgroundHandler.post(() -> {
      try {
        authenticationUseCase.login(email, password);
        mainHandler.post(screenNavigator::navigateToAccountScreen);
      } catch (UseCaseException e) {
        mainHandler.post(() -> view.showFailureMessage());
      }
    });
  }

  void onNavigateToRegistrationScreen() {
    screenNavigator.navigateToRegistrationScreen();
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface LoginView extends MvpView {
    void showFailureMessage();
  }

}
