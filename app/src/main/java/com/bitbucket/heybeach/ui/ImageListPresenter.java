package com.bitbucket.heybeach.ui;

import android.util.Log;
import com.bitbucket.heybeach.domain.AccountManager;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import java.util.List;

class ImageListPresenter extends MvpPresenter<ImageListPresenter.ImageListView> {

  private static final String LOG_TAG = ImageListPresenter.class.getName();

  private final ListImagesUseCase listImagesUseCase;
  private final AccountManager accountManager;
  private final ScreenNavigator screenNavigator;

  ImageListPresenter(ListImagesUseCase listImagesUseCase, AccountManager accountManager, ScreenNavigator screenNavigator) {
    this.listImagesUseCase = listImagesUseCase;
    this.accountManager = accountManager;
    this.screenNavigator = screenNavigator;
  }

  @Override
  public void onAttach(ImageListView view) {
    super.onAttach(view);
    loadImages();
  }

  void onNavigateToAccount() {
    if (accountManager.isUserAuthenticated()) {
      screenNavigator.navigateToAccountScreen();
    } else {
      screenNavigator.navigateToLoginScreen();
    }
  }

  private void loadImages() {
    try {
      List<Image> images = listImagesUseCase.getImages();
      view.updateImages(images);
    } catch (UseCaseException e) {
      Log.e(LOG_TAG, "Failed to load images.", e);
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface ImageListView extends MvpView {
    void updateImages(List<Image> images);
  }

}
