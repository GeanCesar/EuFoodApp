package br.com.geancesar.eufood.telas.cardapio.list_item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.util.AccountManagerUtil;
import br.com.geancesar.eufood.util.Util;

public class ListItemItemCardapioAdapter extends RecyclerView.Adapter<ListItemItemCardapioAdapter.ViewHolder> {

    List<ItemCardapio> itens;
    private LayoutInflater inflater;
    RestauranteListener listener;

    public ListItemItemCardapioAdapter(Context context, List<ItemCardapio> itens, RestauranteListener listener){
        this.inflater = LayoutInflater.from(context);
        this.itens = itens;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListItemItemCardapioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_card_item_cardapio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemItemCardapioAdapter.ViewHolder holder, int position) {
        holder.tvDescricaoItemCardapio.setText(itens.get(position).getDescricao());
        holder.tvNomeItemCardapio.setText(itens.get(position).getNome());
        holder.tvPrecoItemCardapio.setText(Util.getInstance().formataMoeda(itens.get(position).getValor()));

        buscaImagemItem(holder, position);

        holder.llItem.setOnClickListener( l -> {
            listener.detalheItem(itens.get(position));
        });
    }

    private void buscaImagemItem(ViewHolder holder, int position) {
        GlideUrl glideUrl = new GlideUrl("http://192.168.15.103:8080/restaurante/item_cardapio/imagem_item?uuid-item-cardapio=" + itens.get(position).getUuid(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AccountManagerUtil.getInstance().getToken(inflater.getContext()))
                .build());

        Glide
                .with(inflater.getContext())
                .load(glideUrl)
                .addListener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        itens.get(position).setImagemBaixada(resource);
                        return false;
                    }
                })
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

        ViewHolder(View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            tvNomeItemCardapio = itemView.findViewById(R.id.tvNomeItemCardapio);
            tvDescricaoItemCardapio = itemView.findViewById(R.id.tvDescricaoItemCardapio);
            tvPrecoItemCardapio = itemView.findViewById(R.id.tvPrecoItemCardapio);
            ivIconeItemCardapio = itemView.findViewById(R.id.ivIconeItemCardapio);
        }
    }
}
