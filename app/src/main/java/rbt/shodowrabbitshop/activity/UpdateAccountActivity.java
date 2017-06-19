package rbt.shodowrabbitshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.CheckAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.AccountEntity;
import rbt.shodowrabbitshop.entity.DeleteInterface;

public class UpdateAccountActivity extends BaseActivity implements AdapterView.OnItemClickListener, DeleteInterface {
    private int page;//第几页
    private int count;//每页显示多少项
    private boolean isRefreshing;//正在滑动刷新，用来控制在刷新返回数据之前禁止再次发送请求
    private PullToRefreshListView listview;
    private List<AccountEntity> data;//账号总数据
    private CheckAdapter checkAdapter;//账号的适配器
    final int REQUEST_UPDATE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        setTitle("修改账号");
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
        listview = (PullToRefreshListView) findViewById(R.id.account_data);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        data = new ArrayList<>();
        checkAdapter = new CheckAdapter(data, this,this,2);
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
    }

    private void getMoreData() {
        if (!isRefreshing) {
            AVQuery<AVObject> query = new AVQuery<AVObject>("accounts");
            page++;
            query.skip(page * count);
            query.limit(500);
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
        Intent it=new Intent(UpdateAccountActivity.this,UpdateAccountActivity2.class);
        it.putExtra("data",data.get(position-1));
        startActivityForResult(it,REQUEST_UPDATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_UPDATE){
            if (resultCode==0){
                refresh();
            }
        }
    }

    @Override
    public void onDelete() {

    }
}
