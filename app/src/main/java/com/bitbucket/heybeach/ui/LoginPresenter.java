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
    view.disableFormElements();
    view.showProgressIndicator();

    try {
      authenticationUseCase.login(email, password);
      view.hideProgressIndicator();
      screenNavigator.navigateToAccountScreen();
      view.close();
    } catch (UseCaseException e) {
      view.hideProgressIndicator();
      view.enableFormElements();
      view.showFailureMessage();
    }
  }

  void onNavigateToRegistrationScreen() {
    screenNavigator.navigateToRegistrationScreen();
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface LoginView extends MvpView {
    void showProgressIndicator();
    void hideProgressIndicator();
    void enableFormElements();
    void disableFormElements();
    void showFailureMessage();
    void close();
  }

}
