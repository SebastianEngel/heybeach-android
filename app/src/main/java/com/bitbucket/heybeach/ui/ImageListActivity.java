package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.Image;
import java.util.List;

public class ImageListActivity extends AppCompatActivity implements ImageListPresenter.ImageListView {

  private static final String LOG_TAG = ImageListActivity.class.getName();

  private ImageListPresenter imageListPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    imageListPresenter = new ImageListPresenter(DependencyProvider.provideListImagesUseCase());
  }

  @Override
  protected void onStart() {
    super.onStart();
    imageListPresenter.onAttach(this);
  }

  @Override
  protected void onStop() {
    imageListPresenter.onDetach();
    super.onStop();
  }

  @Override
  public void updateImages(List<Image> images) {
    Log.i(LOG_TAG, "Updating list with images: " + images);
  }

}
