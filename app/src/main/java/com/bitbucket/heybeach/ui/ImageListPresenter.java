package com.bitbucket.heybeach.ui;

import android.util.Log;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import java.util.List;

class ImageListPresenter extends MvpPresenter<ImageListPresenter.ImageListView> {

  private static final String LOG_TAG = ImageListPresenter.class.getName();

  private final ListImagesUseCase listImagesUseCase;
  private final ScreenNavigator screenNavigator;
  private int currentPage;

  ImageListPresenter(ListImagesUseCase listImagesUseCase, ScreenNavigator screenNavigator) {
    this.listImagesUseCase = listImagesUseCase;
    this.screenNavigator = screenNavigator;
  }

  @Override
  public void onAttach(ImageListView view) {
    super.onAttach(view);
    onNextPageRequested();
  }

  void onNavigateToAccount() {
    screenNavigator.navigateToAccountScreen();
  }


  void onNextPageRequested() {
    try {
      List<Image> images = listImagesUseCase.getImagesForPage(currentPage + 1);
      view.addImages(images);
      currentPage++;
    } catch (UseCaseException e) {
      Log.e(LOG_TAG, "Failed to load images.", e);
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface ImageListView extends MvpView {
    void addImages(List<Image> images);
  }

}
