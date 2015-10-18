package com.desmond.androiddatabinding.adapter;

import com.desmond.androiddatabinding.R;
import com.desmond.androiddatabinding.databinding.StatusItemBinding;
import com.desmond.androiddatabinding.handler.ClickHandler;
import com.desmond.androiddatabinding.model.Status;
import com.desmond.androiddatabinding.model.StatusMarshaller;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 17/10/15.
 */
public class StatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Status> mStatusList;
    private final StatusMarshaller mStatusMarshaller;

    public StatusAdapter() {
        mStatusList = new ArrayList<>();
        mStatusMarshaller = new StatusMarshaller();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View statusContainer = inflater.inflate(R.layout.status_item, parent, false);
        return new StatusViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Status status = getItem(position);
        ((StatusViewHolder) holder).bind(status);
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    public Status getItem(final int position) {
        return position < getItemCount() ? mStatusList.get(position) : null;
    }

    public void setStatusList(final List<twitter4j.Status> statusList) {
        mStatusList.clear();
        mStatusList.addAll(mStatusMarshaller.marshall(statusList));
        notifyDataSetChanged();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {

        private StatusItemBinding mStatusItemBinding;

        public StatusViewHolder(View itemView) {
            super(itemView);
            mStatusItemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Status status) {
            mStatusItemBinding.setStatus(status);
            mStatusItemBinding.setHandler(new ClickHandler(status));

            // This is needed to prevent a second layout pass
            mStatusItemBinding.executePendingBindings();
        }
    }
}
