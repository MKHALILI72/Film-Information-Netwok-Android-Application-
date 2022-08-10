package com.example.mohammadkhalili.kelaket.mainfilmpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mohammadkhalili.kelaket.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.zip.Inflater;

import Model.AddFavoriteRequest;
import Model.CommentResp;
import Model.FilmReq;
import Model.FilmResp;
import Model.ListFilmRequest;
import Model.Raterequest;
import Server.UrlReq;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.mohammadkhalili.kelaket.mainfilmpage.MainListFilmActivity.rate;

/**
 * Created by mohammad khalili on 7/27/2018.
 */

public class MainFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<CommentResp> comment;
    private FilmResp film;
    private Context context;
    final AddFavoriteRequest addfavorite=new AddFavoriteRequest();

    public List<CommentResp> getComment() {
        return comment;
    }

    public void setComment(List<CommentResp> comment) {
        this.comment = comment;
    }

    public MainFilmAdapter(List<CommentResp> comment, FilmResp film, Context context){
        this.comment=comment;
        this.film=film;
        this.context=context;
    }
    public static class TopSectionViewHolder extends RecyclerView.ViewHolder{
        public ImageView poster;
        public TextView filmname;
        public ImageView backicon;
        public ImageView addfavorite;
        public TextView yearandcountry;
        public TextView genre;
        public TextView nvote;
        public TextView rate;
        public TopSectionViewHolder(View itemView) {
            super(itemView);
            Log.i("mohammad_khalili","topsectionviewholder");
            poster=(ImageView) itemView.findViewById(R.id.poster);
            filmname=(TextView) itemView.findViewById(R.id.filmname);
            backicon=(ImageView) itemView.findViewById(R.id.backicon);
            addfavorite=(ImageView)itemView.findViewById(R.id.addfavorite);
            yearandcountry=(TextView)itemView.findViewById(R.id.yearandcountry);
            genre=(TextView)itemView.findViewById(R.id.genre);
            nvote=(TextView)itemView.findViewById(R.id.sumuser);
            rate=(TextView)itemView.findViewById(R.id.score);

        }
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder{
        public TextView summarytxt;
        public SummaryViewHolder(View itemview){
            super(itemview);
            summarytxt=(TextView) itemview.findViewById(R.id.summarytext);
        }

    }
    public static class AgentViewHolder extends RecyclerView.ViewHolder{
        public TextView directorname;//havaset be inja bashe
        public TextView keyactors;
        public AgentViewHolder(View itemview){
            super(itemview);
            directorname=(TextView) itemview.findViewById(R.id.directorname);
            keyactors=(TextView) itemview.findViewById(R.id.keyactors);
        }

    }
    public static class CostViewHolder extends RecyclerView.ViewHolder{
        public TextView filmcost;
        public CostViewHolder(View itemview){
            super(itemview);
            filmcost=(TextView) itemview.findViewById(R.id.filmcost);
        }

    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public ImageView propic;
        public TextView username;
        public TextView commenttxt;
        public CommentViewHolder(View itemview){
            super(itemview);
            propic=(ImageView) itemview.findViewById(R.id.profileimg);
            username=(TextView) itemview.findViewById(R.id.profilename);
            commenttxt=(TextView) itemview.findViewById(R.id.commenttxt);

        }

    }
    public static class TrailerViewHolder extends RecyclerView.ViewHolder{
        public VideoView trailer;

        public TrailerViewHolder(View itemview){
            super(itemview);
            trailer=(VideoView) itemview.findViewById(R.id.vidio);


        }

    }
    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        public RatingBar ratefilm;
        public ScoreViewHolder(View itemview){
            super(itemview);
            ratefilm=(RatingBar)itemview.findViewById(R.id.ratefilm);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        switch (position) {
            case 0:return 0;
            case 1:return 1;
            case 2:return 2;
            case 3:return 3;
            case 4:return 4;
            case 5:return 5;
            default:return 6;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0:
                return new TopSectionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.topsection_filmview,parent,false));
            case 1:
                return new SummaryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_filmview,parent,false));
            case 2:return new TrailerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer,parent,false));
            case 3:return new AgentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_filmview,parent,false));
            case 4:return new CostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cost_filmview,parent,false));
            case 5:return new ScoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.score_filmview,parent,false));
            default:return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_filmview,parent,false));
        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        Log.i("positionlog", Integer.toString(position));
        if(position==0)
            topSectionViewHolderFun(holder,position);
        if(position==1)
            summaryViewHolderFun(holder,position);
        if(position==2)
            trailerViewHolderFun(holder,position);
        if(position==3)
            agentViewHolderFun(holder,position);
        if(position==4)
            costViewHodlerFun(holder,position);
        if(position==5)
            scoreViewHolderFun(holder,position);
        if(position>5)
            commentViewHolderFun(holder,position);


    }

    @Override
    public int getItemCount() {

        if(comment==null)
            return 6;
        return 6+comment.size();
    }
    public void commentViewHolderFun(RecyclerView.ViewHolder holder,int position){

        int pos=position-6;

        if(comment.get(pos).propic.equals("")){

        }else{
            Glide.with(context)
                    .load(Uri.parse(comment.get(pos).propic))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(((CommentViewHolder)holder).propic);
        }
        ((CommentViewHolder)holder).commenttxt.setText(comment.get(pos).comment);
        ((CommentViewHolder)holder).username.setText(comment.get(pos).username);
    }
    public void agentViewHolderFun(RecyclerView.ViewHolder holder,int position){
        ((AgentViewHolder)holder).keyactors.setText(film.f_keyactor);
        ((AgentViewHolder)holder).directorname.setText(film.f_director);

    }
    public void costViewHodlerFun(RecyclerView.ViewHolder holder,int position){
        ((CostViewHolder)holder).filmcost.setText(film.f_budget);
    }
    public void scoreViewHolderFun(final RecyclerView.ViewHolder holder, int position){
        MainListFilmActivity.rate=new Raterequest();
        rate.setF_id(film.getF_id());


        if(film.f_isfilmscored==false) {
            Log.i("mohammad_khaliliscore",film.f_isfilmscored.toString());
            ((ScoreViewHolder) holder).ratefilm.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    UrlReq myurl = new UrlReq();
                    rate.setRate(v);
                    String url = myurl.callServerFunction(6);
                    String json = new Gson().toJson(rate);
                    RequestBody requestbody = RequestBody.create(MediaType.parse("application/json"), json);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestbody)
                            .build();
                    OkHttpClient okhttp = new OkHttpClient();
                    okhttp.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("erroronresp", e.getMessage());
                            Toast.makeText((Activity) context, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();

                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText((Activity) context, "امتیاز شما ثیت شد", Toast.LENGTH_SHORT).show();
                                    ((ScoreViewHolder) holder).ratefilm.setEnabled(false);

                                }
                            });
                        }
                    });
                }
            });
        }
        else {
            ((ScoreViewHolder) holder).ratefilm.setRating(film.getF_rate());
            ((ScoreViewHolder) holder).ratefilm.setEnabled(false);
        }
    }
    public void summaryViewHolderFun(RecyclerView.ViewHolder holder,int position){
        ((SummaryViewHolder)holder).summarytxt.setText(film.getF_summary());
    }
    public void trailerViewHolderFun(final RecyclerView.ViewHolder holder, int position){
        try {
            String link=film.getF_trailer();
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(((TrailerViewHolder)holder).trailer);
            Uri video = Uri.parse(link);
            Log.i("mohammad_khalili",video.toString());
            ((TrailerViewHolder)holder).trailer.setMediaController(mediaController);
            ((TrailerViewHolder)holder).trailer.setVideoURI(video);
            ((TrailerViewHolder)holder).trailer.start();
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(context, "مشکل در ارتباط با سرور", Toast.LENGTH_SHORT).show();
        }
    }
    public void topSectionViewHolderFun(final RecyclerView.ViewHolder holder, int position){
         if(film.f_addedfavorite==true)
             ((TopSectionViewHolder)holder).addfavorite.setImageResource(R.drawable.addfavorite);
        ((TopSectionViewHolder)holder).filmname.setText(film.getF_name());
        ((TopSectionViewHolder)holder).yearandcountry.setText(film.f_createdate+" _محصول "+film.f_country);
        ((TopSectionViewHolder)holder).genre.setText(film.f_genre);
        ((TopSectionViewHolder)holder).nvote.setText(" از "+Integer.toString(film.f_nvote)+" نفر رای ");
        ((TopSectionViewHolder)holder).rate.setText(Float.toString(film.f_rate));
         Glide.with(context)
                .load(Uri.parse(film.getPoster()))
                .into(((TopSectionViewHolder)holder).poster);
        ((TopSectionViewHolder)holder).backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
        ((TopSectionViewHolder)holder).addfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                addfavorite.setF_id(film.getF_id());


                if(film.f_addedfavorite==false)
                    addfavorite.setAdd(1);
                else
                    addfavorite.setAdd(0);
                Log.i("mohammad_khalili",Integer.toString(addfavorite.getU_id()));
                String url=new UrlReq().callServerFunction(7);
                String json=new Gson().toJson(addfavorite);
                RequestBody requestbody=RequestBody.create(MediaType.parse("application/json"),json);
                Request request=new Request.Builder()
                        .url(url)
                        .post(requestbody)
                        .build();
                OkHttpClient okttp=new OkHttpClient();
                okttp.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("erroronresp", e.getMessage());
                                Toast.makeText((Activity) context, "متاسفانه در ازتباط با سرور مشکلی پیش آمده است", Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody responsebody=response.body();
                        final String body=responsebody.string();

                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 if(film.f_addedfavorite==false) {
                                     ((TopSectionViewHolder) holder).addfavorite.setImageResource(R.drawable.addfavorite);
                                      Toast.makeText(context,"فیلم به علاقمندی های شما اضافه شد",Toast.LENGTH_LONG ).show();
                                     film.f_addedfavorite=true;

                                 }
                                 else {
                                     ((TopSectionViewHolder) holder).addfavorite.setImageResource(R.drawable.ic_bookmark);
                                      Toast.makeText(context,"فیلم از علاقمندی های شما حذف شد",Toast.LENGTH_LONG ).show();
                                     film.f_addedfavorite=false;

                                 }


                            }
                        });

                    }
                });
            }

        });
    }

}
