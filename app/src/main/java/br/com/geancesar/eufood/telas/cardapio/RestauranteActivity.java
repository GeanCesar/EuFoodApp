package br.com.geancesar.eufood.telas.cardapio;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.databinding.ActivityRestauranteBinding;
import br.com.geancesar.eufood.telas.cardapio.fragment.DetalheItemFragment;
import br.com.geancesar.eufood.telas.cardapio.list_item.ListItemCategoriaCardapioAdapter;
import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaItemCardapio;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.telas.cardapio.requests.ListarCategoriaItensTask;
import br.com.geancesar.eufood.telas.cardapio.requests.ListarItensCardapioTask;
import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarCategorias;
import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarItensRestaurante;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.pedido.fragment.DetalhePedidoFragment;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;
import br.com.geancesar.eufood.util.AccountManagerUtil;
import br.com.geancesar.eufood.util.Util;

public class RestauranteActivity extends AppCompatActivity implements RestauranteListener {

    ActivityRestauranteBinding binding;

    ImageView ivIconeRestaurante;
    TextView tvNomeRestaurante;
    TextView tvTotalSacola;
    RecyclerView rvCardapio;
    Button btSacola;
    CardView cvSacola;
    CardView cvDetalheItem;
    CardView cvDetalhePedido;

    Restaurante restaurante;
    List<ItemCardapio> itensCardapio = new ArrayList<>();
    List<CategoriaItemCardapio> categorias = new ArrayList<>();

    List<CriacaoPedidoItemRest> itensAdicionados = new ArrayList<>();

    SlideUp slideUp;
    SlideUp slideUpDetalheItem;
    SlideUp slideUpDetalhePedido;

    BigDecimal valorTotal = BigDecimal.ZERO;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btSacola = findViewById(R.id.btContinuar);

        restaurante = getIntent().getExtras().getSerializable("restaurante", Restaurante.class);

        tvTotalSacola = findViewById(R.id.tvTotalSacola);
        tvNomeRestaurante = findViewById(R.id.tvNomeRestaurante);
        rvCardapio = findViewById(R.id.rvCardapio);
        ivIconeRestaurante = findViewById(R.id.ivIconeRestaurante);
        cvDetalheItem = findViewById(R.id.cvDetalheItem);
        cvDetalhePedido = findViewById(R.id.cvConteudoDashboard);

        buscarItens();
        carregaDados();

        cvSacola = findViewById(R.id.cvSacola);
        slideUp = new SlideUpBuilder(cvSacola)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();

        slideUpDetalheItem = new SlideUpBuilder(cvDetalheItem)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();

        slideUpDetalhePedido = new SlideUpBuilder(cvDetalhePedido)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();

        btSacola.setOnClickListener(l -> {
            CriacaoPedidoRest pedidoRest = getPedidoRest();
            DetalhePedidoFragment fragment = new DetalhePedidoFragment(pedidoRest, restaurante, this);
            getSupportFragmentManager().beginTransaction().replace(R.id.flConteudoDashboard, fragment).commit();

            slideUpDetalhePedido.show();
        });
    }

    private CriacaoPedidoRest getPedidoRest() {
        CriacaoPedidoRest pedidoRest = new CriacaoPedidoRest();
        pedidoRest.setUuidRestaurante(restaurante.getUuid());
        pedidoRest.setItems(itensAdicionados);

        return pedidoRest;
    }

    private void buscaImagem() {
        GlideUrl glideUrl = new GlideUrl("http://192.168.15.103:8080/restaurante/imagem_perfil?uuid-restaurante=" + restaurante.getUuid(), new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer " + AccountManagerUtil.getInstance().getToken(this))
                .build());

        Glide
                .with(this)
                .load(glideUrl)
                .addListener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        restaurante.setImagemBaixada(resource);
                        return false;
                    }
                })
                .into(ivIconeRestaurante);
    }

    private void carregaDados() {
        tvNomeRestaurante.setText(restaurante.getNome());
        buscaImagem();
    }

    private void atualizaLista(){
        rvCardapio.setLayoutManager(new LinearLayoutManager(this));
        rvCardapio.setAdapter(new ListItemCategoriaCardapioAdapter(this, categorias, this));
        if(rvCardapio.getAdapter() != null){
            rvCardapio.getAdapter().notifyDataSetChanged();
        }
    }

    private void buscarCategorias(){
        ListarCategoriaItensTask task = new ListarCategoriaItensTask( restaurante.getUuid(), AccountManagerUtil.getInstance().getToken(this));
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                RespostaListarCategorias resp = task.executa();
                handler.post(() -> processaRespostaCategorias(resp));
            });
        }
    }

    private void buscarItens(){
        ListarItensCardapioTask task = new ListarItensCardapioTask(this, restaurante.getUuid(), AccountManagerUtil.getInstance().getToken(this));
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                RespostaListarItensRestaurante resp = task.executa();
                handler.post(() -> task.posExecucao(resp));
            });
        }
    }

    private void processaRespostaCategorias(RespostaListarCategorias resp) {
        if(resp.isOk()) {
            categorias.addAll(resp.getExtra());
        }

        Map<CategoriaItemCardapio, List<ItemCardapio>> categoriasComItens =
                itensCardapio.stream().collect(Collectors.groupingBy(ItemCardapio::getCategoria));

        for(CategoriaItemCardapio categoria : categorias) {
            categoria.setItens(categoriasComItens.get(categoria));
        }

        atualizaLista();
    }

    @Override
    public void listaItens(List<ItemCardapio> itens) {
        itensCardapio = itens;
        buscarCategorias();
    }

    @Override
    public void detalheItem(ItemCardapio item) {
        item.setRestaurante(restaurante);
        getSupportFragmentManager().beginTransaction().replace(R.id.flDetalheItem, new DetalheItemFragment(this, item)).commit();
        slideUpDetalheItem.show();
    }

    @Override
    public void fecharDetalheItem() {
        slideUpDetalheItem.hide();
    }

    @Override
    public void fecharSacola() {
        slideUpDetalhePedido.hide();
    }

    @Override
    public void adicionarItemSacola(CriacaoPedidoItemRest item) {
        slideUpDetalheItem.hide();
        if(!slideUp.isVisible()){
            slideUp.show();
        }

        itensAdicionados.add(item);
        atualizaTotal();
    }

    @Override
    public void pedidoGerado() {
        finish();
    }

    private void atualizaTotal() {
        valorTotal = BigDecimal.ZERO;
        for(CriacaoPedidoItemRest item : itensAdicionados)  {
            valorTotal = valorTotal.add(item.getValorTotal());
        }

        tvTotalSacola.setText(Util.getInstance().formataMoeda(valorTotal));
    }
}
