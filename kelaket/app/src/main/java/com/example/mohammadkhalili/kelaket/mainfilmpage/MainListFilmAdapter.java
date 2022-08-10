package com.example.mohammadkhalili.kelaket.mainfilmpage;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohammadkhalili.kelaket.R;

import java.util.List;

import Model.ListFilmResp;

/**
 * Created by mohammad khalili on 7/9/2018.
 */

public class MainListFilmAdapter extends RecyclerView.Adapter<MainListFilmAdapter.MyViewHolder> {

    private Context context;
    private List<ListFilmResp> film;
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView poster;
        public TextView filmname;
        public TextView directyear;
        public TextView rate;
        public TextView nvote;
        public TextView directorname;
        public CardView card;
        public MyViewHolder(View v){
            super(v);

            poster=(ImageView) v.findViewById(R.id.poster);
            filmname=(TextView) v.findViewById(R.id.filmname);
            directyear=(TextView)v.findViewById(R.id.directyear);
            rate=(TextView)v.findViewById(R.id.score);
            nvote=(TextView)v.findViewById(R.id.sumuser);
            directorname=(TextView) v.findViewById(R.id.directorname);
            card=(CardView)v.findViewById(R.id.item);

        }

    }
    public MainListFilmAdapter(List<ListFilmResp> film, Context context){
        this.film=film;
        this.context=context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  myview=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistviewfrag,parent,false);
        MyViewHolder vvv=new MyViewHolder(myview);
        Log.i("mohammad_khalili","sakhteshod");
        return vvv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.filmname.setText(film.get(position).getName());
        holder.directyear.setText(film.get(position).getCreatedate());
        holder.directorname.setText("کارگردان:"+film.get(position).getDirector());
        holder.nvote.setText(" از "+Integer.toString(+film.get(position).getNvote())+" نفر رای ");
        holder.rate.setText(film.get(position).getRate().toString());
        Glide.with(context)
                .load(Uri.parse(film.get(position).getPoster()))
                .into(holder.poster);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,MainFilmActivity.class);


                intent.putExtra("f_id",film.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        Log.i("mohammad_khalilil",Integer.toString(film.size()));
        return film.size();
    }
}

