package com.example.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteDatabase {
    public static   final String noteDataColumn ="NoteData";
    public static  final  String titleColumn = "Title";
    public static final String idColumn = "Id";
   private static final String tableName ="NoteTable";
    private Context ourContext;
    private final String databaseName ="NoteDatabase";
    private final static int databaseVersion = 1;
    DatabaseHelper dbhelper;
    ArrayList<Data>  data;
    SQLiteDatabase sqLiteDatabase;
    NoteDatabase(Context context){
        ourContext = context;
    }
    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(@Nullable Context context) {
            super(context, databaseName, null, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlQuery = "CREATE TABLE " + tableName + "(" + idColumn + " INTEGER PRIMARY KEY AUTOINCREMENT," + titleColumn + " TEXT NOT NULL," + noteDataColumn + " TEXT NOT NULL);";
            db.execSQL(sqlQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
            onCreate(db);
        }
    }
    public void openConnection(){
        dbhelper = new DatabaseHelper(ourContext);
        sqLiteDatabase = dbhelper.getWritableDatabase();
    }
    public long doentry(String title, String description){
        ContentValues contentValues = new ContentValues();
        contentValues.put(titleColumn,title);
        contentValues.put(noteDataColumn,description);
        return sqLiteDatabase.insert(tableName,null,contentValues);
    }
    public ArrayList<Data> getData() throws SQLException {
        data = new ArrayList();
        String [] columns = {idColumn,titleColumn,noteDataColumn};
       Cursor cursor = sqLiteDatabase.query(tableName,columns,null,null,null,null,null);
       int idIndex = cursor.getColumnIndex(idColumn);
       int titleIndex = cursor.getColumnIndex(titleColumn);
       int dIndex = cursor.getColumnIndex(noteDataColumn);
       for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            Data note = new Data();
            note.setId(cursor.getInt(idIndex));
            note.setNoteTitle(cursor.getString(titleIndex));
            note.setNoteData(cursor.getString(dIndex));
            data.add(note);
        }
       return  data;
    }
    public int delete(int id){
          return sqLiteDatabase.delete(tableName,idColumn+"=?",new String[]{id+""});
    }
    public int updateData(int id ,String title ,String description){
        ContentValues contentValues = new ContentValues();
        contentValues.put(titleColumn,title);
        contentValues.put(noteDataColumn,description);
        return sqLiteDatabase.update(tableName,contentValues,idColumn+"=?",new String[]{id+""});
    }
    public void closeConnection(){
        sqLiteDatabase.close();
    }

}
