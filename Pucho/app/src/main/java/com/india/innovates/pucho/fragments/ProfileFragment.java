package com.india.innovates.pucho.fragments;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.india.innovates.pucho.QuestionFeed;
import com.india.innovates.pucho.R;
import com.india.innovates.pucho.utils.CircleCropTransformation;
import com.india.innovates.pucho.widgets.BezelImageView;

/**
 * Created by Raghunandan on 04-04-2016.
 */
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        BezelImageView profileIcon = (BezelImageView) view.findViewById(R.id.profile_user_img);
        TextView tvUserName = (TextView) view.findViewById(R.id.tv_username);
        String url = ((QuestionFeed) getActivity()).getPhotoUri();
        String uname = ((QuestionFeed) getActivity()).getUserName();
        if (!TextUtils.isEmpty(uname)) {
            tvUserName.setText(uname);
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this)
                    .load(Uri.parse(url))
                    .placeholder(R.drawable.person_image_empty)
                    .error(R.drawable.person_image_empty)
                    .transform(new CircleCropTransformation(getActivity()))
                    .into(profileIcon);
        }else {
            Drawable res = ContextCompat.getDrawable(getActivity(),R.drawable.person_image_empty);
            profileIcon.setImageDrawable(res);
        }
        return view;
    }
}
