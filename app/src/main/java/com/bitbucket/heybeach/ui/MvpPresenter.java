package com.bitbucket.heybeach.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

abstract class MvpPresenter<V extends MvpView> {

  V view;
  final Handler mainHandler;
  Handler backgroundHandler;
  private final HandlerThread backgroundHandlerThread;

  MvpPresenter() {
    this.backgroundHandlerThread = new HandlerThread(getClass() + "-HandlerThread");
    this.mainHandler = new Handler(Looper.getMainLooper());
  }

  ///////////////////////////////////////////////////////////////////////////
  // Presenter lifecycle
  ///////////////////////////////////////////////////////////////////////////

  public void onAttach(V view) {
    this.view = view;
    backgroundHandlerThread.start();
    backgroundHandler = new Handler(backgroundHandlerThread.getLooper());
  }

  public void onDetach() {
    this.view = null;
    backgroundHandlerThread.quitSafely();
  }

}
