package usuario.app.cadas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import usuario.app.cadas.database.DadosOpenHelper;
import usuario.app.cadas.dominio.repositorios.CalculoRepositorio;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase conexao; //Responsável por armazenar e representar a conexão com o banco de dados
    private DadosOpenHelper dadosOpenHelper; //Vamos criar uma instância dessa classe que criamos na pasta database para conseguirmos nos conectar com o BD
    private CalculoRepositorio calculoRepositorio;
    Button botaoProximo;
    Button botaoSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botaoProximo = (Button) findViewById(R.id.bt_proximo);
        botaoSair = (Button) findViewById(R.id.bt_sair_main);
        criarConexao();
        criarRepositorioConexao();
        if (calculoRepositorio.temTupla()) {
            Intent intent = new Intent(getBaseContext(), MenuActivity.class);
            startActivity(intent);
        }
        botaoSair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        botaoProximo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

    }

    private void criarRepositorioConexao(){
        calculoRepositorio = new CalculoRepositorio(conexao);
    }
    private void criarConexao(){
        //Método que vai criar a conexão com o BD em si. Esse método não cria nenhuma estrutura, apenas conecta com o BD
        try{
            dadosOpenHelper = new DadosOpenHelper(this); //Lembre-se que nosso contrutor pede o contexto, ou seja, a Activity em questão
            //Criando conexão
            conexao = dadosOpenHelper.getWritableDatabase(); //O writable permite leitura e escrita, enquanto q o redtable, só leitura

        }catch (SQLException ex){ //Há dois SQLException e temos q usar o android.database, pois é o q pertence ao pacote do SQLite
            Toast mensagemErro = Toast.makeText(this, R.string.erro_conexao_bd, Toast.LENGTH_SHORT);
            mensagemErro.show();
            finish();
        }
    }
}