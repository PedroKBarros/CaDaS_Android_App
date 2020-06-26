package usuario.app.cadas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import usuario.app.cadas.database.DadosOpenHelper;
import usuario.app.cadas.dominio.repositorios.CalculoRepositorio;

public class MenuActivity extends AppCompatActivity {
    private SQLiteDatabase conexao; //Responsável por armazenar e representar a conexão com o banco de dados
    private DadosOpenHelper dadosOpenHelper; //Vamos criar uma instância dessa classe que criamos na pasta database para conseguirmos nos conectar com o BD
    private CalculoRepositorio calculoRepositorio;

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
        criarConexao();
        criarRepositorioConexao();
        botaoSair.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finishAffinity(); //Fecha a activity atual e todas as que estão abaixo na pilha (abertas anteriormente)
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
            Toast mensagemConfirmacao = Toast.makeText(this, "Conexão estabelecida com sucesso!", Toast.LENGTH_SHORT);
            mensagemConfirmacao.show();

        }catch (SQLException ex){ //Há dois SQLException e temos q usar o android.database, pois é o q pertence ao pacote do SQLite
            Toast mensagemErro = Toast.makeText(this, "Erro! Não foi possível se conectar com o banco de dados.", Toast.LENGTH_SHORT);
            mensagemErro.show();
            finish();
        }
    }
}