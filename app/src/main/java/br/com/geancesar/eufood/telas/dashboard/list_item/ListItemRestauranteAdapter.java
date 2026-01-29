package br.com.geancesar.eufood.telas.dashboard.list_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.dashboard.listener.DashboardListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.requests.CarregarImagemCapaRestauranteTask;
import br.com.geancesar.eufood.telas.dashboard.requests.CarregarImagemRestauranteTask;
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class ListItemRestauranteAdapter extends RecyclerView.Adapter<ListItemRestauranteAdapter.ViewHolder>  {

    List<Restaurante> restaurantes;

    private LayoutInflater inflater;

    DashboardListener listener;

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

        if(restaurantes.get(position).getImagemBaixada() != null) {
            byte[] decodedString = Base64.decode(restaurantes.get(position).getImagemBaixada(), Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivIconeRestaurante.setImageBitmap(imagem);
        } else if(!restaurantes.get(position).isBuscouImagem()){
            CarregarImagemRestauranteTask carregarImagemTask = new CarregarImagemRestauranteTask(listener, restaurantes.get(position).getUuid(), holder, AccountManagerUtil.getInstance().getToken(inflater.getContext()));
            carregarImagemTask.execute();

            CarregarImagemCapaRestauranteTask carregarImagemCapaTask = new CarregarImagemCapaRestauranteTask(listener, restaurantes.get(position).getUuid(), holder, AccountManagerUtil.getInstance().getToken(inflater.getContext()));
            carregarImagemCapaTask.execute();

            restaurantes.get(position).setBuscouImagem(true);
        }
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

}
