package br.ucb.upcoming.services;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.ucb.upcoming.MainActivity;
import br.ucb.upcoming.Network;

/**
 * Created by dougl on 07/11/2017.
 */

public class Filme extends AsyncTask<String,Void,String> {
    ProgressDialog progressDialog;

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
        progressDialog.dismiss();
    }

}
