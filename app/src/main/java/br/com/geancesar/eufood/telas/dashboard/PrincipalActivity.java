package br.com.geancesar.eufood.telas.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.databinding.ActivityPrincipalBinding;
import br.com.geancesar.eufood.telas.cardapio.RestauranteActivity;
import br.com.geancesar.eufood.telas.dashboard.list_item.ListItemRestauranteAdapter;
import br.com.geancesar.eufood.telas.dashboard.listener.DashboardListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.requests.ListarRestaurantesTask;
import br.com.geancesar.eufood.telas.dashboard.requests.RespostaListarRestaurantes;
import br.com.geancesar.eufood.util.AccountManagerUtil;

public class PrincipalActivity extends Activity implements DashboardListener{
    private ActivityPrincipalBinding binding;

    RecyclerView rvRestaurantes;

    SwipeRefreshLayout srRestaurantes;

    List<Restaurante> restaurantes;

    List<Restaurante> restaurantesFiltrados;

    EditText etPesquisar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvRestaurantes = findViewById(R.id.rvRestaurantes);
        srRestaurantes = findViewById(R.id.srRestaurantes);
        srRestaurantes.setOnRefreshListener(this::getRestaurantes);

        etPesquisar = findViewById(R.id.etPesquisar);
        getRestaurantes();

        etPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(restaurantes != null) {
                    restaurantesFiltrados = restaurantes.stream().filter(restaurante -> restaurante.getNome().toUpperCase().contains(s.toString().toUpperCase())).toList();
                    atualizaLista();
                }
            }
        });
    }

    /**
     * Cria thread para buscar restaurantes
     */
    private void getRestaurantes() {
        ListarRestaurantesTask task = new ListarRestaurantesTask(this, AccountManagerUtil.getInstance().getToken(this));
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                RespostaListarRestaurantes resp = task.executa();
                handler.post(() -> task.posExecucao(resp));
            });
        }
    }

    private void atualizaLista(){
        if(rvRestaurantes.getAdapter() == null) {
            rvRestaurantes.setLayoutManager(new LinearLayoutManager(this));
            rvRestaurantes.setAdapter(new ListItemRestauranteAdapter(this, restaurantesFiltrados, this));
            if(srRestaurantes.isRefreshing()) {
                srRestaurantes.setRefreshing(false);
            }
        } else {
            ((ListItemRestauranteAdapter) rvRestaurantes.getAdapter()).setRestaurantes(restaurantesFiltrados);
            rvRestaurantes.getAdapter().notifyDataSetChanged();
            if(srRestaurantes.isRefreshing()) {
                srRestaurantes.setRefreshing(false);
            }
        }
    }

    @Override
    public void listaRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
        this.restaurantesFiltrados = new ArrayList<>();
        this.restaurantesFiltrados.addAll(restaurantes);
        atualizaLista();
    }

    @Override
    public void onItemClick(int adapterPosition) {
        Intent intent = new Intent(this, RestauranteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurante", restaurantes.get(adapterPosition));
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
