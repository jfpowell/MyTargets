/*
 * MyTargets Archery
 *
 * Copyright (C) 2015 Florian Dreier
 * All rights reserved
 */

package de.dreier.mytargets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.recyclerviewchoicemode.SelectableViewHolder;

import de.dreier.mytargets.R;
import de.dreier.mytargets.activities.SimpleFragmentActivity;
import de.dreier.mytargets.adapters.NowListAdapter;
import de.dreier.mytargets.models.SightSetting;
import de.dreier.mytargets.shared.models.Bow;
import de.dreier.mytargets.utils.RoundedAvatarDrawable;

public class BowFragment extends NowListFragment<Bow> implements View.OnClickListener {

    @Override
    protected void init(Bundle intent, Bundle savedInstanceState) {
        itemTypeRes = R.plurals.bow_selected;
        itemTypeDelRes = R.plurals.bow_deleted;
        newStringRes = R.string.new_bow;
        mEditable = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setList(db.getBows(), new BowAdapter());
    }

    @Override
    protected void onEdit(Bow item) {
        Intent i = new Intent(getActivity(), SimpleFragmentActivity.EditBowActivity.class);
        i.putExtra(EditBowFragment.BOW_ID, item.getId());
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    @Override
    public void onClick(View v) {
        startActivity(SimpleFragmentActivity.EditBowActivity.class);
    }

    protected class BowAdapter extends NowListAdapter<Bow> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_image_details, parent, false);
            return new ViewHolder(itemView);
        }
    }

    public class ViewHolder extends SelectableViewHolder<Bow> {
        private final TextView mName;
        private final TextView mDetails;
        private final ImageView mImg;

        public ViewHolder(View itemView) {
            super(itemView, mMultiSelector, BowFragment.this);
            mName = (TextView) itemView.findViewById(R.id.name);
            mDetails = (TextView) itemView.findViewById(R.id.details);
            mImg = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void bindCursor() {
            mName.setText(mItem.name);
            mImg.setImageDrawable(new RoundedAvatarDrawable(mItem.getThumbnail()));

            String html = getString(R.string.bow_type) + ": <b>" +
                    getResources().getStringArray(R.array.bow_types)[mItem.type] + "</b>";
            if (!mItem.brand.trim().isEmpty()) {
                html += "<br>" + getString(R.string.brand) + ": <b>" + mItem.brand + "</b>";
            }
            if (!mItem.size.trim().isEmpty()) {
                html += "<br>" + getString(R.string.size) + ": <b>" + mItem.size + "</b>";
            }
            for (SightSetting s : mItem.sightSettings) {
                html += "<br>" + s.distance.toString(getActivity()) + ": <b>" + s.value + "</b>";
            }
            mDetails.setText(Html.fromHtml(html));
        }
    }
}
