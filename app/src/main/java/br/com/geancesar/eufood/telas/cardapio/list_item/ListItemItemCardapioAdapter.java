package br.com.geancesar.eufood.telas.cardapio.list_item;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.telas.cardapio.requests.CarregarImagemItemCardapioTask;
import br.com.geancesar.eufood.request.model.RespostaRequisicao;
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

        if(itens.get(position).getImagemBaixada() != null) {
            byte[] decodedString = Base64.decode(itens.get(position).getImagemBaixada(), Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivIconeItemCardapio.setImageBitmap(imagem);
        } else if(!itens.get(position).isBuscouImagem()){
            buscaImagemItem(position);
        }

        holder.llItem.setOnClickListener( l -> {
            listener.detalheItem(itens.get(position));
        });
    }

    private void buscaImagemItem(int position) {
        CarregarImagemItemCardapioTask task = new CarregarImagemItemCardapioTask(listener, itens.get(position).getUuid(), AccountManagerUtil.getInstance().getToken(inflater.getContext()));
        itens.get(position).setBuscouImagem(true);
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                RespostaRequisicao resp = task.executa();
                handler.post(() -> task.posExecucao(resp));
            });
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
