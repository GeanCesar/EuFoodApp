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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.DetalheItemListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.util.AccountManagerUtil;
import br.com.geancesar.eufood.util.Util;
import br.com.geancesar.uicomponents.componentes.NumberSelector;
import br.com.geancesar.uicomponents.componentes.listeners.NumberSelectorListener;

public class ListItemSubItemAdapter extends RecyclerView.Adapter<ListItemSubItemAdapter.ViewHolder>{
    CategoriaSubItemRest categoria;
    List<ItemCardapio> itens;
    private LayoutInflater inflater;
    Context context;
    DetalheItemListener listener;

    public ListItemSubItemAdapter(Context context, List<ItemCardapio> itens, CategoriaSubItemRest categoria, DetalheItemListener listener){
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
        this.context = context;
        this.categoria = categoria;
        this.listener = listener;
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
        holder.nsQuantidade.setValorMinimo(0);
        holder.nsQuantidade.setValorMaximo(categoria.getQuantidadeMaxima());
        holder.nsQuantidade.atualizaState();
        holder.nsQuantidade.setValor(itens.get(position).getQuantidadeSelecionada());
        holder.nsQuantidade.setListener(new NumberSelectorListener() {
            @Override
            public void adiciona() {
                itens.get(position).setQuantidadeSelecionada(holder.nsQuantidade.getValor());
                listener.adicionouSubItem(itens.get(position));
            }

            @Override
            public void remove() {
                itens.get(position).setQuantidadeSelecionada(holder.nsQuantidade.getValor());
                listener.removeuSubItem(itens.get(position));
            }
        });

        if(itens.get(position).isQuantidadeBloqueada()) {
            holder.nsQuantidade.bloqueiaAdicionar();
        } else {
            holder.nsQuantidade.atualizaState();
        }

        if(itens.get(position).getDescricao() == null || itens.get(position).getDescricao().isEmpty()) {
            holder.tvDescricaoItemCardapio.setVisibility(GONE);
        }

        carregaImagem(holder, position);
    }

    private void carregaImagem(ViewHolder holder, int position){
        GlideUrl glideUrl = new GlideUrl("http://192.168.15.103:8080/restaurante/sub_item/imagem_item?uuid-item-cardapio=" + itens.get(position).getUuid(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AccountManagerUtil.getInstance().getToken(inflater.getContext()))
                .build());

        Glide
                .with(context)
                .load(glideUrl)
                .into(holder.ivIconeItemCardapio);
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
