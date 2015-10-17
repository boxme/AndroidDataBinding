package com.desmond.androiddatabinding.handler;

import com.desmond.androiddatabinding.model.Status;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by desmond on 17/10/15.
 */
public class ClickHandler {

    private final Status status;

    public ClickHandler(Status status) {
        this.status = status;
    }

    public void onClick(View view) {
        Snackbar.make(view, "Item clicked", Snackbar.LENGTH_LONG).show();
    }
}
