package rbt.shodowrabbitshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.TaskEntity;

public class AddTaskActivity extends BaseActivity implements View.OnClickListener {
    private EditText ETtitle;
    private EditText ETcontent;
    private Button BTsave;
    private boolean key;//网络请求中 true为允许请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        key=true;
        setTitle("添加订单");
        titleShow();
        initView();
    }

    private void initView() {
        ETtitle= (EditText) findViewById(R.id.et_title);
        ETcontent= (EditText) findViewById(R.id.et_content);
        BTsave= (Button) findViewById(R.id.bt_save);
        BTsave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save:
                if (key){
                    key=false;
                    TaskEntity taskEntity=getTask();
                    AVObject tasks = new AVObject("tasks");
                    tasks.put("title", taskEntity.getTitle());
                    tasks.put("content", taskEntity.getContent());
                    tasks.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            key=true;
                            if (e == null) {
                                UtilDialog.showNormalToast("提交成功！");
                                TaskActivity.refresh();
                                finish();
                            } else {
                                UtilDialog.showNormalToast("提交失败！请稍后再试!");
                            }
                        }
                    });
                }else{
                    UtilDialog.showNormalToast("您的操作太快了！");
                }
                break;
        }
    }

    public TaskEntity getTask() {
        TaskEntity task=new TaskEntity();
        task.setTitle(ETtitle.getText().toString());
        task.setContent(ETcontent.getText().toString());
        return task;
    }
}
