package com.example.truyentranh;


import android.content.Intent;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truyentranh.Adapter.MyComicAdapter;
import com.example.truyentranh.Adapter.MySliderAdapter;
import com.example.truyentranh.Common.Common;
import com.example.truyentranh.Model.Banner;
import com.example.truyentranh.Model.Comic;
import com.example.androidcomicreader.Service.PicassoImageLoadingService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import ss.com.bannerslider.Slider;


public class MainActivity extends AppCompatActivity {

    public final static String ID_EXTRA = "comic ID";

    //View
    Slider slider;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<Comic> dsComic;
    RecyclerView recycler_comic;
    TextView txt_comic;
    List<Banner> bannerList = new ArrayList<>();
    List<Comic> comicList = new ArrayList<>();
    ImageView btn_filter_search;
    //tạo Cursor c  để duyêt lấy giá trị trong bảng
    Cursor c;

    MySliderAdapter adapter;// adapter được sử dụng cho banner
    MyComicAdapter adapter1;// adapter được sử dụng cho comic

    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // lấy lại id của  button sử dụng với mục đích tìm kiếm
        btn_filter_search = (ImageView) findViewById(R.id.btn_show_filter_search);
        btn_filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chuyển sang FilterSearchActivity khi người dùng click vào button trên
                startActivity(new Intent(MainActivity.this, FilterSearchActivity.class));

            }
        });

       // lấy dữ liệu từ bảng Comic
        SQLiteDataController db =  SQLiteDataController.getInstance(this);
        try {
            db.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // mở database để lấy thông tin
        db.openDataBase();
        c = db.queryComic();
        // lấy lại id của các comic
        recycler_comic = (RecyclerView) findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        // khởi tạo layout GridLayoutManager để hiển thị danh sách các truyện
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        // thay đổi layout thành gridLayoutManager vừa tạo
        recycler_comic.setLayoutManager(gridLayoutManager);
        txt_comic = (TextView) findViewById(R.id.txt_comic);
        // lấy lại id của banner
        slider = (Slider) findViewById(R.id.banner_slider);
        Slider.init(new PicassoImageLoadingService());
        // tạo các đối tượng banner để hiển thị trên banner của ứng dụng
        for (int i = 0; i < 1; i++) {
           Banner banner = new Banner(i, "http://sohanews.sohacdn.com/thumb_w/660/2018/photo1544515241907-1544515245018-crop-15445153387811714841569.jpg");
           Banner banner2 = new Banner(i, "https://kienthucnews.com/hinh-ve-truyen-co-tich/imager_7_4243_700.jpg");
            Banner banner3 = new Banner(i,"https://xuatkhaulaodong.com.vn/images/2018/01/22/1-top-10-truyen-manga-nhat-ban-hay-nhat-moi-thoi-dai-one-piece-dao-hai-tac.jpg");
            Banner banner4 = new Banner(i,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRuVW3vUMeHpUwhCVZgQIR03743hNgIn40AXpcxezA3_jTHGNZts8MwoVLccHt8_dmLjAM&usqp=CAU");
            bannerList.add(banner);// thêm vào danh sách banner
            // thêm vào danh sách banner
            bannerList.add(banner2);
            bannerList.add(banner3);
            bannerList.add(banner4);

        }
        adapter = new MySliderAdapter(bannerList);
        slider.setAdapter(adapter);

        // di chuyển Cursor c về vị trí đầu của  bảng  Comic
        c.moveToFirst();
        // duyệt tất cả dữ liệu có trong bảng Comic lần lượt tạo ra các đối tượng tương ứng
        while (!c.isAfterLast())
        {

            Comic comic = new Comic(c.getInt(0), c.getString(1), c.getString(2));
            comicList.add(comic);// thêm truyện vào
            c.moveToNext();
        }
        adapter1 = new MyComicAdapter(MainActivity.this, comicList);
        recycler_comic.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

    }
}
