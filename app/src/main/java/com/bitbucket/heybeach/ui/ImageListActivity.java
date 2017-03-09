package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.Image;
import java.util.List;

public class ImageListActivity extends AppCompatActivity implements ImageListPresenter.ImageListView {

  private static final String LOG_TAG = ImageListActivity.class.getName();

  RecyclerView imageList;

  private ImageListPresenter imageListPresenter;
  private ImageListAdapter imageListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_list);

    imageList = (RecyclerView) findViewById(R.id.image_list);
    setupSignalsList();

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
    imageListAdapter.updateItems(images);
  }

  private void setupSignalsList() {
    imageList.setHasFixedSize(true);

    imageListAdapter = new ImageListAdapter();
    imageList.setAdapter(imageListAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    imageList.setLayoutManager(layoutManager);

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
    imageList.addItemDecoration(dividerItemDecoration);
  }

}
