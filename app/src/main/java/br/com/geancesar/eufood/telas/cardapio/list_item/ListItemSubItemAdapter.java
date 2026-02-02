package br.com.geancesar.eufood.telas.cardapio.list_item;

import static android.view.View.GONE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.util.Util;
import br.com.geancesar.uicomponents.componentes.NumberSelector;

public class ListItemSubItemAdapter extends RecyclerView.Adapter<ListItemSubItemAdapter.ViewHolder>{
    CategoriaSubItemRest categoria;
    List<ItemCardapio> itens;
    private LayoutInflater inflater;
    Context context;

    public ListItemSubItemAdapter(Context context, List<ItemCardapio> itens, CategoriaSubItemRest categoria){
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
        this.context = context;
        this.categoria = categoria;
    }

    @NonNull
    @Override
    public ListItemSubItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_sub_item, parent, false);
        return new ListItemSubItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemSubItemAdapter.ViewHolder holder, int position) {
        holder.tvNomeItemCardapio.setText(itens.get(position).getNome());
        holder.tvPrecoItemCardapio.setText("+ " + Util.getInstance().formataMoeda(itens.get(position).getValor()));
        holder.tvDescricaoItemCardapio.setText(itens.get(position).getDescricao());
        holder.nsQuantidade.setValorMaximo(categoria.getQuantidadeMaxima());

        if(itens.get(position).getDescricao() == null || itens.get(position).getDescricao().isEmpty()) {
            holder.tvDescricaoItemCardapio.setVisibility(GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llItem;
        TextView tvNomeItemCardapio;
        TextView tvDescricaoItemCardapio;
        TextView tvPrecoItemCardapio;
        ImageView ivIconeItemCardapio;
        NumberSelector nsQuantidade;

        ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            tvNomeItemCardapio = itemView.findViewById(R.id.tvNomeItemCardapio);
            tvDescricaoItemCardapio = itemView.findViewById(R.id.tvDescricaoItemCardapio);
            tvPrecoItemCardapio = itemView.findViewById(R.id.tvPrecoItemCardapio);
            ivIconeItemCardapio = itemView.findViewById(R.id.ivIconeItemCardapio);
            nsQuantidade = itemView.findViewById(R.id.nsQuantidade);
        }
    }

}
