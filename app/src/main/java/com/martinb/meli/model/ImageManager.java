package com.martinb.meli.model;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageManager {

    public static ArrayList<Uri> getSelectedImages(@Nullable Intent data) {
        ArrayList<Uri> images = new ArrayList<>();

        ClipData clipData = data.getClipData();
        int n = clipData.getItemCount();
        for (int i = 0; i < n; i++) {
            Uri image = clipData.getItemAt(i).getUri();
            images.add(image);
//            String path = image.getPath();
//            Log.d("MartinB", path);
//            Log.d("MartinB ext", path.substring( path.lastIndexOf("/")+1) );
        }
        return images;
    }

    public static ArrayList<String> getEncodedImages(ArrayList<Uri> images, ContentResolver cr) {
        ArrayList<String> encodedImages = new ArrayList<>();

        for (Uri image : images) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            String encodedImage = Base64.encodeToString(os.toByteArray(), Base64.DEFAULT);
            encodedImages.add(encodedImage);
        }
        return encodedImages;
    }

    public static Bitmap getDecodeImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
