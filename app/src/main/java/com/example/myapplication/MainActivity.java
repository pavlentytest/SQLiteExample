package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2;
    EditText ed;
    RatingBar rt;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rt = findViewById(R.id.ratingBar);
        rt.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rt.setRating(v);
                Toast.makeText(MainActivity.this,"Rating: "+String.valueOf(v),Toast.LENGTH_LONG).show();
            }
        });
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("films.db",MODE_PRIVATE,null);
                db.execSQL("CREATE TABLE IF NOT EXISTS films (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, rate float)");

                String name = ed.getText().toString();
                float val = rt.getRating();
                db.execSQL("INSERT INTO films (name, rate) VALUES ('"+name+"',"+val+")");

                Cursor query = db.rawQuery("SELECT * FROM films",null);
                tv2 = findViewById(R.id.textView2);
                if(query.moveToNext()) {
                    do {
                        String f = query.getString(1);
                        float  v = query.getFloat(2);
                        tv2.append("Film:"+f+", rating:"+v+"\n");
                    } while (query.moveToNext());
                }
                query.close();
                db.close();
            }
        });

    }
}
