package rbt.shodowrabbitshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.ExpeditionAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.DefaultUtils;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.ExpeditionEntity;

public class ExpeditionActivity extends BaseActivity implements View.OnClickListener {
    public static PullToRefreshListView expedition;
    private Button addAccount;
    public static List<ExpeditionEntity> data;//远征账号数据
    public static ExpeditionAdapter expeditionAdapter;//远征账号适配器
    private View sync;//右上角同步数据
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);
        setTitle("我的远征");
        initView();
        initData();
        titleShow();
    }

    private void initData() {
        data = new ArrayList<>();
        expeditionAdapter = new ExpeditionAdapter(data, this);
        refresh();
        expedition.setAdapter(expeditionAdapter);
    }

    private void initView() {
        expedition = (PullToRefreshListView) findViewById(R.id.expedition_lv);
        addAccount = (Button) findViewById(R.id.add_account_bt);
        addAccount.setOnClickListener(this);
        expedition.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expedition.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                refresh();
            }
        });
        sync = LayoutInflater.from(this).inflate(R.layout.button_expedition_sync, null);
        setRightFunctionView(sync, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ExpeditionActivity.this);
                View view = View.inflate(ExpeditionActivity.this, R.layout.item_dialog, null);
                TextView android = (TextView) view.findViewById(R.id.tv_android);
                TextView ios = (TextView) view.findViewById(R.id.tv_ios);
                android.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AVQuery<AVObject> query1 = new AVQuery<>("expeditions");
                        query1.whereEqualTo("server", "安卓");
                        query1.limit(999);
                        query1.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                if (e == null) {
                                    ArrayList<ExpeditionEntity> tmpData = new ArrayList<ExpeditionEntity>();//查询到的临时数据
                                    for (int i = 0; i < list.size(); i++) {
                                        AVObject avObject = list.get(i);
                                        ExpeditionEntity expeditionEntity = new ExpeditionEntity();
                                        expeditionEntity.setId(avObject.getObjectId());
                                        expeditionEntity.setName(avObject.getString("name"));
                                        expeditionEntity.setAccount(avObject.getString("account"));
                                        expeditionEntity.setTargetTime(avObject.getString("targetTime"));
                                        expeditionEntity.setServer(avObject.getString("server"));
                                        expeditionEntity.setTag(avObject.getString("tag"));
                                        tmpData.add(expeditionEntity);
                                    }
                                    if (tmpData.size() > 0) {
                                        String targetFile = "";
                                        for (ExpeditionEntity a : tmpData) {
                                            targetFile = targetFile + a.getAccount() + "\n";
                                            Log.e("toString", a.toString());
                                        }
                                        String fileName = "/storage/emulated/0/shadowrabbit.txt";
                                        String fileName2 = "/storage/emulated/0/lostrabbit.txt";
                                        DefaultUtils.writeFileData(fileName, targetFile, "UTF-8");
                                        DefaultUtils.writeFileData(fileName2, "0", "UTF-8");
                                        UtilDialog.showNormalToast("数据同步成功！");
                                    } else {
                                        UtilDialog.showNormalToast("没有数据");
                                    }
                                    alert.dismiss();
                                }
                            }
                        });
                    }
                });
                ios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AVQuery<AVObject> query1 = new AVQuery<>("expeditions");
                        query1.whereEqualTo("server", "IOS");
                        query1.limit(999);
                        query1.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                if (e == null) {
                                    ArrayList<ExpeditionEntity> tmpData = new ArrayList<ExpeditionEntity>();//查询到的临时数据
                                    for (int i = 0; i < list.size(); i++) {
                                        AVObject avObject = list.get(i);
                                        ExpeditionEntity expeditionEntity = new ExpeditionEntity();
                                        expeditionEntity.setId(avObject.getObjectId());
                                        expeditionEntity.setName(avObject.getString("name"));
                                        expeditionEntity.setAccount(avObject.getString("account"));
                                        expeditionEntity.setTargetTime(avObject.getString("targetTime"));
                                        expeditionEntity.setServer(avObject.getString("server"));
                                        expeditionEntity.setTag(avObject.getString("tag"));
                                        tmpData.add(expeditionEntity);
                                    }
                                    if (tmpData.size() > 0) {
                                        String targetFile = "";
                                        for (ExpeditionEntity a : tmpData) {
                                            targetFile = targetFile + a.getAccount() + "\n";
                                            Log.e("toString", a.toString());
                                        }
                                        String fileName = "/storage/emulated/0/shadowrabbit.txt";
                                        String fileName2 = "/storage/emulated/0/lostrabbit.txt";
                                        DefaultUtils.writeFileData(fileName, targetFile, "UTF-8");
                                        DefaultUtils.writeFileData(fileName2, "0", "UTF-8");
                                        UtilDialog.showNormalToast("数据同步成功！");
                                    } else {
                                        UtilDialog.showNormalToast("没有数据");
                                    }
                                    alert.dismiss();
                                }
                            }
                        });
                    }
                });
                alert = alertDialog.setTitle("同步").setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
                alert.show();
            }
        });
    }

    public static void refresh() {
        AVQuery<AVObject> query = new AVQuery<AVObject>("expeditions");
        query.limit(999);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<ExpeditionEntity> tmpData = new ArrayList<>();//查询到的临时数据
                    for (int i = 0; i < list.size(); i++) {
                        AVObject avObject = list.get(i);
                        ExpeditionEntity expeditionEntity = new ExpeditionEntity();
                        expeditionEntity.setId(avObject.getObjectId());
                        expeditionEntity.setName(avObject.getString("name"));
                        expeditionEntity.setAccount(avObject.getString("account"));
                        expeditionEntity.setTargetTime(avObject.getString("targetTime"));
                        expeditionEntity.setServer(avObject.getString("server"));
                        expeditionEntity.setTag(avObject.getString("tag"));
                        tmpData.add(expeditionEntity);
                    }
                    if (tmpData.size() > 0) {
                    } else {
                        UtilDialog.showNormalToast("没有查询到数据");
                    }
                    data.clear();
                    data.addAll(tmpData);
                    expeditionAdapter.notifyDataSetChanged();
                    expedition.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            expedition.onRefreshComplete();
                        }
                    }, 500);
                } else {
                    UtilDialog.showNormalToast("获取失败！");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_account_bt:
                startActivity(new Intent(this, AddExpeditionActivity.class));
                break;
        }
    }
}
