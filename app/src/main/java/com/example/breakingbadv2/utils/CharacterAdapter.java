package com.example.breakingbadv2.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.breakingbadv2.MoreInfoActivity;
import com.example.breakingbadv2.R;
import com.example.breakingbadv2.domain.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> implements Parcelable {
    private String TAG = this.getClass().getSimpleName();
    private List<Character> characterList;
    private Context context;
    private String toastMsg;
    private String charactersFound;

    public CharacterAdapter(List<Character> characterList, Context context) {
        Log.d(TAG, "Called constructor - creating character list");
        this.characterList = characterList;
        this.context = context;
        this.charactersFound = "characters found";
    }

    public CharacterAdapter(Parcel in) {
        toastMsg = in.readString();
        charactersFound = in.readString();
    }

    public static final Creator<CharacterAdapter> CREATOR = new Creator<CharacterAdapter>() {
        @Override
        public CharacterAdapter createFromParcel(Parcel in) {
            return new CharacterAdapter(in);
        }

        @Override
        public CharacterAdapter[] newArray(int size) {
            return new CharacterAdapter[size];
        }
    };

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "called onCreateViewHolder");
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.character_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Log.d(TAG, "Called onBindViewHolder");
        Character character = characterList.get(position);

        holder.mCharacterName.setText(character.getName());
        holder.mCharacterNickname.setText(character.getNickname());
        holder.mCharacterStatus.setText(character.getStatus());
        holder.mCharacterImage.setImageResource(R.mipmap.ic_launcher);
        Picasso
                .get()
                .load(character.getImageUrl())
                .resize(400, 400)
                .into(holder.mCharacterImage);
        //      opens detail page for character.
        holder.mLinearLayoutInfo.setOnClickListener((l) -> {
            Intent intent = new Intent(context, MoreInfoActivity.class);
            intent.putExtra("character", (Parcelable) character);
            context.startActivity(intent);
            Log.d(TAG, "onClick was called from linearlayout");
        });
//      opens a web page with the image in fullscreen.
        holder.mCharacterImage.setOnClickListener((l) -> {
            Log.d(TAG, "onClick was called from image");
            Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(character.getImageUrl()));
            holder.mCharacterImage.getContext().startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Called getItemCount");
        return characterList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d(TAG, "Called writeToParcel");
        dest.writeString(TAG);
        dest.writeString(toastMsg);
        dest.writeString(charactersFound);
    }
}
