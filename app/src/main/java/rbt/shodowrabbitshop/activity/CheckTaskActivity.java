package rbt.shodowrabbitshop.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
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

public class CheckTaskActivity extends BaseActivity implements View.OnClickListener {
    private View update;
    private EditText ETtitle;
    private EditText ETcontent;
    private Button Btsumit;
    private TaskEntity taskEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_task);
        setTitle("查看订单");
        titleShow();
        initView();
        initData();
    }

    private void initData() {
        taskEntity= (TaskEntity) getIntent().getExtras().get("data");
        ETtitle.setText(taskEntity.getTitle().toString());
        ETcontent.setText(taskEntity.getContent().toString());
        Btsumit.setClickable(false);
        ETtitle.setCursorVisible(false);
        ETcontent.setCursorVisible(false);
        ETtitle.setEnabled(false);
        ETcontent.setEnabled(false);
    }

    private void initView() {
        ETtitle= (EditText) findViewById(R.id.et_title);
        ETcontent= (EditText) findViewById(R.id.et_content);
        Btsumit= (Button) findViewById(R.id.bt_sumit);
        update= LayoutInflater.from(this).inflate(R.layout.button_update,null);
        Btsumit.setOnClickListener(this);
        setRightUpdate();
    }
    private void setRightUpdate() {
        setRightFunctionView(update, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.INVISIBLE);
                ETcontent.setCursorVisible(true);
                ETtitle.setCursorVisible(true);
                Btsumit.setVisibility(View.VISIBLE);
                Btsumit.setClickable(true);
                ETtitle.setEnabled(true);
                ETcontent.setEnabled(true);
                setTitle("修改订单");
                titleShow();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sumit:
                TaskEntity taskEntityUpdate = getTaskEntity();
                AVObject tasks = AVObject.createWithoutData("tasks", taskEntity.getId());
                tasks.put("title", taskEntityUpdate.getTitle());
                tasks.put("content", taskEntityUpdate.getContent());
                tasks.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            UtilDialog.showNormalToast("修改成功!");
                            TaskActivity.refresh();
                            finish();
                        } else {
                            UtilDialog.showNormalToast("修改失败!");
                        }
                    }
                });
                break;
        }
    }

    public TaskEntity getTaskEntity() {
        TaskEntity taskEntity=new TaskEntity();
        taskEntity.setContent(ETcontent.getText().toString());
        taskEntity.setTitle(ETtitle.getText().toString());
        return taskEntity;
    }
}
