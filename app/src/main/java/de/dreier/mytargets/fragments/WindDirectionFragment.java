/*
 * MyTargets Archery
 *
 * Copyright (C) 2015 Florian Dreier
 * All rights reserved
 */

package de.dreier.mytargets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.recyclerviewchoicemode.SelectableViewHolder;

import de.dreier.mytargets.R;
import de.dreier.mytargets.adapters.NowListAdapter;
import de.dreier.mytargets.models.WindDirection;

public class WindDirectionFragment extends NowListFragment<WindDirection> {

    @Override
    protected void init(Bundle intent, Bundle savedInstanceState) {
        mEditable = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        setList(WindDirection.getList(getActivity()), new WindDirectionAdapter());
    }

    @Override
    public void onLongClick(SelectableViewHolder holder) {
        onClick(holder, (WindDirection) holder.getItem());
    }

    @Override
    protected void onEdit(WindDirection item) {
    }

    protected class WindDirectionAdapter extends NowListAdapter<WindDirection> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_text, parent, false);
            return new ViewHolder(itemView);
        }
    }

    public class ViewHolder extends SelectableViewHolder<WindDirection> {
        private final TextView mName;

        public ViewHolder(View itemView) {
            super(itemView, mMultiSelector, WindDirectionFragment.this);
            mName = (TextView) itemView.findViewById(R.id.name);
        }

        @Override
        public void bindCursor() {
            mName.setText(mItem.name);
        }
    }
}
