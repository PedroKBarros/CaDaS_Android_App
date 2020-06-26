package usuario.app.cadas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DadosOpenHelper extends SQLiteOpenHelper {
    //Construtor
    public DadosOpenHelper(@Nullable Context context) {
        super(context, "BD_DADOS_CALCULOS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDDL.getCreateTableCalculo());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Método para realizar qualquer atualização no BD, como incluir um campo numa tabela, por exemplo

    }
}
