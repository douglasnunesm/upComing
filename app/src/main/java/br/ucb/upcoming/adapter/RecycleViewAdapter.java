package br.ucb.upcoming.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import br.ucb.upcoming.DetalheFilmeActivity;
import br.ucb.upcoming.R;
import br.ucb.upcoming.entity.Filme;

/**
 * Created by dougl on 17/10/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    static List<Filme> filmes;
    static Context context;

    public RecycleViewAdapter(Context context, List<Filme> filmes) {
        this.filmes = filmes;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitulo;
        public TextView textViewData;
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            textViewTitulo =(TextView) v.findViewById(R.id.tvTitulo);
            textViewData = (TextView) v.findViewById(R.id.tvDataLancamento);
            imageView = (ImageView) v.findViewById(R.id.ivFilme);
            //textViewTitulo.setText(v.);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION) {
                        Filme filme = filmes.get(position);
                        Intent intent = new Intent(context, DetalheFilmeActivity.class);
                        intent.putExtra("filme", filme);
                        context.startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(filmes.get(position).getIdFilme());
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder holder, int position) {
        holder.textViewTitulo.setText(filmes.get(position).getTitulo());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.textViewData.setText(simpleDateFormat.format(filmes.get(position).getDataLancamento()));
        String urlImage = "http://image.tmdb.org/t/p/original" + filmes.get(position).getCaminhoPoster();
        /*Drawable drawable = LoadImageFromWebOperations(urlImage);
        new DownloadImageTask(holder.imageView)
                .execute(urlImage);*/

        Picasso.with(holder.imageView.getContext()).load(urlImage).into(holder.imageView);
        //holder.imageView.setImageDrawable(drawable);
        holder.imageView.setAdjustViewBounds(true);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }


    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
