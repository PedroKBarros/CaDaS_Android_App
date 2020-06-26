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

import usuario.app.cadas.database.DadosOpenHelper;
import usuario.app.cadas.dominio.entidades.Calculo;
import usuario.app.cadas.dominio.repositorios.CalculoRepositorio;

public class PesquisaCadastroActivity extends AppCompatActivity {
    private SQLiteDatabase conexao; //Responsável por armazenar e representar a conexão com o banco de dados
    private DadosOpenHelper dadosOpenHelper; //Vamos criar uma instância dessa classe que criamos na pasta database para conseguirmos nos conectar com o BD
    private CalculoRepositorio calculoRepositorio;
    Button botaoVoltar;
    Button botaoPesquisar;
    EditText edTxtNome;
    EditText edTxtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_cadastro);
        criarConexao();
        criarRepositorioConexao();
        botaoVoltar = (Button) findViewById(R.id.bt_pesquisa_voltar);
        botaoPesquisar = (Button) findViewById(R.id.bt_pesquisa_pesquisar);
        edTxtNome = (EditText) findViewById(R.id.ed_txt_pesquisa_nome);
        edTxtResultado = (EditText) findViewById(R.id.ed_txt_resultado);

        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                retornaMenu();
            }
        });
        botaoPesquisar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!dadosValidos()){
                    Toast mensagemErro = Toast.makeText(getApplicationContext(), R.string.activity_pesquisa_cadastro_dados_invalidos, Toast.LENGTH_SHORT);
                    mensagemErro.show();
                    return;
                }
                pesquisaCadastro();
            }
        });
    }
    private void pesquisaCadastro(){
        Calculo calculo;

        calculo = calculoRepositorio.buscarCalculo(edTxtNome.getText().toString().toUpperCase());
        if (calculo == null){
            edTxtResultado.setText(R.string.activity_pesquisa_cadastro_sem_resultado);
            return;
        }
        apresentaResultado(calculo);
    }
    private void apresentaResultado(Calculo calculo){
        String resultado;
        resultado = "Expressão: " + calculo.expressao + "\n";
        resultado += "Data: " + calculo.data + "\n";
        if (calculo.descricao.compareTo("") != 0)
            resultado += "Descrição: " + calculo.descricao;
        else
            resultado += "Descrição: -";

        edTxtResultado.setText(resultado);
    }
    private boolean dadosValidos(){
        return edTxtNome.getText().toString().compareTo("") != 0;
    }
    private void retornaMenu(){
        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
        startActivity(intent);
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