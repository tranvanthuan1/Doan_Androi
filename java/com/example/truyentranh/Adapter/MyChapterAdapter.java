package com.example.truyentranh.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.truyentranh.Common.Common;
import com.example.truyentranh.Interface.IRecyclerOnClick;
import com.example.truyentranh.Model.Chapter;
import com.example.truyentranh.R;
import com.example.truyentranh.ViewDetail;

import java.util.List;
import java.util.concurrent.Callable;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {
    Context context;
    List<Chapter> chapterList;
    public  MyChapterAdapter(Context context, List<Chapter> chapterList){
        this.context = context;
        this.chapterList = chapterList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.chapter_item,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // thay đổi, cập nhật lại tên chapter tương ứng
        myViewHolder.txt_chapter_number.setText(new StringBuilder(chapterList.get(i).Name));
        // gán chapter hiện tại cho chapter đang được chọn
        Common.selected_chapter = chapterList.get(i);
        Common.chapter_index = i;

        // xử lí khi người dùng click vào một chapter cụ thể
        myViewHolder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_chapter = chapterList.get(position);
                Common.chapter_index = position;
                // chuyển sang một Activity mới - ViewDetail
                context.startActivity(new Intent(context,ViewDetail.class));

            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_chapter_number;
       IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chapter_number = (TextView)itemView.findViewById(R.id.txt_chapter_number);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iRecyclerOnClick.onClick(view, getAdapterPosition());


        }
    }
}
