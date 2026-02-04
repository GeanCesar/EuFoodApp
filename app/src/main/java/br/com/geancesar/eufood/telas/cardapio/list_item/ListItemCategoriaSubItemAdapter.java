package br.com.geancesar.eufood.telas.cardapio.list_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.DetalheItemListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;

public class ListItemCategoriaSubItemAdapter extends RecyclerView.Adapter<ListItemCategoriaSubItemAdapter.ViewHolder> {

    List<CategoriaSubItemRest> itens;
    private LayoutInflater inflater;
    Context context;
    DetalheItemListener listener;

    public ListItemCategoriaSubItemAdapter(Context context, List<CategoriaSubItemRest> itens, DetalheItemListener listener){
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_categoria_sub_item, parent, false);
        return new ListItemCategoriaSubItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDescricaoCategoria.setText(itens.get(position).getDescricao());
        holder.tvEscolhaAte.setText("Escolha até " + itens.get(position).getQuantidadeMaxima() + " opções");
        holder.tvObrigatorio.setVisibility(itens.get(position).getQuantidadeMinima() > 0 ? View.VISIBLE : View.GONE);
        atualizaLista(holder, position);
    }

    private void atualizaLista(ListItemCategoriaSubItemAdapter.ViewHolder holder, int posicao){
        if(itens.get(posicao).getItens() != null) {
            holder.rvSubItens.setLayoutManager(new LinearLayoutManager(context));
            holder.rvSubItens.setAdapter(new ListItemSubItemAdapter(context, itens.get(posicao).getItens(), itens.get(posicao), listener));
            if(holder.rvSubItens.getAdapter() != null){
                holder.rvSubItens.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llItem;
        TextView tvDescricaoCategoria;
        TextView tvObrigatorio;
        TextView tvEscolhaAte;
        RecyclerView rvSubItens;

        ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            tvDescricaoCategoria = itemView.findViewById(R.id.tvDescricaoCategoria);
            tvEscolhaAte = itemView.findViewById(R.id.tvEscolhaAte);
            tvObrigatorio = itemView.findViewById(R.id.tvObrigatorio);
            rvSubItens = itemView.findViewById(R.id.rvSubItens);
        }
    }
}
