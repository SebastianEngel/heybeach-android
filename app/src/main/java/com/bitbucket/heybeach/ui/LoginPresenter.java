package com.bitbucket.heybeach.ui;

import com.bitbucket.heybeach.domain.LoginUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;

class LoginPresenter extends MvpPresenter<LoginPresenter.LoginView> {

  private final LoginUseCase loginUseCase;
  private final ScreenNavigator screenNavigator;

  LoginPresenter(LoginUseCase loginUseCase, ScreenNavigator screenNavigator) {
    this.loginUseCase = loginUseCase;
    this.screenNavigator = screenNavigator;
  }

  void onLoginAction(String email, String password) {
    backgroundHandler.post(() -> {
      try {
        loginUseCase.login(email, password);
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
