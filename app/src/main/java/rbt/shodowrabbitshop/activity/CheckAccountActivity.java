package rbt.shodowrabbitshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.CheckAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.Contants;
import rbt.shodowrabbitshop.base.DefaultUtils;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.AccountEntity;
import rbt.shodowrabbitshop.entity.DeleteInterface;

public class CheckAccountActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener, DeleteInterface {
    private int page;//第几页
    private int count;//每页显示多少项
    private boolean isRefreshing;//正在滑动刷新，用来控制在刷新返回数据之前禁止再次发送请求
    private PullToRefreshListView listview;
    private List<AccountEntity> data;//账号总数据
    private CheckAdapter checkAdapter;//账号的适配器
    private View save;//生成本地数据文件
    private int server;//1代表当前服务器安卓，2代表ios
    //左右分页
    private TextView seg_left;
    private TextView seg_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_account);
        setTitle("查询账号");
        initData();
        titleShow();
        initView();
        refresh();
    }

    private void initData() {
        page = 0;
        count = 50;
    }

    private void initView() {
        save= LayoutInflater.from(this).inflate(R.layout.button_save,null);
        seg_left = (TextView) findViewById(R.id.seg_left);
        seg_right = (TextView) findViewById(R.id.seg_right);
        seg_left.setOnClickListener(this);
        seg_right.setOnClickListener(this);
        listview = (PullToRefreshListView) findViewById(R.id.account_data);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        data = new ArrayList<>();
        checkAdapter = new CheckAdapter(data, this,this,1);
        listview.setAdapter(checkAdapter);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //上滑重置
                refresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //下滑获取更多数据
                getMoreData();
            }
        });
        listview.setOnItemClickListener(this);
        setRightSearch();
        setLeft();
    }

    private void setRightSearch() {
        setRightFunctionView(save, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetFile="";
                for (AccountEntity a : data) {
                    targetFile=targetFile+a.toString()+"\n";
                    Log.e("toString", a.toString());
                }
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
                String time = dateFormat.format( now );
                DefaultUtils.makeRootDirectory(Contants.FILE_ROOT_DIR);
                String fileName = Contants.FILE_ROOT_DIR + Contants.ACCOUNT_DATA+time+".txt";
                DefaultUtils.writeFileData(fileName, targetFile, "UTF-8");
                UtilDialog.showNormalToast("保存成功！");
            }
        });
    }
    private void getMoreData() {
        if (!isRefreshing) {
            AVQuery<AVObject> query = new AVQuery<AVObject>("accounts");
            page++;
            query.skip(page * count);
            query.limit(500);
            if (server == 1) {
                query.whereContains("server", "安卓");
            } else if (server == 2){
                query.whereContains("server", "ios");
            }
            query.orderByDescending("createdAt");
            isRefreshing = true;
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        List<AccountEntity> tmpData = new ArrayList<AccountEntity>();//查询到的临时数据
                        for (int i = 0; i < list.size(); i++) {
                            AVObject avObject = list.get(i);
                            AccountEntity accountEntity = new AccountEntity();
                            accountEntity.setId(avObject.getObjectId());
                            accountEntity.setNum(avObject.getString("num"));
                            accountEntity.setShips(avObject.getList("ships"));
                            accountEntity.setPrice(avObject.getString("price"));
                            accountEntity.setTag(avObject.getString("tag"));
                            accountEntity.setServer(avObject.getString("server"));
                            tmpData.add(accountEntity);
                        }
                        if (tmpData.size() > 0) {
                            data.addAll(tmpData);
                            checkAdapter.notifyDataSetChanged();
                        } else {
                            UtilDialog.showNormalToast("没有更多数据");
                            page--;
                        }
                    } else {
                        UtilDialog.showNormalToast("获取失败！");
                    }
                }
            });
            isRefreshing = false;
            //滑动完成
            listview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listview.onRefreshComplete();
                }
            }, 500);
        }
    }

    private void refresh() {
        if (!isRefreshing) {
            page = 0;
            //查询 添加数据 通知更新
            AVQuery<AVObject> query = new AVQuery<AVObject>("accounts");
            query.skip(page * count);
            query.limit(500);
            if (server == 1) {
                query.whereContains("server", "安卓");
            } else if (server == 2){
                query.whereContains("server", "ios");
            }
            query.orderByDescending("createdAt");
            isRefreshing = true;
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        List<AccountEntity> tmpData = new ArrayList<AccountEntity>();//查询到的临时数据
                        for (int i = 0; i < list.size(); i++) {
                            AVObject avObject = list.get(i);
                            AccountEntity accountEntity = new AccountEntity();
                            accountEntity.setId(avObject.getObjectId());
                            accountEntity.setNum(avObject.getString("num"));
                            accountEntity.setShips(avObject.getList("ships"));
                            accountEntity.setPrice(avObject.getString("price"));
                            accountEntity.setTag(avObject.getString("tag"));
                            accountEntity.setServer(avObject.getString("server"));
                            Log.e("ships", accountEntity.getShips().toString());
                            tmpData.add(accountEntity);
                        }
                        if (tmpData.size() > 0) {
                            data.clear();
                            data.addAll(tmpData);
                            checkAdapter.notifyDataSetChanged();
                        } else {
                            UtilDialog.showNormalToast("没有查询到数据");
                        }
                    } else {
                        UtilDialog.showNormalToast("获取失败！");
                    }
                }
            });
            isRefreshing = false;
            //滑动完成
            listview.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listview.onRefreshComplete();
                }
            }, 500);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }
    private void setLeft() {
        seg_left.setSelected(true);
        seg_right.setSelected(false);
        seg_left.setTextSize(18);
        seg_right.setTextSize(16);
        server=1;
    }
    private void setRight() {
        seg_left.setSelected(false);
        seg_right.setSelected(true);
        seg_left.setTextSize(16);
        seg_right.setTextSize(18);
        server=2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seg_left:
                setLeft();
                queryByType();
                break;
            case R.id.seg_right:
                setRight();
                queryByType();
                break;
        }

    }
    public void queryByType(){
        AVQuery<AVObject> query2 = new AVQuery<>("accounts");
        if (server == 1) {
            query2.whereContains("server", "安卓");
        } else {
            query2.whereContains("server", "ios");
        }
        query2.orderByDescending("createdAt");
        query2.limit(500);
        query2.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    data.clear();
                    for (int i = 0; i < list.size(); i++) {
                        AVObject avObject = list.get(i);
                        AccountEntity accountEntity = new AccountEntity();
                        accountEntity.setId(avObject.getObjectId());
                        accountEntity.setNum(avObject.getString("num"));
                        accountEntity.setShips(avObject.getList("ships"));
                        accountEntity.setPrice(avObject.getString("price"));
                        accountEntity.setTag(avObject.getString("tag"));
                        accountEntity.setServer(avObject.getString("server"));
                        Log.e("ships", accountEntity.getShips().toString());
                        data.add(accountEntity);
                        checkAdapter.notifyDataSetChanged();
                    }
                } else {
                    UtilDialog.showNormalToast("获取失败！");
                }
            }
        });
    }

    @Override
    public void onDelete() {
        refresh();
    }
}
