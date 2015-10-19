package com.desmond.androiddatabinding.binder;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by desmond on 17/10/15.
 */
public class DataBinder {

    private DataBinder() {

    }

    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void setImageUrl(final ImageView imageView, final String url, final int placeHolder) {
        final Context context = imageView.getContext();

        final RequestCreator requestCreator = Picasso.with(context).load(url);
        if (placeHolder != 0) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.into(imageView);
    }
}
