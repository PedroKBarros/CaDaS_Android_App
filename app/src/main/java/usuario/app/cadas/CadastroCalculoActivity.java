package usuario.app.cadas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import usuario.app.cadas.database.DadosOpenHelper;
import usuario.app.cadas.dominio.entidades.Calculo;
import usuario.app.cadas.dominio.repositorios.CalculoRepositorio;

public class CadastroCalculoActivity extends AppCompatActivity {
    private SQLiteDatabase conexao; //Responsável por armazenar e representar a conexão com o banco de dados
    private DadosOpenHelper dadosOpenHelper; //Vamos criar uma instância dessa classe que criamos na pasta database para conseguirmos nos conectar com o BD
    private CalculoRepositorio calculoRepositorio;
    Button botaoVoltar;
    Button botaoConfirmar;
    EditText edTxtNome;
    EditText edTxtExpressao;
    EditText edTxtData;
    EditText edTxtDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_calculo);
        botaoVoltar = (Button) findViewById(R.id.bt_voltar);
        botaoConfirmar = (Button) findViewById(R.id.bt_confirmar);
        edTxtNome = (EditText) findViewById(R.id.ed_txt_nome);
        edTxtExpressao = (EditText) findViewById(R.id.ed_txt_expressao);
        edTxtData = (EditText) findViewById(R.id.ed_txt_data);
        edTxtDescricao = (EditText) findViewById(R.id.ed_txt_descricao);
        criarConexao();
        criarRepositorioConexao();

        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                retornaMenu();
            }
        });
        botaoConfirmar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!dadosValidos()){
                    Toast mensagemErro = Toast.makeText(getApplicationContext(), R.string.activity_cadastro_dados_invalidos, Toast.LENGTH_SHORT);
                    mensagemErro.show();
                    return;
                }
                if (existeCalculoMesmoNome()){
                    Toast mensagemErro = Toast.makeText(getApplicationContext(), R.string.activity_cadastro_calculo_mesmo_nome, Toast.LENGTH_SHORT);
                    mensagemErro.show();
                    return;
                }
                cadastraCalculo();
                Toast mensagemErro = Toast.makeText(getApplicationContext(), R.string.activity_cadastro_calculo_cadastrado_sucesso, Toast.LENGTH_SHORT);
                mensagemErro.show();
                retornaMenu();
            }
        });
    }
    private void retornaMenu(){
        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
        startActivity(intent);
    }
    private void cadastraCalculo(){
        Calculo calculo;
        calculo = new Calculo();
        calculo.nome = edTxtNome.getText().toString().toUpperCase();
        calculo.expressao = edTxtExpressao.getText().toString();
        calculo.data = edTxtData.getText().toString();
        calculo.descricao = edTxtDescricao.getText().toString();
        calculoRepositorio.inserirTupla(calculo);

    }
    private boolean existeCalculoMesmoNome(){
        return calculoRepositorio.buscarCalculo(edTxtNome.getText().toString().toUpperCase()) != null;
    }
    private boolean dadosValidos(){
        return edTxtNome.getText().toString().compareTo("") != 0 && edTxtExpressao.getText().toString().compareTo("") != 0 && edTxtData.getText().toString().compareTo("") != 0;
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
            finishAffinity(); //Fecha a activity atual e todas as que estão abaixo na pilha (abertas anteriormente);
        }
    }
}