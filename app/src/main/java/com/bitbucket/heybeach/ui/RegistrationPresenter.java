package com.bitbucket.heybeach.ui;

import com.bitbucket.heybeach.domain.RegistrationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;

class RegistrationPresenter extends MvpPresenter<RegistrationPresenter.RegistrationView> {

  private final RegistrationUseCase registrationUseCase;
  private final ScreenNavigator screenNavigator;

  RegistrationPresenter(RegistrationUseCase registrationUseCase, ScreenNavigator screenNavigator) {
    this.registrationUseCase = registrationUseCase;
    this.screenNavigator = screenNavigator;
  }

  void onRegistrationAction(String email, String password) {
    backgroundHandler.post(() -> {
      try {
        registrationUseCase.register(email, password);
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
