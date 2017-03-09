package com.bitbucket.heybeach.ui;

import android.util.Log;
import com.bitbucket.heybeach.domain.RegistrationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import com.bitbucket.heybeach.domain.User;

class RegistrationPresenter extends MvpPresenter<RegistrationPresenter.RegistrationView> {

  private static final String LOG_TAG = RegistrationPresenter.class.getName();

  private final RegistrationUseCase registrationUseCase;

  RegistrationPresenter(RegistrationUseCase registrationUseCase) {
    this.registrationUseCase = registrationUseCase;
  }

  void onRegistrationAction(String email, String password) {
    backgroundHandler.post(() -> {
      try {
        User user = registrationUseCase.register(email, password);
        Log.d(LOG_TAG, "Successfully registered user: " + user);

        // TODO Perform login now?

        mainHandler.post(() -> view.showSuccessMessage());
      } catch (UseCaseException e) {
        Log.e(LOG_TAG, "Failed to register user.", e);
      }
    });
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface RegistrationView extends MvpView {
    void showSuccessMessage();
  }

}
