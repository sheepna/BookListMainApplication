package com.jnu.booklistmainapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book);

        EditText editTextTitle=findViewById(R.id.edittext_book_item_title);
        //EditText editTextPrice=findViewById(R.id.edittext_book_item_price);
        Button buttonok=findViewById(R.id.button_ok);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editTextTitle.getText().toString());
                //double price=Double.parseDouble(editTextPrice.getText().toString());
                //bundle.putDouble("price",price);
                intent.putExtras(bundle);//intent中使用bundle打包大量数据，少量可以直接使用bundle.put~
                setResult(RESULT_CODE_SUCCESS,intent);//使用setResult返回
                InputBookActivity.this.finish();//关掉窗口
            }
        });
    }
}