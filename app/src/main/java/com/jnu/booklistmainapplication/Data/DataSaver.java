package com.jnu.booklistmainapplication.Data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataSaver {
    public void Save(Context context, ArrayList<Book>data){
        try {
            //为默认操作模式，代表该文件是私有数据，只能被应用本身访问,在该模式下，写入的内容会覆盖原文件的内容
            FileOutputStream dataStream= context.openFileOutput("mydata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out=new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
@NonNull
    public ArrayList<Book>Load(Context context){
        ArrayList<Book>data=new ArrayList<>();
        try {
            FileInputStream fileIn=context.openFileInput("mydata.dat");
            ObjectInputStream in=new ObjectInputStream(fileIn);
            data=(ArrayList<Book>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
