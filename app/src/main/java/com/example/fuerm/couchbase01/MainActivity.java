package com.example.fuerm.couchbase01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.ZipUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creamos un manager el objeto de mayor nivel en la jerarquía de couchbase, maneja las bases de datos

        Manager manager = null;
        Database database = null;
        try {
            // 1
            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
            // 2
            database = manager.getExistingDatabase("quizzdroid");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

// 3
        if (database == null) {
            try {
                ZipUtils.unzip(getApplicationContext().getAssets().open("quizzdroid.cblite2.zip"), manager.getContext().getFilesDir());
                // 4
                database = manager.getDatabase("quizzdroid");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }
    }
}
