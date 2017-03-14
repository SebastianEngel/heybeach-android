package com.bitbucket.heybeach.ui;

abstract class MvpPresenter<V extends MvpView> {

  V view;

  ///////////////////////////////////////////////////////////////////////////
  // Presenter lifecycle
  ///////////////////////////////////////////////////////////////////////////

  void onAttach(V view) {
    this.view = view;
  }

  void onDetach() {
    this.view = null;
  }

}
