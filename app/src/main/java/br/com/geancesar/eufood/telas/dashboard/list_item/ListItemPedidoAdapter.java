package br.com.geancesar.eufood.telas.dashboard.list_item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import br.com.geancesar.eufood.telas.dashboard.listener.PedidosListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;
import br.com.geancesar.eufood.telas.dashboard.requests.ConsultarRestauranteTask;
import br.com.geancesar.eufood.util.AccountManagerUtil;
import br.com.geancesar.eufood.util.Util;

public class ListItemPedidoAdapter extends RecyclerView.Adapter<ListItemPedidoAdapter.ViewHolder> {

    List<ConsultaPedidoRest> pedidos;
    private LayoutInflater inflater;
    private PedidosListener listener;
    private Context context;

    public ListItemPedidoAdapter(Context context, List<ConsultaPedidoRest> pedidos, PedidosListener listener) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.pedidos = pedidos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_card_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDataPedido.setText(Util.getInstance().formataData(pedidos.get(position).getDataCriacao()));
        holder.tvValorTotal.setText(Util.getInstance().formataMoeda(pedidos.get(position).getValorTotal()));

        GlideUrl glideUrl = new GlideUrl("http://192.168.15.103:8080/restaurante/imagem_perfil?uuid-restaurante=" + pedidos.get(position).getUuidRestaurante(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AccountManagerUtil.getInstance().getToken(context))
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
                        pedidos.get(position).setImagemRestauranteBaixada(resource);
                        return false;
                    }
                })
                .into(holder.ivIconeRestaurante);

        ConsultarRestauranteTask task = new ConsultarRestauranteTask(new PedidosListener() {
            @Override
            public void pedidoSelecionado(int position) {}

            @Override
            public void pedidosConsultados(List<ConsultaPedidoRest> pedidos) {}

            @Override
            public void restauranteConsultado(Restaurante restaurante) {
                pedidos.get(position).setNomeRestaurante(restaurante.getNome());
                holder.tvNomeRestaurante.setText(pedidos.get(position).getNomeRestaurante());
            }
        }, AccountManagerUtil.getInstance().getToken(context), pedidos.get(position).getUuidRestaurante());

        task.execute();
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNomeRestaurante;
        TextView tvStatus;
        TextView tvDataPedido;
        TextView tvValorTotal;
        ImageView ivIconeRestaurante;

        ViewHolder(View itemView) {
            super(itemView);
            tvNomeRestaurante = itemView.findViewById(R.id.tvNomeRestaurantePedido);
            tvStatus = itemView.findViewById(R.id.tvStatusPedido);
            tvDataPedido = itemView.findViewById(R.id.tvDataPedido);
            tvValorTotal = itemView.findViewById(R.id.tvValorTotalPedido);
            ivIconeRestaurante = itemView.findViewById(R.id.ivIconeRestaurantePedido);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) listener.pedidoSelecionado(getAdapterPosition());
        }
    }
}
