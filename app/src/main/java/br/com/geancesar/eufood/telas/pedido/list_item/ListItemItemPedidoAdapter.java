package br.com.geancesar.eufood.telas.pedido.list_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.pedido.listener.DetalhePedidoListener;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;
import br.com.geancesar.eufood.util.Util;
import br.com.geancesar.uicomponents.componentes.NumberSelector;
import br.com.geancesar.uicomponents.componentes.listeners.NumberSelectorListener;

public class ListItemItemPedidoAdapter extends RecyclerView.Adapter<ListItemItemPedidoAdapter.ViewHolder>{

    CriacaoPedidoRest pedido;
    private LayoutInflater inflater;
    DetalhePedidoListener listener;
    Context context;

    public ListItemItemPedidoAdapter(Context context, CriacaoPedidoRest pedido, DetalhePedidoListener listener){
        this.inflater = LayoutInflater.from(context);
        this.pedido = pedido;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemItemPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_item_sacola, parent, false);
        return new ListItemItemPedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemItemPedidoAdapter.ViewHolder holder, int position) {
        holder.tvDescricao.setText(pedido.getItems().get(position).getDescricao());
        holder.tvNomePrato.setText(pedido.getItems().get(position).getNome());
        holder.tvTotal.setText(Util.getInstance().formataMoeda(pedido.getItems().get(position).getValorTotal()));
        holder.nsQuantidade.setValor(pedido.getItems().get(position).getQuantidade().intValue());
        holder.nsQuantidade.atualizaState();
        holder.tvDescricao.setText(pedido.getItems().get(position).getDescricao());

        holder.nsQuantidade.setListener(new NumberSelectorListener() {
            @Override
            public void adiciona() {
                if(listener != null) {
                    listener.adicionaItem(pedido.getItems().get(position));
                }
            }
            @Override
            public void remove() {
                if(listener != null) {
                    listener.removeItem(pedido.getItems().get(position));
                }
            }
        });

        Glide
                .with(context)
                .load(pedido.getItems().get(position).getImagemBaixada())
                .into(holder.ivIconeItem);

        atualizaLista(holder, position);
    }

    private void atualizaLista(ListItemItemPedidoAdapter.ViewHolder holder, int posicao){
        if(pedido.getItems().get(posicao).getSubItems() != null) {
            holder.rvSubItens.setLayoutManager(new LinearLayoutManager(context));
            holder.rvSubItens.setAdapter(new ListItemSubItemPedidoAdapter(context, pedido.getItems().get(posicao)));
            if(holder.rvSubItens.getAdapter() != null){
                holder.rvSubItens.getAdapter().notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getItemCount() {
        return pedido.getItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomePrato;
        TextView tvDescricao;
        TextView tvTotal;
        NumberSelector nsQuantidade;
        RecyclerView rvSubItens;
        ImageView ivIconeItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvNomePrato = itemView.findViewById(R.id.tvNomeItemCardapio);
            tvDescricao = itemView.findViewById(R.id.tvDescricaoItemCardapio);
            tvTotal = itemView.findViewById(R.id.tvPrecoItemCardapio);
            nsQuantidade = itemView.findViewById(R.id.nsQuantidade);
            rvSubItens = itemView.findViewById(R.id.rvSubItem);
            ivIconeItem= itemView.findViewById(R.id.ivIconeItemCardapio);
        }
    }

}
