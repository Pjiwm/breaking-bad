package com.example.breakingbadv2.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingbadv2.R;


public class CharacterViewHolder  extends RecyclerView.ViewHolder {
    private final String TAG = this.getClass().getSimpleName();
    public TextView mCharacterName;
    public TextView mCharacterNickname;
    public TextView mCharacterStatus;
    public ImageView mCharacterImage;
    public LinearLayout mLinearLayoutInfo;

    public CharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        mCharacterName = itemView.findViewById(R.id.character_list_item_character_name);
        mCharacterNickname = itemView.findViewById(R.id.character_list_item_character_nickname);
        mCharacterStatus = itemView.findViewById(R.id.character_list_item_character_status);
        mCharacterImage = itemView.findViewById(R.id.character_list_item_img);
        mLinearLayoutInfo = itemView.findViewById(R.id.linear_layout_info);
    }
}
