package com.bitbucket.heybeach.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitbucket.heybeach.BuildConfig;
import com.bitbucket.heybeach.R;
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
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beach_list, parent, false));
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

    private ImageView imageView;
    private TextView nameView;

    ViewHolder(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.image);
      nameView = (TextView) itemView.findViewById(R.id.name);
    }

    void bind(Image image) {
      imageView.setMinimumWidth(image.getWidth());
      imageView.setMinimumHeight(image.getHeight());
      nameView.setText(image.getName());
      ImageLoader.getInstance().load(BuildConfig.API_BASE_URL + "/" + image.getRelativeUrl(), imageView);
    }

  }

}
