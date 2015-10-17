package com.desmond.androiddatabinding.binder;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by desmond on 17/10/15.
 */
public class DataBinder {

    private DataBinder() {

    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(final ImageView imageView, final String url) {
        final Context context = imageView.getContext();
        Picasso.with(context).load(url).into(imageView);
    }
}
