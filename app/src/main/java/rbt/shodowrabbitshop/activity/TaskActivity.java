package rbt.shodowrabbitshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.TaskAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.TaskEntity;

public class TaskActivity extends BaseActivity implements View.OnClickListener {
    public static PullToRefreshListView task;
    private Button addTask;
    public static List<TaskEntity> data;//订单数据
    public static TaskAdapter taskAdapter;//订单适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setTitle("我的订单");
        initView();
        initData();
        titleShow();
    }

    private void initData() {
        data=new ArrayList<>();
        taskAdapter=new TaskAdapter(data,this);
        refresh();
        task.setAdapter(taskAdapter);
    }

    private void initView() {
        task = (PullToRefreshListView) findViewById(R.id.task_lv);
        addTask = (Button) findViewById(R.id.add_task_bt);
        addTask.setOnClickListener(this);
        task.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        task.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                refresh();
            }
        });
    }

    public static void refresh() {
        AVQuery<AVObject> query = new AVQuery<AVObject>("tasks");
        query.limit(999);
        // 按时间，降序排列
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<TaskEntity> tmpData = new ArrayList<>();//查询到的临时数据
                    for (int i = 0; i < list.size(); i++) {
                        AVObject avObject = list.get(i);
                        TaskEntity taskEntity = new TaskEntity();
                        taskEntity.setId(avObject.getObjectId());
                        taskEntity.setTitle(avObject.getString("title"));
                        taskEntity.setUpdateTime(getDate(avObject.getUpdatedAt()));
                        taskEntity.setContent(avObject.getString("content"));
                        taskEntity.setCreateTime(getDate(avObject.getCreatedAt()));
                        tmpData.add(taskEntity);
                    }
                    if (tmpData.size() > 0) {
                    } else {
                        UtilDialog.showNormalToast("没有查询到数据");
                    }
                    data.clear();
                    data.addAll(tmpData);
                    taskAdapter.notifyDataSetChanged();
                    task.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            task.onRefreshComplete();
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
        switch (v.getId()){
            case R.id.add_task_bt:
                startActivity(new Intent(this,AddTaskActivity.class));
                break;
        }
    }
    static String getDate(Date date){
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }
}
