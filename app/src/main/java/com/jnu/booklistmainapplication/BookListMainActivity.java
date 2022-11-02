package com.jnu.booklistmainapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.booklistmainapplication.Data.Book;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    private ArrayList<Book>books;
    private MainRecycleViewAdapter mainRecycleViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        books=new ArrayList<>();

        getListBooks();

        mainRecycleViewAdapter= new MainRecycleViewAdapter(books);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
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

                itemView.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

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
