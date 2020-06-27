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

import java.util.ArrayList;
import java.util.List;

import usuario.app.cadas.database.DadosOpenHelper;
import usuario.app.cadas.dominio.entidades.Calculo;
import usuario.app.cadas.dominio.repositorios.CalculoRepositorio;

public class ListaCalculosActivity extends AppCompatActivity {
    private SQLiteDatabase conexao; //Responsável por armazenar e representar a conexão com o banco de dados
    private DadosOpenHelper dadosOpenHelper; //Vamos criar uma instância dessa classe que criamos na pasta database para conseguirmos nos conectar com o BD
    private CalculoRepositorio calculoRepositorio;
    EditText edTxtLista;
    Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_calculos);
        criarConexao();
        criarRepositorioConexao();
        edTxtLista = (EditText) findViewById(R.id.ed_txt_lista);
        botaoVoltar = (Button) findViewById(R.id.bt_lista_voltar);
        listaCalculos();

        botaoVoltar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                retornaMenu();
            }
        });
    }
    private void retornaMenu(){
        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
        startActivity(intent);
    }

    private void listaCalculos(){
        List<Calculo> calculos;
        Calculo calculo;
        calculos = calculoRepositorio.buscarTodasTuplas();
        if (calculos.size() == 0) {
            edTxtLista.setText(R.string.activity_lista_calculos_nao_tem_calculos);
            return;
        }
        String dadosCalculos = "";
        for(int i = 0;i < calculos.size();i++){
            calculo = calculos.get(i);
            dadosCalculos = dadosCalculos + "-"+calculo.nome + "\n";
            dadosCalculos += "   " + calculo.expressao + "\n";
        }
        edTxtLista.setText(dadosCalculos);

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