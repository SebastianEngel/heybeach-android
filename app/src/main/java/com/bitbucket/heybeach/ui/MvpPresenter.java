package com.bitbucket.heybeach.ui;

abstract class MvpPresenter<V extends MvpView> {

  V view;

  ///////////////////////////////////////////////////////////////////////////
  // Presenter lifecycle
  ///////////////////////////////////////////////////////////////////////////

  public void onAttach(V view) {
    this.view = view;
  }

  public void onDetach() {
    this.view = null;
  }

}
