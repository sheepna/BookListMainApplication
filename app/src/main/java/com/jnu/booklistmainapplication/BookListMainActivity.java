package com.jnu.booklistmainapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.booklistmainapplication.Data.Book;
import com.jnu.booklistmainapplication.Data.DataSaver;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    private ArrayList<Book>books;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_DELETE = 2;

    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS)
                    {//创建一个包含所有 extra 数据的 Bundle 对象，然后使用 putExtras() 将 Bundle 插入 Intent 中。
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        int position=bundle.getInt("position");
                        books.add(position, new Book(title,R.drawable.book_no_name) );
                        new DataSaver().Save(BookListMainActivity.this,books);
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        books=new ArrayList<>();

        DataSaver dataSaver=new DataSaver();
        books=dataSaver.Load(BookListMainActivity.this);
        getListBooks();

        mainRecycleViewAdapter= new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    //在Activity中重载onContextItemSelected
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_ADD://Intent(Context, Class) 构造函数分别为应用和组件提供 Context 和 Class 对象。因此，此 Intent 将显式启动该应用中的EditBookActivity类。
                Intent intent=new Intent(this ,EditBookActivity.class) ;
                intent.putExtra("position",item.getOrder());//添加 extra 数据，每种方法均接受两个参数：键名和值
                addDataLauncher.launch(intent);
                //books.add(item.getOrder(),new Book("added"+item.getOrder(),R.drawable.ic_launcher_background));
               // mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                new DataSaver().Save(BookListMainActivity.this,books);
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder>{

        private ArrayList<Book>localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

            private final TextView textViewTitle;
            private final ImageView imageViewImage;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textViewTitle=itemView.findViewById(R.id.text_view_book_title);
                imageViewImage=itemView.findViewById(R.id.image_view_book_cover);

                itemView.setOnCreateContextMenuListener(this);//在ViewHolder实现onContextMenuListener接口
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete "+getAdapterPosition());
            }
        }

        public MainRecycleViewAdapter (ArrayList<Book>dataSet){
            localDataSet=dataSet;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_layout,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            holder.getImageViewImage().setImageResource(localDataSet.get(position).getCoverResourceId());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
    public void getListBooks() {
        books.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        books.add(new Book("创新工程实践", R.drawable.book_no_name));
        books.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));
    }
}
