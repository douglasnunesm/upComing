package br.ucb.upcoming;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.ucb.upcoming.adapter.RecycleViewAdapter;
import br.ucb.upcoming.entity.Filme;

public class DetalheFilmeActivity extends Activity {
    TextView tvTitulo;
    TextView tvTituloOriginal;
    TextView tvDataLancamento;
    TextView tvGenero;
    TextView tvSinopse;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        imageView = (ImageView) findViewById(R.id.ivFilmeDetalhe);
        tvTitulo = (TextView)findViewById(R.id.tvTituloDetalhe);
        tvDataLancamento = (TextView)findViewById(R.id.tvDataLancamentoDetalhe);
        tvGenero = (TextView)findViewById(R.id.tvGenero);
        tvSinopse = (TextView)findViewById(R.id.tvSinopse);
        tvTituloOriginal = (TextView)findViewById(R.id.tvTituloOriginal);
        Intent intent = getIntent();
        Filme filme = (Filme) intent.getSerializableExtra("filme");

        String urlImage = "http://image.tmdb.org/t/p/original" + filme.getCaminhoPoster();

        Picasso.with(this).load(urlImage).into(imageView);
        tvTitulo.setText(filme.getTitulo());
        tvSinopse.setText(filme.getSinopse());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tvDataLancamento.setText(simpleDateFormat.format(filme.getDataLancamento()));
        tvTituloOriginal.setText(filme.getTituloOriginal());
        String url = "https://api.themoviedb.org/3/movie/"+filme.getIdFilme()
                +"?api_key=352875ee29f9f2057f001c72994e2940&language=pt-BR";
        DetalheService detalheService = new DetalheService();
        detalheService.execute(url);

    }

    public class DetalheService extends AsyncTask<String,Void,String> {


        ProgressDialog progressDialog;
        //List<Filme> filmes = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DetalheFilmeActivity.this,"Aguarde","Buscando dados");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return Network.get(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("genres");
                for(int i = 0; i < jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String genero = object.getString("name");
                    stringBuilder.append(genero);
                    if((i + 1) != jsonArray.length()){
                        stringBuilder.append(", ");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvGenero.setText(stringBuilder.toString());
            progressDialog.dismiss();
        }



        private List<Filme> recuperarFilmes(String s){
            List<Filme> filmeList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0; i < jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Filme filme = new Filme();
                    filme.setIdFilme(object.getString("id"));
                    filme.setTitulo(object.getString("title"));
                    filme.setPopularidade(object.getString("popularity"));
                    filme.setCaminhoPoster(object.getString("poster_path"));
                    filme.setLinguaOriginal(object.getString("original_language"));
                    filme.setTituloOriginal(object.getString("original_title"));
                    filme.setSinopse(object.getString("overview"));
                    String dataString = object.getString("release_date");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    filme.setDataLancamento((Date)formatter.parse(dataString));
                    filmeList.add(filme);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Collections.sort(filmeList);

            return filmeList;
        }

    }
}
