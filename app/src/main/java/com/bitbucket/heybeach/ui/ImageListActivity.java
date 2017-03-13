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
import com.bitbucket.heybeach.domain.ListImagesUseCase;
import java.util.List;

public class ImageListActivity extends AppCompatActivity implements ImageListPresenter.ImageListView {

  private ImageListPresenter presenter;
  private ImageListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_list);
    setupToolbar();
    setupSignalsList();
    createPresenter();
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.onAttach(this);
  }

  @Override
  protected void onStop() {
    presenter.onDetach();
    super.onStop();
  }

  @Override
  public void updateImages(List<Image> images) {
    listAdapter.updateItems(images);
  }

  private void setupToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.beaches_list_title);
    toolbar.inflateMenu(R.menu.menu_beaches_list);
    toolbar.setOnMenuItemClickListener(item -> {
      switch (item.getItemId()) {
        case R.id.account:
          presenter.onNavigateToAccount();
          return true;
      }
      return false;
    });
  }

  private void setupSignalsList() {
    RecyclerView imageList = (RecyclerView) findViewById(R.id.image_list);
    imageList.setHasFixedSize(true);

    listAdapter = new ImageListAdapter();
    imageList.setAdapter(listAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    imageList.setLayoutManager(layoutManager);

    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
    imageList.addItemDecoration(dividerItemDecoration);
  }

  private void createPresenter() {
    ListImagesUseCase listImagesUseCase = DependencyProvider.provideListImagesUseCase();
    ScreenNavigator screenNavigator = DependencyProvider.provideScreenNavigator(this);
    presenter = new ImageListPresenter(listImagesUseCase, screenNavigator);
  }

}
