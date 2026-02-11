package br.com.geancesar.eufood.telas.dashboard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.databinding.ActivityPrincipalBinding;
import br.com.geancesar.eufood.telas.dashboard.fragment.PedidosFragment;
import br.com.geancesar.eufood.telas.dashboard.fragment.RestaurantesFragment;

public class PrincipalActivity extends AppCompatActivity {

    ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nvMenu.setOnNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getTitle().equals("Início")) {
                fragmentRestaurantes();
            } else if (menuItem.getTitle().equals("Pedidos")) {
                fragmentPedidos();
            }

            return true;
        });

        fragmentRestaurantes();
    }

    private void fragmentRestaurantes(){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.flConteudoDashboard, new RestaurantesFragment()).commit();
    }


    private void fragmentPedidos(){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.flConteudoDashboard, new PedidosFragment()).commit();
    }


}
