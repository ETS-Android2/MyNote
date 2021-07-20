package com.example.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class NoteDatabase {
    private final String tableName ="noteTable";
    public final String titleColumn ="titleColumn";
    public final String descriptionColumn ="descriptionColumn";
    private final String idColumn = "id";
    private final int databaseVersion = 1;
    private final String dataBaseName = "NoteDatabase";
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    Context context;
    NoteDatabase(Context context){
        this.context= context;
        }
    public void openConnection(){
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }
   class DatabaseHelper extends SQLiteOpenHelper{

       public DatabaseHelper(@Nullable Context context) {
           super(context, dataBaseName, null, databaseVersion);
       }

       @Override
       public void onCreate(SQLiteDatabase db) {
           String query = "CREATE TABLE "+tableName+"("+idColumn+" INTEGER PRIMARY KEY AUTOINCREMENT, "+titleColumn+
                   " TEXT NOT NULL,"+descriptionColumn+" TEXT NOT NULL);";
           db.execSQL(query);
       }

       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           db.execSQL("DROP TABLE IF EXISTS "+tableName);
           onCreate(db);
       }
   }
     public long doEntery(String text,String title){                                                 // for Entry Purpose
         ContentValues contentValues = new ContentValues();
         contentValues.put(titleColumn,title);
         contentValues.put(descriptionColumn,text);
        return sqLiteDatabase.insert(tableName,null,contentValues);

     }
     public void updateData(String text , String title, int id){                                     // for Update Data
         ContentValues contentValues = new ContentValues();
         contentValues.put(titleColumn,title);
         contentValues.put(descriptionColumn,text);
         sqLiteDatabase.update(tableName,contentValues,idColumn+"=?",new String[]{id+""});
     }
     public long deleteData(int id ){                                                                // for Delate Data;
       return  sqLiteDatabase.delete(tableName,idColumn+"=?",new String[]{id+""});
    }
    public ArrayList<Note> getData() {
          ArrayList<Note> noteList = new ArrayList<>();
          String [] columns = {titleColumn,idColumn,descriptionColumn};
          try {
        Cursor cursor = sqLiteDatabase.query(tableName,columns,null,null,null,null,null);
        int titleIndex = cursor.getColumnIndex(titleColumn);
        int descriptionIndex = cursor.getColumnIndex(descriptionColumn);
        int idIndex = cursor.getColumnIndex(idColumn);
         for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
        {
             Note note = new Note();
             note.setTitle(cursor.getString(titleIndex));
             note.setDescription(cursor.getString(descriptionIndex));
             note.setId(cursor.getInt(idIndex));
             noteList.add(note);
        }}catch (SQLException exception){
              Log.d("Sql Exception ", "getData: There Is Sql Exception");
          }
         return noteList;
    }
    public void closeConnection(){
        sqLiteDatabase.close();
    }
}
