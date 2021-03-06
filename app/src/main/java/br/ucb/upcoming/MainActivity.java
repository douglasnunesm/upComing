package br.ucb.upcoming;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.ucb.upcoming.adapter.RecycleViewAdapter;
import br.ucb.upcoming.entity.Filme;
import br.ucb.upcoming.services.FilmeService;

public class MainActivity extends Activity  {
    private static final int QTD_PAGINAS = 1;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Filme> filmes =  new ArrayList<>();
    String url = "https://api.themoviedb.org/3/movie/upcoming?" +
            "api_key=352875ee29f9f2057f001c72994e2940&language=pt-BR&page=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.listaFilmes);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        for(int i = 1; i <= QTD_PAGINAS; i++) {
            FilmeService filmeService = new FilmeService();
            //String[] urls = {url + i};
            filmeService.execute(url+i);
        }

    }
    public class FilmeService extends AsyncTask<String,Void,String> {


        ProgressDialog progressDialog;
        //List<Filme> filmes = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,"Aguarde","Buscando dados");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return Network.get(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //textView.setText(s);
            filmes.addAll(recuperarFilmes(s));

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
            adapter = new RecycleViewAdapter(MainActivity.this,filmeList);
            recyclerView.setAdapter(adapter);
            return filmeList;
        }

    }
}
