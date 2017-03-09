package com.bitbucket.heybeach.ui;

import android.util.Log;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import java.util.List;

class ImageListPresenter extends MvpPresenter<ImageListPresenter.ImageListView> {

  private static final String LOG_TAG = ImageListPresenter.class.getName();

  private final ListImagesUseCase listImagesUseCase;

  ImageListPresenter(ListImagesUseCase listImagesUseCase) {
    super();
    this.listImagesUseCase = listImagesUseCase;
  }

  @Override
  public void onAttach(ImageListView view) {
    super.onAttach(view);
    loadImages();
  }

  private void loadImages() {
    backgroundHandler.post(() -> {
      try {
        List<Image> images = listImagesUseCase.getImages();
        mainHandler.post(() -> view.updateImages(images));
      } catch (UseCaseException e) {
        Log.e(LOG_TAG, "Failed to load images.", e);
      }
    });
  }

  ///////////////////////////////////////////////////////////////////////////
  // MVP view interface
  ///////////////////////////////////////////////////////////////////////////

  interface ImageListView extends MvpView {
    void updateImages(List<Image> images);
  }

}
