package com.bitbucket.heybeach.ui;

import android.graphics.Bitmap;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bitbucket.heybeach.BuildConfig;
import com.bitbucket.heybeach.R;
import com.bitbucket.heybeach.domain.Image;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader;
import com.bitbucket.heybeach.ui.imageloading.ImageLoader.Callback;
import java.util.ArrayList;
import java.util.List;

class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

  private final List<Image> items = new ArrayList<>();

  void addItems(List<Image> images) {
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

    private ProgressBar progressIndicator;
    private ImageView imageView;
    private TextView nameView;

    ViewHolder(View itemView) {
      super(itemView);
      progressIndicator = (ProgressBar) itemView.findViewById(R.id.progress_indicator);
      imageView = (ImageView) itemView.findViewById(R.id.image);
      nameView = (TextView) itemView.findViewById(R.id.title_value);
    }

    void bind(Image image) {
      nameView.setText(image.getName());

      imageView.setImageBitmap(null);
      progressIndicator.setVisibility(View.VISIBLE);

      ImageLoader.getInstance().load(BuildConfig.API_BASE_URL + "/" + image.getRelativeUrl(), imageView, new Callback() {
        @Override
        public void onDownloadCompleted(Bitmap bitmap) {
          progressIndicator.setVisibility(View.GONE);
          imageView.setScaleType(ScaleType.CENTER_CROP);
          imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onDownloadFailed() {
          progressIndicator.setVisibility(View.GONE);
          imageView.setScaleType(ScaleType.CENTER);
          imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.getContext().getResources(), R.drawable.ic_mood_bad_24dp,
              imageView.getContext().getTheme()));
        }
      });
    }

  }

}
