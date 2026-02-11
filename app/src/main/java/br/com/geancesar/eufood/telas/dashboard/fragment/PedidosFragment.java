package br.com.geancesar.eufood.telas.dashboard.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.dashboard.list_item.ListItemPedidoAdapter;
import br.com.geancesar.eufood.telas.dashboard.listener.PedidosListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;
import br.com.geancesar.eufood.telas.dashboard.requests.ListarPedidosTask;
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class PedidosFragment extends Fragment implements PedidosListener {

    RecyclerView rvPedidos;

    List<ConsultaPedidoRest> pedidos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        rvPedidos = view.findViewById(R.id.rvPedidos);
        rvPedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        listarPedidos();
        return view;
    }

    private void listarPedidos(){
        ListarPedidosTask task = new ListarPedidosTask(AccountManagerUtil.getInstance().getToken(getContext()), this);
        task.execute();
    }

    private void atualizaLista(){
        rvPedidos.setAdapter(new ListItemPedidoAdapter(getContext(), pedidos, this));
        rvPedidos.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void pedidoSelecionado(int position) {

    }

    @Override
    public void pedidosConsultados(List<ConsultaPedidoRest> pedidos) {
        this.pedidos = pedidos;
        atualizaLista();
    }

    @Override
    public void restauranteConsultado(Restaurante restaurante) {

    }
}
