package com.gdou.yudong.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gdou.yudong.R;
import com.gdou.yudong.adapter.FragmentAdapter;
import com.gdou.yudong.ui.fragment.BookShelfFragment;
import com.gdou.yudong.ui.fragment.BookStoreFragment;
import com.gdou.yudong.ui.fragment.MyPageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BasicActivity{

    @BindView(R.id.iv_bookshelf)
    public ImageView iv_bookshelfe;
    @BindView(R.id.iv_bookstore)
    public ImageView iv_bookstore;
    @BindView(R.id.iv_mypage)
    public ImageView iv_mypage;
    @BindView(R.id.homeViewPager)
    public ViewPager homeViewPager;
    @BindView(R.id.rl_navi_bookshelf)
    public RelativeLayout rl_navi_bookshelf;
    @BindView(R.id.rl_navi_bookstore)
    public RelativeLayout rl_navi_bookstore;
    @BindView(R.id.rl_navi_mypage)
    public RelativeLayout rl_navi_mypage;

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

        initView();
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        homeViewPager.setOffscreenPageLimit(3);
        homeViewPager.setAdapter(fragmentAdapter);

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setItemSelected(position);
            }

            @Override
            public void onPageSelected(int position) {
                setItemSelected(position);
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

        bookShelfFragment = new BookShelfFragment();
        bookStoreFragment = new BookStoreFragment();
        myPageFragment = new MyPageFragment();
        myPageFragment.setActivity(this);

        fragmentList.add(bookShelfFragment);
        fragmentList.add(bookStoreFragment);
        fragmentList.add(myPageFragment);

    }

    @OnClick(R.id.rl_navi_bookshelf)
    public void bookshelfClick(){
        homeViewPager.setCurrentItem(0,true);
    }

    @OnClick(R.id.rl_navi_bookstore)
    public void bookstoreClick(){
        homeViewPager.setCurrentItem(1,true);
    }

    @OnClick(R.id.rl_navi_mypage)
    public void mypageClick(){
        homeViewPager.setCurrentItem(2,true);
    }

    /**
     * 设置导航栏图标选择状态
     * */
    private void setItemSelected(int position){
        switch (position){
            case 0:
                iv_bookshelfe.setImageResource(R.mipmap.shelf_select);
                iv_bookstore.setImageResource(R.mipmap.shop_normal);
                iv_mypage.setImageResource(R.mipmap.my_normal);
                break;
            case 1:
                iv_bookshelfe.setImageResource(R.mipmap.shelf_normal);
                iv_bookstore.setImageResource(R.mipmap.shop_select);
                iv_mypage.setImageResource(R.mipmap.my_normal);
                break;
            case 2:
                iv_bookshelfe.setImageResource(R.mipmap.shelf_normal);
                iv_bookstore.setImageResource(R.mipmap.shop_normal);
                iv_mypage.setImageResource(R.mipmap.my_select);
                break;
            default:
                break;
        }
    }


}
