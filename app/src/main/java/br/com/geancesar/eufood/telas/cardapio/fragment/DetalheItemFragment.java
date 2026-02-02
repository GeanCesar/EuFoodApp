package br.com.geancesar.eufood.telas.cardapio.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.list_item.ListItemCategoriaSubItemAdapter;
import br.com.geancesar.eufood.telas.cardapio.listener.DetalheItemListener;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.telas.cardapio.requests.ListarCategoriasSubItensTask;
import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarCategoriasSubitem;
import br.com.geancesar.eufood.util.AccountManagerUtil;
import br.com.geancesar.uicomponents.componentes.NumberSelector;
import br.com.geancesar.uicomponents.componentes.ValueButton;
import br.com.geancesar.uicomponents.componentes.listeners.NumberSelectorListener;

public class DetalheItemFragment extends Fragment implements DetalheItemListener {

    ItemCardapio item;
    TextView tvNomeItem;
    TextView tvDescricaoItem;
    ImageView ivIcone;
    ValueButton btAdicionar;
    NumberSelector nsQuantidade;
    RecyclerView rvCategoria;
    LinearLayout llRecolher;
    RestauranteListener listener;
    List<CategoriaSubItemRest> itensCategoria;

    public DetalheItemFragment(){}
    
    public DetalheItemFragment(RestauranteListener listener, ItemCardapio item){
        this.item = item;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_cardapio, container, false);
        tvNomeItem = view.findViewById(R.id.tvNomeItem);
        tvDescricaoItem = view.findViewById(R.id.tvDescricaoItem);
        ivIcone = view.findViewById(R.id.ivImagemItem);
        btAdicionar = view.findViewById(R.id.btAdicionar);
        nsQuantidade = view.findViewById(R.id.nsQuantidade);
        llRecolher = view.findViewById(R.id.llRecolher);
        rvCategoria = view.findViewById(R.id.rvCategorias);

        tvNomeItem.setText(item.getNome());
        tvDescricaoItem.setText(item.getDescricao());

        if(item.getImagemBaixada() != null) {
            byte[] decodedString = Base64.decode(item.getImagemBaixada(), Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivIcone.setImageBitmap(imagem);
        }

        nsQuantidade.setListener(new NumberSelectorListener(){
            @Override
            public void adiciona() {
                atualizaTextoBotao();
            }

            @Override
            public void remove() {
                atualizaTextoBotao();
            }
        });

        atualizaTextoBotao();
        llRecolher.setOnClickListener(l -> listener.fecharDetalheItem());

        buscarCategorias();

        return view;
    }

    private void buscarCategorias() {
        ListarCategoriasSubItensTask task = new ListarCategoriasSubItensTask(
                item.getRestaurante().getUuid(),
                item.getUuid(), AccountManagerUtil.getInstance().getToken(getContext()),
                this);

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                RespostaListarCategoriasSubitem resp = task.executa();
                handler.post(() -> task.posExecucao(resp));
            });
        }
    }

    private void atualizaListaSubItens(){
        rvCategoria.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategoria.setAdapter(new ListItemCategoriaSubItemAdapter(getContext(), itensCategoria));
        rvCategoria.getAdapter().notifyDataSetChanged();
    }

    private void atualizaTextoBotao(){
        double valor = nsQuantidade.getValor() * item.getValor().doubleValue();
        btAdicionar.setValor(BigDecimal.valueOf(valor));
    }

    @Override
    public void buscarCategoriaSubitem(List<CategoriaSubItemRest> categorias) {
        itensCategoria = categorias;
        atualizaListaSubItens();
    }
}
