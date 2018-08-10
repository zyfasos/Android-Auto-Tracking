package zyf.asos.tracking.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @author zyfasos
 */
public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private View lay1;
    private View lay2;
    private TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();
    }

    private void findView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        lay1 = findViewById(R.id.lay1);
        lay2 = findViewById(R.id.lay2);
        tv1 = findViewById(R.id.tv1);
    }

    private void setListener() {

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, btn1.getText().toString() + "被点击了!!", Toast.LENGTH_SHORT).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, btn2.getText().toString() + "被点击了!!", Toast.LENGTH_SHORT).show();
            }
        });

        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, lay1.toString() + "被点击了!!", Toast.LENGTH_SHORT).show();
            }
        });

        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, lay2.toString() + "被点击了!!", Toast.LENGTH_SHORT).show();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, tv1.getText().toString() + "被点击了!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
