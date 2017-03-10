package com.bitbucket.heybeach.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.bitbucket.heybeach.DependencyProvider;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.Image;
import java.util.List;

public class ImageListActivity extends AppCompatActivity implements ImageListPresenter.ImageListView {

  private ImageListPresenter imageListPresenter;
  private ImageListAdapter imageListAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_list);
    setupToolbar();
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
    imageListAdapter.updateItems(images);
  }

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.beaches_list_title);
    toolbar.inflateMenu(R.menu.menu_beaches_list);
    toolbar.setOnMenuItemClickListener(item -> {
      switch (item.getItemId()) {
        case R.id.account:
          imageListPresenter.onNavigateToAccount();
          return true;
      }
      return false;
    });
  }

  private void setupSignalsList() {
    RecyclerView imageList = (RecyclerView) findViewById(R.id.image_list);
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
