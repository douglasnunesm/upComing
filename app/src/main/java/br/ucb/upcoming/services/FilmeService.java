package br.ucb.upcoming.services;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ucb.upcoming.Network;
import br.ucb.upcoming.entity.Filme;

/**
 * Created by dougl on 07/11/2017.
 */

public class FilmeService extends AsyncTask<String,Void,String> {
    ProgressDialog progressDialog;
    List<Filme> filmes = new ArrayList<>();
    @Override
    protected void onPreExecute() {
        //progressDialog = ProgressDialog.show(MainActivity.getBaseContext(),"Aguarde","Buscando dados");
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

        return filmeList;
    }

}
