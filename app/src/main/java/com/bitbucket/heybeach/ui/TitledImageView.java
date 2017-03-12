package com.bitbucket.heybeach.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import com.bitbucket.heybeach.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TitledImageView extends AppCompatImageView {

  private Paint textBackgroundPaint;
  private TextPaint textPaint;
  private int textSpacing;
  private String title;

  public TitledImageView(Context context) {
    super(context);

    Resources resources = context.getResources();

    textBackgroundPaint = new Paint();
    textBackgroundPaint.setColor(Color.argb(200, 250, 250, 250));

    textPaint = new TextPaint();
    textPaint.setTextSize(resources.getDimensionPixelSize(R.dimen.image_title_text_size));
    textPaint.setColor(Color.RED);

    textSpacing = resources.getDimensionPixelSize(R.dimen.image_title_text_spacing);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    drawTextWithBackground(canvas);
  }

  public void setImageBitmapWithTitle(Bitmap bitmap, String title) {
    this.title = title;
    setImageBitmap(bitmap);
  }

  private void drawTextWithBackground(Canvas canvas) {
    List<String> lines = calculateTitleLines(canvas.getWidth());
    Collections.reverse(lines);

    Rect textBounds = new Rect();
    int lineStartY = canvas.getHeight();

    for (String line : lines) {
      textPaint.getTextBounds(line, 0, line.length(), textBounds);

      int lineStartX = textSpacing;
      lineStartY = lineStartY - textSpacing;
      canvas.drawRect(0, lineStartY - textBounds.height(), canvas.getWidth(), lineStartY + textSpacing, textBackgroundPaint);
      canvas.drawText(line, lineStartX, lineStartY, textPaint);
      lineStartY = lineStartY - textBounds.height();
    }

    canvas.drawRect(0, lineStartY - textSpacing, canvas.getWidth(), lineStartY, textBackgroundPaint);
  }

  private List<String> calculateTitleLines(float maxWidth) {
    int index = 0;
    int fittingCharCount;

    List<String> lines = new ArrayList<>();
    while ((fittingCharCount = calculateFittingChars(index, maxWidth)) > 0) {
      lines.add(title.substring(index, index += fittingCharCount));
    }
    return lines;
  }

  private int calculateFittingChars(int indexInText, float maxWidth) {
    int count = title.length() - indexInText;
    return textPaint.breakText(title.toCharArray(), indexInText, count, maxWidth - textSpacing * 2, null);
  }

}
