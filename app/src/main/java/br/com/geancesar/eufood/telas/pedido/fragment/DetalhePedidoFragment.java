package br.com.geancesar.eufood.telas.pedido.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.math.BigDecimal;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.pedido.list_item.ListItemItemPedidoAdapter;
import br.com.geancesar.eufood.telas.pedido.listener.DetalhePedidoListener;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;
import br.com.geancesar.eufood.util.Util;

public class DetalhePedidoFragment extends Fragment implements DetalhePedidoListener {

    private TextView tvNomeRestaurante;
    private TextView tvSubTotal;
    private TextView tvTaxaEntrega;
    private TextView tvTotal;
    private TextView tvTotalSacola;
    private TextView tvQtdItens;
    private AppCompatButton btContinuar;
    private RecyclerView rvItensAdicionados;
    private ImageView ivIconeRestaurante;
    private LinearLayout llRecolher;
    private CardView cvCriacaoPedido;

    CriacaoPedidoRest pedido;

    Restaurante restaurante;

    RestauranteListener listener;

    // TODO Incluir frete dinamico do restaurante
    private final BigDecimal valorFrete = BigDecimal.valueOf(4.99);

    SlideUp slideUpCriacaoPedido;

    public DetalhePedidoFragment(CriacaoPedidoRest pedido, Restaurante restaurante, RestauranteListener listener) {
        setPedido(pedido);
        setRestaurante(restaurante);
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhe_pedido, container, false);
        tvNomeRestaurante = view.findViewById(R.id.tvNomeRestaurante);
        tvSubTotal = view.findViewById(R.id.tvSubTotal);
        tvTaxaEntrega = view.findViewById(R.id.tvTaxaEntrega);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvTotalSacola = view.findViewById(R.id.tvTotalSacola);
        tvQtdItens = view.findViewById(R.id.tvQtdItens);
        llRecolher = view.findViewById(R.id.llRecolher);
        btContinuar = view.findViewById(R.id.btContinuar);
        rvItensAdicionados = view.findViewById(R.id.rvItensAdicionados);
        ivIconeRestaurante = view.findViewById(R.id.ivIconeRestaurante);
        cvCriacaoPedido = view.findViewById(R.id.cvCriacaoPedido);

        llRecolher.setOnClickListener(l -> {
            listener.fecharSacola();
        });
        carregaDados();

        slideUpCriacaoPedido = new SlideUpBuilder(cvCriacaoPedido)
                .withGesturesEnabled(false)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();

        btContinuar.setOnClickListener(l -> {
            CriacaoPedidoFragment fragment = new CriacaoPedidoFragment(pedido, this);
            getParentFragmentManager().beginTransaction().replace(R.id.flCriacaoPedido, fragment).commit();
            slideUpCriacaoPedido.show();
        });

        return view;
    }

    public void carregaDados(){
        tvNomeRestaurante.setText(restaurante.getNome());
        tvTaxaEntrega.setText(Util.getInstance().formataMoeda(valorFrete));
        tvQtdItens.setText(pedido.getItems().size() + " itens adicionados");
        atualizaTotais();

        Glide
                .with(getContext())
                .load(restaurante.getImagemBaixada())
                .into(ivIconeRestaurante);

        rvItensAdicionados.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItensAdicionados.setAdapter(new ListItemItemPedidoAdapter(getContext(), pedido, this));
        rvItensAdicionados.getAdapter().notifyDataSetChanged();
    }

    private void atualizaTotais(){
        tvSubTotal.setText(Util.getInstance().formataMoeda(getTotalPedido()));
        tvTotal.setText(Util.getInstance().formataMoeda(getTotalPedido().add(valorFrete)));
        tvTotalSacola.setText(Util.getInstance().formataMoeda(getTotalPedido().add(valorFrete)));
    }

    private BigDecimal getTotalPedido(){
        BigDecimal valorTotal = BigDecimal.ZERO;
        if(pedido != null) {
            for(CriacaoPedidoItemRest item : pedido.getItems()) {
                valorTotal = valorTotal.add(item.getValorTotal());
            }
        }
        return  valorTotal;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void setPedido(CriacaoPedidoRest pedido) {
        this.pedido = pedido;
    }

    @Override
    public void adicionaItem(CriacaoPedidoItemRest item) {
        BigDecimal precoPorItem = item.getValorTotal().divide(item.getQuantidade());
        item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
        item.setValorTotal(precoPorItem.multiply(item.getQuantidade()));
        atualizaTotais();
    }

    @Override
    public void removeItem(CriacaoPedidoItemRest item) {
        BigDecimal precoPorItem = item.getValorTotal().divide(item.getQuantidade());
        item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
        item.setValorTotal(precoPorItem.multiply(item.getQuantidade()));
        atualizaTotais();
    }

    @Override
    public void pedidoCriado(CriacaoPedidoRest pedido) {
        slideUpCriacaoPedido.hide();
        listener.pedidoGerado();
    }
}
