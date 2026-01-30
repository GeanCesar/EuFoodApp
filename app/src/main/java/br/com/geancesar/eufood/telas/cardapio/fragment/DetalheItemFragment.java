package br.com.geancesar.eufood.telas.cardapio.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.math.BigDecimal;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.util.Util;
import br.com.geancesar.uicomponents.componentes.NumberSelector;
import br.com.geancesar.uicomponents.componentes.listeners.NumberSelectorListener;

public class DetalheItemFragment extends Fragment {

    ItemCardapio item;
    TextView tvNomeItem;
    TextView tvDescricaoItem;
    ImageView ivIcone;
    Button btAdicionar;
    NumberSelector nsQuantidade;

    public DetalheItemFragment(){}
    
    public DetalheItemFragment(ItemCardapio item){
        this.item = item;
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

        return view;
    }

    private void atualizaTextoBotao(){
        double valor = nsQuantidade.getValor() * item.getValor().doubleValue();
        btAdicionar.setText("Adicionar R$ " + Util.getInstance().formataMoeda(valor));
    }

}
