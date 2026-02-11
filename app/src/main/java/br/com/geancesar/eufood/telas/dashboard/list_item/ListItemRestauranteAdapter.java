package br.com.geancesar.eufood.telas.dashboard.list_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.dashboard.listener.DashboardListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class ListItemRestauranteAdapter extends RecyclerView.Adapter<ListItemRestauranteAdapter.ViewHolder>  {

    List<Restaurante> restaurantes;

    private LayoutInflater inflater;

    static DashboardListener listener;

    public ListItemRestauranteAdapter(Context context, List<Restaurante> restaurantes, DashboardListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.restaurantes = restaurantes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_card_restaurante_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNomeRestaurante.setText(restaurantes.get(position).getNome());
        buscaImagem(holder, position);
    }

    private void buscaImagem(ViewHolder holder, int position) {
        GlideUrl glideUrl = new GlideUrl("http://192.168.15.103:8080/restaurante/imagem_perfil?uuid-restaurante=" + restaurantes.get(position).getUuid(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AccountManagerUtil.getInstance().getToken(inflater.getContext()))
                .build());

        Glide
                .with(inflater.getContext())
                .load(glideUrl)
                .into(holder.ivIconeRestaurante);
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNomeRestaurante;
        TextView tvCategoriaRestaurante;
        ImageView ivIconeRestaurante;

        ViewHolder(View itemView) {
            super(itemView);
            tvNomeRestaurante = itemView.findViewById(R.id.tvNomeRestaurante);
            tvCategoriaRestaurante = itemView.findViewById(R.id.tvCategoriaRestaurante);
            ivIconeRestaurante = itemView.findViewById(R.id.ivIconeRestaurante);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) listener.onItemClick(getAdapterPosition());
        }
    }

    public void setRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }
}
