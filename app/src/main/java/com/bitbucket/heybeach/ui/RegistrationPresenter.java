package com.bitbucket.heybeach.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.bitbucket.heybeach.domain.RegistrationUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import com.bitbucket.heybeach.domain.User;

class RegistrationPresenter extends MvpPresenter<RegistrationPresenter.RegistrationView> {

  private static final String LOG_TAG = RegistrationPresenter.class.getName();

  private final RegistrationUseCase registrationUseCase;
  private final HandlerThread backgroundHandlerThread;
  private final Handler mainHandler;
  private Handler backgroundHandler;

  RegistrationPresenter(RegistrationUseCase registrationUseCase) {
    this.registrationUseCase = registrationUseCase;
    this.backgroundHandlerThread = new HandlerThread(ImageListPresenter.class + "-HandlerThread");
    this.mainHandler = new Handler(Looper.getMainLooper());
  }

  @Override
  public void onAttach(RegistrationView view) {
    super.onAttach(view);
    backgroundHandlerThread.start();
    backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
  }

  @Override
  public void onDetach() {
    backgroundHandlerThread.quitSafely();
    super.onDetach();
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
