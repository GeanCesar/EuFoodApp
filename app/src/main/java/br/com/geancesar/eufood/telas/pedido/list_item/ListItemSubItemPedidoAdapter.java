package br.com.geancesar.eufood.telas.pedido.list_item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;

public class ListItemSubItemPedidoAdapter extends RecyclerView.Adapter<ListItemSubItemPedidoAdapter.ViewHolder>{

    CriacaoPedidoItemRest item;
    private LayoutInflater inflater;
    Context context;

    public ListItemSubItemPedidoAdapter(Context context, CriacaoPedidoItemRest item){
        this.inflater = LayoutInflater.from(context);
        this.item = item;
        this.context = context;
    }

    @NonNull
    @Override
    public ListItemSubItemPedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_sub_item_sacola, parent, false);
        return new ListItemSubItemPedidoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemSubItemPedidoAdapter.ViewHolder holder, int position) {
        holder.tvQuantidadeSubItem.setText(item.getSubItems().get(position).getQuantidade() + "");
        holder.tvDescricaoSubItem.setText(item.getSubItems().get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return item.getSubItems().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantidadeSubItem;
        TextView tvDescricaoSubItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvQuantidadeSubItem = itemView.findViewById(R.id.tvQuantidadeSubItem);
            tvDescricaoSubItem = itemView.findViewById(R.id.tvDescricaoSubItem);
        }
    }

}
