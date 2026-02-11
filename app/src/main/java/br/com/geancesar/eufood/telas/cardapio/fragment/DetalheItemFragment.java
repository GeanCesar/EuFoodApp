package br.com.geancesar.eufood.telas.cardapio.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.ArrayList;
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
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoSubItemRest;
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

    boolean contemTodosObrigatorios = true;

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
            Bitmap imagem =  ((BitmapDrawable) item.getImagemBaixada()).getBitmap();
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

        btAdicionar.setOnClickListener(l -> {
            if(listener != null) {
                listener.adicionarItemSacola(montaItem());
            }
        });

        buscarCategorias();

        return view;
    }

    private CriacaoPedidoItemRest montaItem(){
        CriacaoPedidoItemRest itemRest = new CriacaoPedidoItemRest();
        itemRest.setQuantidade(BigDecimal.valueOf(nsQuantidade.getValor()));
        itemRest.setUuid(item.getUuid());
        itemRest.setPreco(item.getValor());
        itemRest.setValorTotal(btAdicionar.getValor());
        itemRest.setSubItems(new ArrayList<>());
        itemRest.setDescricao(item.getDescricao());
        itemRest.setNome(item.getNome());
        itemRest.setImagemBaixada(item.getImagemBaixada());

        if(itensCategoria != null) {
            for(CategoriaSubItemRest categoria : itensCategoria) {
                for(ItemCardapio sub : categoria.getItens()){
                    if(sub.getQuantidadeSelecionada() > 0) {
                        CriacaoPedidoSubItemRest subitem = new CriacaoPedidoSubItemRest();
                        subitem.setUuid(sub.getUuid());
                        subitem.setQuantidade(BigDecimal.valueOf(sub.getQuantidadeSelecionada()));
                        subitem.setValorTotal(sub.getValor().multiply(BigDecimal.valueOf(sub.getQuantidadeSelecionada())));
                        subitem.setNome(sub.getNome());
                        itemRest.getSubItems().add(subitem);
                    }
                }
            }
        }

        return itemRest;
    }

    /**
     * Metodo reponsavel por liberar o botão de adicionar ou não, com base nos itens obrigatórios selecionados
     */
    private void bloqueiaDesbloqueiaBotaoAdicionar(){
        if(contemCategoriaObrigatoria()) {
            verificaObrigatorios(true);
            btAdicionar.setEnabled(contemTodosObrigatorios);
        } else {
            verificaObrigatorios(false);
        }
    }

    private void verificaObrigatorios(boolean precisaObrigatorio) {
        if (itensCategoria != null) {
            contemTodosObrigatorios = false;
            for (CategoriaSubItemRest cat : itensCategoria) {
                if(cat.getQuantidadeMinima() > 0) {
                    contemTodosObrigatorios = cat.getQuantidadeMinima() <= cat.quantidadeSelecionada();
                    if (!contemTodosObrigatorios) {
                        break;
                    }
                }
            }
        }

        if (contemTodosObrigatorios) {
            for (CategoriaSubItemRest cat : itensCategoria) {
                for (ItemCardapio item : cat.getItens()) {
                    item.setQuantidadeBloqueada(true);
                }
            }
            rvCategoria.getAdapter().notifyDataSetChanged();
        } else {
            if(!precisaObrigatorio) {
                for (CategoriaSubItemRest cat : itensCategoria) {
                    if(cat.getQuantidadeMaxima() == cat.quantidadeSelecionada()) {
                        itensCategoria.stream().forEach(categoriaSubItemRest -> categoriaSubItemRest.getItens().stream().forEach(itemCardapio -> itemCardapio.setQuantidadeBloqueada(true)));
                        rvCategoria.getAdapter().notifyDataSetChanged();
                        return;
                    }
                }
            }
            itensCategoria.stream().forEach(categoriaSubItemRest -> categoriaSubItemRest.getItens().stream().forEach(itemCardapio -> itemCardapio.setQuantidadeBloqueada(false)));
            rvCategoria.getAdapter().notifyDataSetChanged();
        }
    }

    private boolean contemCategoriaObrigatoria(){
        boolean contem = false;
        for(CategoriaSubItemRest cat : itensCategoria) {
            if(cat.getQuantidadeMinima() > 0){
                return true;
            }
        }
        return contem;
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
        rvCategoria.setAdapter(new ListItemCategoriaSubItemAdapter(getContext(), itensCategoria, this));
        rvCategoria.getAdapter().notifyDataSetChanged();
        bloqueiaDesbloqueiaBotaoAdicionar();
    }

    private void atualizaTextoBotao(){
        double valor = nsQuantidade.getValor() * item.getValor().doubleValue();

        double valorSubItem = 0;
        if(itensCategoria != null) {
            for(CategoriaSubItemRest cat : itensCategoria){
                for(ItemCardapio sub : cat.getItens()){
                    valorSubItem = valorSubItem + (sub.getQuantidadeSelecionada() * sub.getValor().doubleValue());
                }
            }
        }

        btAdicionar.setValor(BigDecimal.valueOf(valor + valorSubItem));
    }

    @Override
    public void buscarCategoriaSubitem(List<CategoriaSubItemRest> categorias) {
        itensCategoria = categorias;
        atualizaListaSubItens();
    }

    @Override
    public void adicionouSubItem(ItemCardapio item) {
        bloqueiaDesbloqueiaBotaoAdicionar();
        atualizaTextoBotao();
    }

    @Override
    public void removeuSubItem(ItemCardapio item) {
        bloqueiaDesbloqueiaBotaoAdicionar();
        atualizaTextoBotao();
    }
}
