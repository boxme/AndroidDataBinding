package com.desmond.androiddatabinding.adapter;

import com.desmond.androiddatabinding.R;
import com.desmond.androiddatabinding.binder.MyDataBindingComponent;
import com.desmond.androiddatabinding.databinding.StatusItemBinding;
import com.desmond.androiddatabinding.handler.ClickHandler;
import com.desmond.androiddatabinding.model.Status;
import com.desmond.androiddatabinding.model.StatusMarshaller;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

    public static final String TAG = StatusAdapter.class.getSimpleName();

    private static final Object DATA_INVALIDATION = new Object();

    private final List<Status> mStatusList;
    private final StatusMarshaller mStatusMarshaller;

    private RecyclerView mRecyclerView;

    public StatusAdapter() {
        mStatusList = new ArrayList<>();
        mStatusMarshaller = new StatusMarshaller();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);

        final StatusViewHolder viewHolder = StatusViewHolder.create(inflater, parent,
                R.layout.status_item, viewType);

        addPreBindCallback(viewHolder);

        return viewHolder;
    }

    /**
     * Full Bind will trigger this
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Status status = getItem(position);
        ((StatusViewHolder) holder).bind(status);
    }

    /**
     * Partial Bind will trigger this
     * @param holder
     * @param position
     * @param payloads Check out MainActivity.onCreate() comment. Lower version will require
     *                 RecyclerView.getItemAnimator().setSupportsChangeAnimations(false) to
     *                 receive the payload;
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (isForDataBinding(payloads) && holder instanceof StatusViewHolder) {
            ((StatusViewHolder) holder).getBinding().executePendingBindings();
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    /**
     * This will allow the recyclerView to stop the observable to trigger a change by itself,
     * without going through the recyclerView.
     * @param viewHolder
     */
    private void addPreBindCallback(@NonNull final StatusViewHolder viewHolder) {
        viewHolder.getBinding().addOnRebindCallback(new OnRebindCallback() {

            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                // Return false if the binding is not because of a RecyclerView computation.
                // False will trigger onCanceled()
                return mRecyclerView != null && mRecyclerView.isComputingLayout();
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                if (mRecyclerView == null || mRecyclerView.isComputingLayout()) {
                    return;
                }

                final int position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // This data invalidation is sent to #onBindViewHolder(ViewHolder, int, List<>)
                    notifyItemChanged(position, DATA_INVALIDATION);
                }
            }
        });
    }

    public boolean isForDataBinding(List<Object> payloads) {
        if (payloads == null || payloads.isEmpty()) {
            return false;
        }

        for (Object payload : payloads) {
            if (payload != DATA_INVALIDATION) {
                return false;
            }
        }

        return true;
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

        public static StatusViewHolder create(final LayoutInflater inflater,
                                              final ViewGroup parent, @LayoutRes final int layoutId,
                                              final int viewType) {
            return new StatusViewHolder(inflater.inflate(layoutId, parent, false));
        }

        private StatusViewHolder(View itemView) {
            super(itemView);

            // Below is different Component for different layout
//            DataBindingUtil.bind(itemView, MyDataBindingComponent.create());

            DataBindingUtil.setDefaultComponent(MyDataBindingComponent.create());
            mStatusItemBinding = DataBindingUtil.bind(itemView);
        }

        public void bind(Status status) {
            mStatusItemBinding.setStatus(status);
            mStatusItemBinding.setHandler(new ClickHandler(status));

            // This is needed to prevent a second layout pass
            mStatusItemBinding.executePendingBindings();
        }

        public StatusItemBinding getBinding() {
            return this.mStatusItemBinding;
        }
    }
}
