package usuario.app.cadas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button botaoNovoCalculo;
    Button botaoPesquisarCalculo;
    Button botaoListarCalculos;
    Button botaoSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        botaoNovoCalculo = (Button) findViewById(R.id.bt_cadastrar_calculo);
        botaoPesquisarCalculo = (Button) findViewById(R.id.bt_consultar_calculo);
        botaoListarCalculos = (Button) findViewById(R.id.bt_listar_calculos);
        botaoSair = (Button) findViewById(R.id.bt_sair_menu);

        botaoSair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finishAffinity(); //Fecha a activity atual e todas as que estão abaixo na pilha (abertas anteriormente)
            }
        });
        botaoNovoCalculo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), CadastroCalculoActivity.class);
                startActivity(intent);
            }
        });
        botaoPesquisarCalculo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), PesquisaCadastroActivity.class);
                startActivity(intent);
            }
        });
    }

}