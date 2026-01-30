package br.com.geancesar.eufood.telas.cardapio;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.databinding.LayoutRestauranteBinding;
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
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class RestauranteActivity extends AppCompatActivity implements RestauranteListener {

    LayoutRestauranteBinding binding;
    ImageView ivIconeRestaurante;
    TextView tvNomeRestaurante;
    RecyclerView rvCardapio;

    Button btSacola;

    CardView cvSacola;

    CardView cvDetalheItem;

    FrameLayout flDetalheItem;

    Restaurante restaurante;
    List<ItemCardapio> itensCardapio = new ArrayList<>();
    List<CategoriaItemCardapio> categorias = new ArrayList<>();

    SlideUp slideUp;
    SlideUp slideUpDetalheItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = LayoutRestauranteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btSacola = findViewById(R.id.btVerSacola);

        restaurante = getIntent().getExtras().getSerializable("restaurante", Restaurante.class);

        tvNomeRestaurante = findViewById(R.id.tvNomeRestaurante);
        rvCardapio = findViewById(R.id.rvCardapio);
        ivIconeRestaurante = findViewById(R.id.ivIconeRestaurante);
        cvDetalheItem = findViewById(R.id.cvDetalheItem);
        flDetalheItem = findViewById(R.id.flDetalheItem);

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
    }



    private void carregaDados() {
        tvNomeRestaurante.setText(restaurante.getNome());
        if(restaurante.getImagemBaixada() != null) {
            byte[] decodedString = Base64.decode(restaurante.getImagemBaixada(), Base64.DEFAULT);
            Bitmap imagem = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivIconeRestaurante.setImageBitmap(imagem);
        }
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
    public void getImagemItem(String imagemBase64, String uuid) {
        itensCardapio.stream().filter(item -> item.getUuid().equalsIgnoreCase(uuid)).findFirst().get().setImagemBaixada(imagemBase64);
        atualizaLista();
    }

    @Override
    public void listaItens(List<ItemCardapio> itens) {
        itensCardapio = itens;
        buscarCategorias();
    }

    @Override
    public void detalheItem(ItemCardapio item) {
        loadFragment(new DetalheItemFragment(this, item));
        slideUpDetalheItem.show();
    }

    @Override
    public void fecharDetalheItem() {
        slideUpDetalheItem.hide();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.flDetalheItem, fragment).commit();
    }
}
