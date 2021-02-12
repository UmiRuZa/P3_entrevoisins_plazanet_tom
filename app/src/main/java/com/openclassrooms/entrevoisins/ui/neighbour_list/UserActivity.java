package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoritesNeighboursApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    // Api
    private Neighbour mNeighbour;
    private List<Neighbour> mNeighbourList;
    private NeighbourApiService mApiService;
    private FavoritesNeighboursApiService mFavoritesNeighboursApiService;

    // UI
    @BindView(R.id.user_activity_rootview)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.imageview_neighbour_photo)
    ImageView mNeighbourPhoto;
    @BindView(R.id.textview_neighbour_name)
    TextView mNeighbourName;
    @BindView(R.id.textview_neighbour_name_description)
    TextView mNeighbourNameDescription;
    @BindView(R.id.textview_neighbour_adrress)
    TextView mNeighbourAddress;
    @BindView(R.id.textview_neighbour_phone)
    TextView mNeighbourPhone;
    @BindView(R.id.textview_neighbour_facebook)
    TextView mNeighbourFacebook;
    @BindView(R.id.text_view_neighbour_about_description)
    TextView mNeighbourDescription;
    @BindView(R.id.add_neighbour_to_fav)
    FloatingActionButton mButtonAddToFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        mApiService = DI.getNeighbourApiService();
        mFavoritesNeighboursApiService = DI.getFavoriteNeighbourApiService();

        this.initList();
        this.getNeighbourByID();
        this.updateUI();
        this.setOnClickOnFavButton();
        this.configureFavButtonState();
    }

    /**
     * Navigate to {@link UserActivity}
     * @param pActivity the activity who initiate the navigation
     */
    public static void navigate(Activity pActivity) {
        Intent intent = new Intent(pActivity, UserActivity.class);
        ActivityCompat.startActivity(pActivity, intent, null);
    }

    /**
     * Get the list of neighbours
     */
    private void initList() {
        mNeighbourList = mApiService.getNeighbours();
    }

    /**
     * Fetch neighbour form previous activity to neighbourlist
     */
    private void getNeighbourByID() {
        long id = getIntent().getLongExtra(NeighbourFragment.KEY_USER_ID, 0);
        for (Neighbour neighbour : mNeighbourList) {
            if (neighbour.getId() == id) {
                mNeighbour = neighbour;
            }
        }
    }

    /**
     * Update all the UI components
     */
    private void updateUI() {
        Glide.with(this).load(mNeighbour.getAvatarUrl()).into(mNeighbourPhoto);
        mNeighbourName.setText(mNeighbour.getName());
        mNeighbourNameDescription.setText(mNeighbour.getName());
        mNeighbourAddress.setText(mNeighbour.getAddress());
        mNeighbourPhone.setText(mNeighbour.getPhoneNumber());
        mNeighbourFacebook.setText(mNeighbour.getAvatarUrl());
        mNeighbourDescription.setText(mNeighbour.getAboutMe());
    }

    private void setOnClickOnFavButton() {
        mButtonAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mFavoritesNeighboursApiService.createFavoriteNeighbour(mNeighbour);
                Snackbar.make(mConstraintLayout, R.string.snackbar_addToFav, Snackbar.LENGTH_SHORT)
                        .show();
                configureFavButtonState();
            }
        });
    }

    private void configureFavButtonState() {
        List<Neighbour> favoritesList = mFavoritesNeighboursApiService.getFavoritesNeighbours();
        if (favoritesList.contains(mNeighbour)) {
            mButtonAddToFav.setEnabled(false);
            mButtonAddToFav.getDrawable().mutate().setTint(getResources().getColor(R.color.colorFavButton));
            mButtonAddToFav.setElevation(16);
        } else {
            mButtonAddToFav.setEnabled(true);
            mButtonAddToFav.getDrawable().mutate().setTint(getResources().getColor(R.color.colorFavButtonFalse));
        }
    }
}