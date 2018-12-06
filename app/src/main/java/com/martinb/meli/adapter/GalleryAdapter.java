package com.martinb.meli.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.martinb.meli.R;

import java.util.ArrayList;

public class GalleryAdapter extends PagerAdapter {

    Activity activity;
    ArrayList<Bitmap> images;

    public GalleryAdapter(Activity activity, ArrayList<Bitmap> images) {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.gallery_item, container, false);

        ImageView image = item.findViewById(R.id.image);
//        image.setImageResource(images[position]);
        image.setImageBitmap(images.get(position));

        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
