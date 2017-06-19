package rbt.shodowrabbitshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.MainBannerAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private RelativeLayout RLBanner;
    private ViewPager VPBanner;
    private int currentItem = 0;//当前轮播页
    private ScheduledExecutorService scheduledExecutorService;//定时任务
    public boolean isAutoPlay = true;//自动轮播启用开关
    private boolean key;//实名认证请求开关
    private MainBannerAdapter mainBannerAdapter;
    private LinearLayout mLL_Point;
    private List BannerImgs;
    private ArrayList<ImageView> mBannerViews;
    private ImageView[] tips;
    private LinearLayout LLaddAccount;//添加账号
    private LinearLayout LLcheckAccount;//查看账号
    private LinearLayout LLupdateAccount;//修改账号
    private LinearLayout LLtask;//我的订单
    private LinearLayout LLexpeditionManage;//远征管理
    private LinearLayout LLdungeonManage;//战役管理
    private View search;//搜索
    private final int currentVersion=4;//当前版本号
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImgs();
        initViews();
        checkVersion();
    }

    private void checkVersion() {
        final AVQuery<AVObject> avQuery = new AVQuery<>("Version");
        avQuery.getInBackground("590675f8ac502e0063ef5643", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e==null){
                    String version=avObject.getString("version");
                    if (Integer.valueOf(version)>currentVersion){
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("更新");
                        alertDialog.setMessage("发现新版本是否更新？");
                        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this,DownloadActivity.class));
                            }
                        });
                        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });
    }

    private void initImgs() {
        BannerImgs=new ArrayList();
        int iv1=R.drawable.banner1;
        int iv2=R.drawable.banner2;
        int iv3=R.drawable.banner3;
        BannerImgs.add(iv1);
        BannerImgs.add(iv2);
        BannerImgs.add(iv3);
    }

    private void initViews() {
        RLBanner = (RelativeLayout)findViewById(R.id.banner_rl);
        LLaddAccount = (LinearLayout)findViewById(R.id.add_account_ll);
        LLcheckAccount = (LinearLayout)findViewById(R.id.check_account_ll);
        LLupdateAccount = (LinearLayout)findViewById(R.id.update_account_ll);
        LLtask= (LinearLayout) findViewById(R.id.task_ll);
        LLexpeditionManage= (LinearLayout) findViewById(R.id.expedition_manage_ll);
        LLdungeonManage= (LinearLayout) findViewById(R.id.dungeon_manage_ll);
        search= LayoutInflater.from(this).inflate(R.layout.button_search,null);
        //获取屏幕40%高作为展示区
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        height = height * 2 / 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        RLBanner.setLayoutParams(params);
        VPBanner = (ViewPager)findViewById(R.id.banner_vp);
        VPBanner.setOnPageChangeListener(this);
        LLaddAccount.setOnClickListener(this);
        LLcheckAccount.setOnClickListener(this);
        LLupdateAccount.setOnClickListener(this);
        LLtask.setOnClickListener(this);
        LLexpeditionManage.setOnClickListener(this);
        LLdungeonManage.setOnClickListener(this);
        setRightSearch();
        fillBanner();
        BackBtnHide();
    }

    private void setRightSearch() {
        setRightFunctionView(search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchAccountActivity.class));
            }
        });
    }

    private void fillBanner() {
        mBannerViews = new ArrayList<ImageView>();
        for (int i = 0; i < BannerImgs.size(); i++) {
            ImageView iv = new ImageView(this);//
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(this).load((Integer) BannerImgs.get(i)).into(iv);
            mBannerViews.add(iv);
        }
        mainBannerAdapter = new MainBannerAdapter(mBannerViews);
        VPBanner.setAdapter(mainBannerAdapter);
        mLL_Point = (LinearLayout)findViewById(R.id.view_pager_point_group);
        //将点点加入到ViewGroup中
        if (BannerImgs.size() > 0){
            tips = new ImageView[BannerImgs.size()];
        }
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.main_banner_circle_point_selected);
            } else {
                tips[i].setBackgroundResource(R.drawable.main_banner_circle_point_normal);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            mLL_Point.addView(imageView, layoutParams);
        }
        if (isAutoPlay) {
            startPlay();
        }
    }

    //Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if (isAutoPlay) {
                VPBanner.setCurrentItem(currentItem);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_account_ll:
                startActivity(new Intent(this,AddAccountActivity.class));
                break;
            case R.id.check_account_ll:
                startActivity(new Intent(this,CheckAccountActivity.class));
                break;
            case R.id.update_account_ll:
                startActivity(new Intent(this,UpdateAccountActivity.class));
                break;
            case R.id.task_ll:
                startActivity(new Intent(this,TaskActivity.class));
                break;
            case R.id.expedition_manage_ll:
                startActivity(new Intent(this,ExpeditionActivity.class));
                break;
            case R.id.dungeon_manage_ll:
                startActivity(new Intent(this,DungeonActivity.class));
                break;
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (VPBanner) {
                currentItem = (currentItem + 1) %BannerImgs.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 开始轮播图切换
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }

    //banner viewpager 页面替换监听事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % BannerImgs.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1:// 手势滑动，空闲中
                isAutoPlay = false;
                break;
            case 2:// 界面切换中
                isAutoPlay = true;
                break;
            case 0:// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                if (VPBanner.getCurrentItem() == VPBanner.getAdapter().getCount() - 1 && !isAutoPlay) {
                    VPBanner.setCurrentItem(0);
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (VPBanner.getCurrentItem() == 0 && !isAutoPlay) {
                    VPBanner.setCurrentItem(VPBanner.getAdapter().getCount() - 1);
                }
                break;
        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.main_banner_circle_point_selected);
            } else {
                tips[i].setBackgroundResource(R.drawable.main_banner_circle_point_normal);
            }
        }
    }
}
