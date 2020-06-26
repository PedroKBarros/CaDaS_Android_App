package usuario.app.cadas.database;

public class ScriptDDL {
    public static String getCreateTableCalculo(){
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS CALCULO ( ");
        sql.append("NOME VARCHAR(100) PRIMARY KEY NOT NULL, ");
        sql.append("EXPRESSAO VARCHAR(300) NOT NULL, ");
        sql.append("DATA CHAR(10) NOT NULL, ");
        sql.append("DESCRICAO VARCHAR(500) NOT NULL DEFAULT ('') )");

        return sql.toString();
    }
}