package com.bitbucket.heybeach.ui;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import com.bitbucket.heybeach.domain.UseCaseException;
import java.util.List;

class ImageListPresenter extends MvpPresenter<ImageListPresenter.ImageListView> {

  private static final String LOG_TAG = ImageListPresenter.class.getName();

  private final ListImagesUseCase listImagesUseCase;
  private final HandlerThread backgroundHandlerThread;
  private final Handler mainHandler;
  private Handler backgroundHandler;

  ImageListPresenter(ListImagesUseCase listImagesUseCase) {
    this.listImagesUseCase = listImagesUseCase;
    this.backgroundHandlerThread = new HandlerThread(ImageListPresenter.class + "-HandlerThread");
    this.mainHandler = new Handler(Looper.getMainLooper());
  }

  @Override
  public void onAttach(ImageListView view) {
    super.onAttach(view);
    backgroundHandlerThread.start();
    backgroundHandler = new Handler(backgroundHandlerThread.getLooper());

    loadImages();
  }

  @Override
  public void onDetach() {
    backgroundHandlerThread.quitSafely();
    super.onDetach();
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
