package com.martinb.meli.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.martinb.meli.R;

public class GalleryAdapter extends PagerAdapter {

    Activity activity;
    int[] images;

    public GalleryAdapter(Activity activity, int[] images) {
        this.activity = activity;
        this.images = images;
    }


    @Override
    public int getCount() {
        return images.length;
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
//        DisplayMetrics disp = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(disp);
//        int height = disp.heightPixels;
//        int width = disp.widthPixels;
//        image.setMinimumHeight(height);
//        image.setMinimumWidth(width);
        image.setImageResource(images[position]);

        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
