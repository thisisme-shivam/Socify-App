package com.example.socify.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socify.QueryFragments.QueryListFragment;
import com.example.socify.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class UserTagsLoaderAdapter extends RecyclerView.Adapter<UserTagsLoaderAdapter.TagViewHolder> {

    ArrayList<String> tags;
    Context context;
    int returntype;

    public UserTagsLoaderAdapter(ArrayList<String> tags, Context context) {
        this.tags = tags;
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {

        switch (tags.get(position)) {
            case "\uD83C\uDFB8 Music":
                returntype = 0;
                break;
            case "\uD83E\uDD16 Android Development":
                returntype = 1;
            break;

            case "\uD83C\uDF10 Web Development":
                returntype = 2;
            break;

            case "\uD83D\uDCF8 Photography":
                returntype = 3;
            break;

            case "⌨️ Competitive Coding":
                returntype = 4;
            break;

            case "⚽ Sports":
                returntype = 5;
            break;

            case "\uD83D\uDCAA Fitness":
                returntype = 6;
            break;

            case "\uD83D\uDD7A Dancing":
                returntype = 7;
            break;

            case "\uD83C\uDF49 Food":
                returntype = 8;
            break;

            case "\uD83C\uDFA8 Art & Design":
                returntype = 9;
            break;

            case "\uD83D\uDC68\u200D\uD83D\uDCBB Programming":
                returntype = 10;
            break;
            case "\uD83D\uDDE3️ Comm. Skills":
                returntype = 11;
            break;
            case "\uD83E\uDDE0 Aptitude":
                returntype = 12;
            break;
            case "\uD83D\uDC77\u200D♂️ Civil Eng.":
                returntype = 13;
            break;
            case "\uD83D\uDC68\u200D\uD83D\uDD27 Mechanical Eng.":
                returntype = 14;
            break;
            case "\uD83D\uDCA1 Electrical Eng.":
                returntype = 15;
            break;
            case "\uD83D\uDCF1 Electr. & Comm. Eng.":
                returntype = 16;
            break;
            case "\uD83D\uDC8A Pharmacy":
                returntype = 17;
            break;
            case "\uD83D\uDC68\u200D\uD83D\uDCBC MBA":
                returntype = 18;
            break;

            case "\uD83D\uDD2F Others":
                returntype = 19;
            break;

            default:
                returntype = 0;
        }
        Log.i("view number ", String.valueOf(returntype));
        return returntype;
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.musictaglayout, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.androidtaglayout, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.webtaglayout, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(context).inflate(R.layout.phototaglayout, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(context).inflate(R.layout.ccodingtaglayout, parent, false);
                break;
            case 5:
                view = LayoutInflater.from(context).inflate(R.layout.sportstaglayout, parent, false);
                break;
            case 6:
                view = LayoutInflater.from(context).inflate(R.layout.fitnesstaglayout, parent, false);
                break;
            case 7:
                view = LayoutInflater.from(context).inflate(R.layout.dancingtaglayout, parent, false);
                break;
            case 8:
                view = LayoutInflater.from(context).inflate(R.layout.foodtaglayout, parent, false);
                break;
            case 9:
                view = LayoutInflater.from(context).inflate(R.layout.artndesigntaglayout, parent, false);
                break;
            case 10:
                view = LayoutInflater.from(context).inflate(R.layout.programmingtaglayout, parent, false);
                break;
            case 11:
                view = LayoutInflater.from(context).inflate(R.layout.commskillslayout, parent, false);
                break;
            case 12:
                view = LayoutInflater.from(context).inflate(R.layout.aptitaglayout, parent, false);
                break;
            case 13:
                view = LayoutInflater.from(context).inflate(R.layout.civiltaglayout, parent, false);
                break;
            case 14:
                view = LayoutInflater.from(context).inflate(R.layout.mechtaglayout, parent, false);
                break;
            case 15:
                view = LayoutInflater.from(context).inflate(R.layout.electtaglayout, parent, false);
                break;
            case 16:
                view = LayoutInflater.from(context).inflate(R.layout.ecetaglayout, parent, false);
                break;
            case 17:
                view = LayoutInflater.from(context).inflate(R.layout.pharmcaytaglayout, parent, false);
                break;
            case 18:
                view = LayoutInflater.from(context).inflate(R.layout.mbataglayout, parent, false);
                break;
            case 19:
                view = LayoutInflater.from(context).inflate(R.layout.otherstaglayout, parent, false);
                break;

        }

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;
                Log.i("value",tags.get(position));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader, new QueryListFragment(tags.get(position))).commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return tags.size();
    }

    static class TagViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView tagTitle;
        FloatingActionButton actionButton;
        public TagViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTitle =itemView.findViewById(R.id.tagtitletv);
            actionButton = itemView.findViewById(R.id.actionbutton);
        }
    }

}
