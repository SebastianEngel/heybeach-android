package com.bitbucket.heybeach.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader;
import java.util.ArrayList;
import java.util.List;

class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

  private final List<Image> items = new ArrayList<>();

  void updateItems(List<Image> images) {
    items.clear();
    items.addAll(images);
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(new TitledImageView(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.bind(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TitledImageView imageView;

    ViewHolder(View itemView) {
      super(itemView);
      imageView = (TitledImageView) itemView;
    }

    void bind(Image image) {
      imageView.setMinimumHeight(image.getHeight());
      ImageLoader.getInstance().load(image, imageView);
    }

  }

}
