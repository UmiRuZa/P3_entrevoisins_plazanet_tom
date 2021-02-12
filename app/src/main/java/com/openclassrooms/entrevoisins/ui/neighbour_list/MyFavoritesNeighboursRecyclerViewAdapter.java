package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourFromFavEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoritesNeighboursApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class MyFavoritesNeighboursRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoritesNeighboursRecyclerViewAdapter.ViewHolder> {

    private List<Neighbour> mNeighbourList;
    private FavoritesNeighboursApiService mFavoritesNeighboursApiService;

    public MyFavoritesNeighboursRecyclerViewAdapter(List<Neighbour> pNeighbourList) {
        mNeighbourList = pNeighbourList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup pViewGroup, int pI) {
        mFavoritesNeighboursApiService = DI.getFavoriteNeighbourApiService();
        View view = LayoutInflater.from(pViewGroup.getContext())
                .inflate(R.layout.fragment_neighbour, pViewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder pViewHolder, int pI) {
        Neighbour neighbour = mNeighbourList.get(pI);
        pViewHolder.mNeighbourName.setText(neighbour.getName());
        Glide.with(pViewHolder.mNeighbourAvatar.getContext()).load(neighbour.getAvatarUrl()).into(pViewHolder.mNeighbourAvatar);

        pViewHolder.mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                EventBus.getDefault().post(new DeleteNeighbourFromFavEvent(neighbour));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNeighbourList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
