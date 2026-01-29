package br.com.geancesar.eufood.telas.cardapio.list_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaItemCardapio;

public class ListItemCategoriaCardapioAdapter extends RecyclerView.Adapter<ListItemCategoriaCardapioAdapter.ViewHolder> {

    List<CategoriaItemCardapio> categorias;
    private LayoutInflater inflater;
    RestauranteListener listener;
    Context context;

    public ListItemCategoriaCardapioAdapter(Context context, List<CategoriaItemCardapio> categorias, RestauranteListener listener){
        this.inflater = LayoutInflater.from(context);
        this.categorias = categorias;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemCategoriaCardapioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_categoria_cardapio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCategoriaCardapioAdapter.ViewHolder holder, int position) {
        holder.tvCategoria.setText(categorias.get(position).getDescricao());
        atualizaLista(holder, position);
    }

    private void atualizaLista(ViewHolder holder, int posicao){
        if(categorias.get(posicao).getItens() != null) {
            holder.rvItensCardapio.setLayoutManager(new LinearLayoutManager(context));
            holder.rvItensCardapio.setAdapter(new ListItemItemCardapioAdapter(context, categorias.get(posicao).getItens(), listener));
            if(holder.rvItensCardapio.getAdapter() != null){
                holder.rvItensCardapio.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoria;
        RecyclerView rvItensCardapio;

        ViewHolder(View itemView) {
            super(itemView);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            rvItensCardapio = itemView.findViewById(R.id.rvItensCardapio);
        }
    }
}
