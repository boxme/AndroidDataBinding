package com.desmond.androiddatabinding.binder;

import android.content.Context;
import android.databinding.DataBindingComponent;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by desmond on 20/10/15.
 */
public class MyDataBindingComponent implements DataBindingComponent {

    private static MyDataBindingComponent sInstance = new MyDataBindingComponent();

    public static MyDataBindingComponent create() {
        return sInstance;
    }

    private MyDataBindingComponent() {}

    private TestAdapter mAdapter = new TestAdapter() {
        @Override
        public void setImageUrl(ImageView imageView, String url, int placeHolder) {
            final Context context = imageView.getContext();

            final RequestCreator requestCreator = Picasso.with(context).load(url);
            if (placeHolder != 0) {
                requestCreator.placeholder(placeHolder);
            }
            requestCreator.into(imageView);
        }
    };

    @Override
    public TestAdapter getTestAdapter() {
        return mAdapter;
    }
}
