package com.wasem.mysteriousquestions.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;
import com.wasem.mysteriousquestions.AppSharedPreferences;
import com.wasem.mysteriousquestions.DataBase.Listeners.SelectLevelListener;
import com.wasem.mysteriousquestions.DataBase.Models.Level;
import com.wasem.mysteriousquestions.R;
import com.wasem.mysteriousquestions.databinding.CustomLayoutLevelsBinding;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelHolder> {
    private List<Level> levels;
    private Context context;
    public SelectLevelListener selectLevelListener;

    public LevelAdapter(List<Level> levels, Context context, SelectLevelListener selectLevelListener) {
        this.levels = levels;
        this.context = context;
        this.selectLevelListener = selectLevelListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomLayoutLevelsBinding binding = CustomLayoutLevelsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new LevelHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull LevelHolder holder, int position) {
        Level level = levels.get(position);
        holder.binding.tvLevelNumber.setText(String.valueOf(level.level_no));
        holder.binding.tvPointsRequired.setText(String.valueOf(level.unlockPoints));
        if (level.level_no == 1) {
            holder.binding.imgRating.setImageResource(AppSharedPreferences.getInstance(getContext()).getLvlOneRating());
        }
        else {
            holder.binding.imgRating.setImageResource(AppSharedPreferences.getInstance(getContext()).getLvlTwoRating());
        }

        if (AppSharedPreferences.getInstance(getContext()).getScore() >= level.unlockPoints ) {
            holder.binding.imgLock.setImageResource(R.drawable.img_unlocked);
        }
        else {
            holder.binding.imgLock.setImageResource(R.drawable.img_lock);
        }

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppSharedPreferences.getInstance(getContext()).getScore() >= level.unlockPoints ) {
                    selectLevelListener.onSelectedLevelListener(level);
                }
                else {
                    FancyToast.makeText(getContext(),"Please collect the required points",Toast.LENGTH_SHORT,
                            FancyToast.WARNING,R.drawable.img_logo,false).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return levels.size();
    }
}

class LevelHolder extends RecyclerView.ViewHolder{
    CustomLayoutLevelsBinding binding;
    public LevelHolder(@NonNull View itemView) {
        super(itemView);
        binding = CustomLayoutLevelsBinding.bind(itemView);

    }
}
