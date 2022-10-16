package com.jnu.booklistmainapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    RecyclerView recyclerView;
    MyAdapater myAdapater;
    List<Book> books = new ArrayList<>();
    ActivityResultLauncher<Intent>addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
    ,result ->{
        if(null!=result){
            Intent intent=result.getData();
            if(result.getResultCode()!=InputBookActivity.RESULT_CODE_SUCCESS){
                Bundle bundle=intent.getExtras();
                String title=bundle.getString("title");
               // double price=bundle.getDouble("price");
                books.add(1,new Book(title,R.drawable.ic_launcher_background));
                myAdapater.notifyItemInserted(1);
            }
        }
            } );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle_view_books);

        getListBooks();

        myAdapater = new MyAdapater();
        recyclerView.setAdapter(myAdapater);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {//10.16
        switch(item.getItemId()){
            case MENU_ID_ADD:
                Intent intent=new Intent(this,InputBookActivity.class);
                addDataLauncher.launch(intent);//启动以后，返回，显示消息
                //startActivity(intent);//启动另外一个界面
                //Toast.makeText(this,"item add"+item.getOrder()+"clicked",Toast.LENGTH_LONG).show();//获得点击的是那一条数据
                break;
            case MENU_ID_UPDATE://toast显示提示信息
                Toast.makeText(this,"item update"+item.getOrder()+"clicked",Toast.LENGTH_LONG).show();
                break;
            case MENU_ID_DELETE:

                AlertDialog alertDialog=new AlertDialog.Builder(this)//删除，要通知调节器去更新界面
                        .setTitle("Confirmation")
                                .setMessage("ARE YOU SURE TO DELETE THIS ITEM?")
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                myAdapater.notifyItemRemoved(item.getOrder());
                            }
                        }).create();
                alertDialog.show();

                break;
        }
        return super.onContextItemSelected(item);
    }

    class MyAdapater extends RecyclerView.Adapter<MyViewHolder> {//重写三个函数
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(BookListMainActivity.this, R.layout.item_layout, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Book book = books.get(position);
            holder.tv.setText(book.getTitle());
            holder.iv.setImageDrawable(getResources().getDrawable(book.getCoverResourceId()));
        }

        @Override
        public int getItemCount() {
            return books.size();
        }


    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tv;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text_view_book_title);
            iv = itemView.findViewById(R.id.image_view_book_cover);

            itemView.setOnCreateContextMenuListener(this);//10.16
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {//10.16
            contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add"+getAdapterPosition());//获得当前点击的项目的位置
            contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update"+getAdapterPosition());
            contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete"+getAdapterPosition());
        }
    }

    public void getListBooks() {
        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
    }
}
class Book{
    public String title;
    //public Double price;
    public int id;
    public Book(String s, int i) {
        this.title=s;
        //this.price=p;
        this.id=i;
    }



    public String getTitle(){
        return this.title;
    }
    public int getCoverResourceId(){
        return this.id;
    }
}