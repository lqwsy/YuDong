package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.FragmentAdapter;
import com.gdou.yudong.ui.fragment.BookShelfFragment;
import com.gdou.yudong.ui.fragment.BookStoreFragment;
import com.gdou.yudong.ui.fragment.MyPageFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BasicActivity implements View.OnClickListener{

    @BindView(R.id.iv_bookshelf)
    public ImageView iv_bookshelfe;
    @BindView(R.id.iv_bookstore)
    public ImageView iv_bookstore;
    @BindView(R.id.iv_mypage)
    public ImageView iv_mypage;
    @BindView(R.id.homeViewPager)
    public ViewPager homeViewPager;

    private List<Fragment> fragmentList = new ArrayList<>();
    private BookShelfFragment bookShelfFragment;
    private BookStoreFragment bookStoreFragment;
    private MyPageFragment myPageFragment;
    private FragmentAdapter fragmentAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);//对绑定的成员赋值
//        clearLoginSharedPreferences();//清除登录数据

        File fileDir = new File(Environment.getExternalStorageDirectory()+"/yudong/");//自定义文件存放路径:/storage/emulated/0/yudong
        if(!fileDir.exists()){
            fileDir.mkdir();//创建新目录
        }

        Log.i("appPath : ",fileDir.toString());

        initView();
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        homeViewPager.setOffscreenPageLimit(3);
        homeViewPager.setAdapter(fragmentAdapter);

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化界面
     * */
    private void initView(){
        iv_bookshelfe.setOnClickListener(this);
        iv_bookstore.setOnClickListener(this);
        iv_mypage.setOnClickListener(this);

        bookShelfFragment = new BookShelfFragment();
        bookStoreFragment = new BookStoreFragment();
        myPageFragment = new MyPageFragment();

        fragmentList.add(bookShelfFragment);
        fragmentList.add(bookStoreFragment);
        fragmentList.add(myPageFragment);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_bookshelf:
                homeViewPager.setCurrentItem(0,true);
                break;
            case R.id.iv_bookstore:
                homeViewPager.setCurrentItem(1,true);
                break;
            case R.id.iv_mypage:
                homeViewPager.setCurrentItem(2,true);
                break;
            default:
                break;
        }
    }

    /**
     * 设置导航栏图标选择状态
     * */
    private void setItemSelected(int position){

    }


}
