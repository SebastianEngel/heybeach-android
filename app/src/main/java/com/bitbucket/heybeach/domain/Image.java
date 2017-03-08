package com.bitbucket.heybeach.domain;

import java.util.Objects;

public class Image {

  private String id;
  private String name;
  private String url;
  private int width;
  private int height;

  public Image(String id, String name, String url, int width, int height) {
    this.id = id;
    this.name = name;
    this.url = url;
    this.width = width;
    this.height = height;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Image image = (Image) o;
    return width == image.width
        && height == image.height
        && Objects.equals(id, image.id)
        && Objects.equals(name, image.name)
        && Objects.equals(url, image.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, url, width, height);
  }

  @Override
  public String toString() {
    return "Image{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", url='"
        + url
        + '\''
        + ", width="
        + width
        + ", height="
        + height
        + '}';
  }

}
