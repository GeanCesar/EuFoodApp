package br.com.geancesar.eufood.telas.pedido.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.pedido.listener.DetalhePedidoListener;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;
import br.com.geancesar.eufood.telas.pedido.requests.CriarPedidoTask;
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class CriacaoPedidoFragment extends Fragment {

    CriacaoPedidoRest pedido;

    DetalhePedidoListener listener;

    public CriacaoPedidoFragment(CriacaoPedidoRest pedido, DetalhePedidoListener listener) {
        this.pedido = pedido;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_criacao_pedido, container, false);
        criaPedido();
        return view;
    }

    private void criaPedido(){
        CriarPedidoTask task = new CriarPedidoTask(pedido, AccountManagerUtil.getInstance().getToken(getContext()), listener);
        task.execute();
    }
}
