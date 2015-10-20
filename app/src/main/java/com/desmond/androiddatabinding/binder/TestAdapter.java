package com.desmond.androiddatabinding.binder;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by desmond on 20/10/15.
 */
public interface TestAdapter {
    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    void setImageUrl(final ImageView imageView, final String url, final int placeHolder);
}
