package br.ucb.upcoming.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dougl.desafioemprestimorealm.entity.Emprestimo;

import java.util.List;

import br.ucb.upcoming.R;
import br.ucb.upcoming.entity.Filme;

/**
 * Created by dougl on 17/10/2017.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    List<Filme> filmes;

    public RecycleViewAdapter(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
           // mTextView = v.findViewById(R.id.objeto);
            mTextView.setTypeface(null, Typeface.BOLD);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long id = getItemId();
                    Filme emprestimo = Filme.findById(Filme.class,id);

                }
            });

        }
    }

    @Override
    public long getItemId(int position) {
        return filmes.get(position).getId();
    }

    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_filmes,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.ViewHolder holder, int position) {
        //holder.mTextView.setText(filmes.get(position).getObjeto());
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }
}
