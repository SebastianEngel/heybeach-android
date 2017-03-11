package com.bitbucket.heybeach.ui;

import com.bitbucket.heybeach.domain.AuthenticationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;

class RegistrationPresenter extends MvpPresenter<RegistrationPresenter.RegistrationView> {

  private final AuthenticationUseCase authenticationUseCase;
  private final ScreenNavigator screenNavigator;

  RegistrationPresenter(AuthenticationUseCase authenticationUseCase, ScreenNavigator screenNavigator) {
    this.authenticationUseCase = authenticationUseCase;
    this.screenNavigator = screenNavigator;
  }

  void onRegistrationAction(String email, String password) {
    backgroundHandler.post(() -> {
      try {
        authenticationUseCase.register(email, password);
        mainHandler.post(screenNavigator::navigateToAccountScreen);
      } catch (UseCaseException e) {
        mainHandler.post(() -> view.showFailureMessage());
      }
    });
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface RegistrationView extends MvpView {
    void showFailureMessage();
  }

}
