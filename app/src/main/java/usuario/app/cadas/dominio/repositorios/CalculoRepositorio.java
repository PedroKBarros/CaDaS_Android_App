package usuario.app.cadas.dominio.repositorios;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import usuario.app.cadas.dominio.entidades.Calculo;

//Essa classe serve para a manipulação dos dados de Calculo
public class CalculoRepositorio {
    private SQLiteDatabase conexao;

    public CalculoRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }
    public void inserirTupla(Calculo calculo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", calculo.nome);
        contentValues.put("EXPRESSAO", calculo.expressao);
        contentValues.put("DATA", calculo.data);
        contentValues.put("DESCRICAO", calculo.descricao);

        conexao.insertOrThrow("CALCULO", null, contentValues); //Esse método insere os dados no BD, podendo gerar uma exceção caso ocorra um erro, e se não ocorrer, retorna
        //o valor da chave primária, o q é bom, pois podemos usá-lo caso seja necessário
        //O primeiro parâmetro é o nome da tabela, o segundo é besteira e o terceiro são os valores da tupla.
    }
    public void excluirTupla(String nome){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(nome);

        conexao.delete("CALCULO", "NOME = ?", parametros);

    }

    public List<Calculo> buscarTodasTuplas(){
        List<Calculo> calculos = new ArrayList<Calculo>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT NOME, EXPRESSAO, DATA, DESCRICAO ");
        sql.append("FROM CALCULO");

        Cursor resultado = conexao.rawQuery(sql.toString(), null); //Método para escrever consulta SQL, passando a String da consulta e parâmetros da consulta em um vetor de Strings
        //Verificando se algum registro foi retornado:                         //O primeiro parâmetro é a quary e o outro são os argumentos. Porém, como não temos argumentos, pois não usamos a clausula where, coloquei como null.
        if (resultado.getCount() > 0){                                           //Ele retorna um objeto Cursor, que contém as tuplas de resultado. Vamos converter para List<Cliente>.
            //Garantindo q a gnt vai ter os resultados a
            // partir do  primeiro registro:
            resultado.moveToFirst();
            //Transformando o resultado em List<Cliente>:
            do{
                //Convertendo os valores da tabela em atributos da classe Calculo:
                Calculo calculo = new Calculo();
                calculo.nome = resultado.getString(resultado.getColumnIndexOrThrow("NOME"));
                calculo.expressao = resultado.getString(resultado.getColumnIndexOrThrow("EXPRESSAO"));
                calculo.data = resultado.getString(resultado.getColumnIndexOrThrow("DATA"));
                calculo.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

                calculos.add(calculo);
            }while(resultado.moveToNext());//Enquanto houver registro,
            // vamos mover o resultado para o próximo registro. Esse método retorna true, caso tenha conseguido mover.
        }


        return calculos;
    }
    public Calculo buscarCalculo(String nome){
        Calculo calculo = new Calculo();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT NOME, EXPRESSAO, DATA, DESCRICAO ");
        sql.append("FROM CALCULO ");
        sql.append("WHERE NOME = ?"); //Se fossem dois argumentos de condição, seria: WHERE CODIGO = ? AND TELEFONE = ?, por exemplo

        //Configurando os parâmetros do WHERE, que no caso é só um:
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(nome);
        //Executando consulta:
        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);
        //Verificando se há um registro de retorno da consulta:
        if (resultado.getCount() > 0){
            resultado.moveToFirst();
            //Convertendo para instância de classe Cliente:
            calculo.nome = resultado.getString(resultado.getColumnIndexOrThrow("NOME"));
            calculo.expressao = resultado.getString(resultado.getColumnIndexOrThrow("EXPRESSAO"));
            calculo.data = resultado.getString(resultado.getColumnIndexOrThrow("DATA"));
            calculo.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));
            return calculo;
        }

        return null;
    }
}
