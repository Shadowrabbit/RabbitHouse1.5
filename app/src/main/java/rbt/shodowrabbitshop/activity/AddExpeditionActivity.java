package rbt.shodowrabbitshop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import java.util.Calendar;
import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.ExpeditionEntity;

public class AddExpeditionActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText ETname;//买家ID
    private EditText ETaccount;//账号
    private TextView TVtargetTime;//到期时间
    private Spinner SPserver;//服务器
    private EditText ETtag;//备注
    private Button BTsave;
    private String spinnerValue;
    private boolean key;//网络请求中 true为允许请求
    int mYear, mMonth, mDay;//时间
    final int DATE_DIALOG = 1;
    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            TVtargetTime.setText(getDate());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expedition);
        setTitle("添加远征");
        titleShow();
        initView();
        initData();
    }

    private void initData() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        key=true;
    }

    private void initView() {
        ETname= (EditText) findViewById(R.id.et_name);
        ETaccount= (EditText) findViewById(R.id.et_account);
        TVtargetTime = (TextView) findViewById(R.id.tv_targetTime);
        SPserver= (Spinner) findViewById(R.id.sp_server);
        ETtag= (EditText) findViewById(R.id.et_tag);
        BTsave= (Button) findViewById(R.id.submit);
        TVtargetTime.setOnClickListener(this);
        SPserver.setOnItemSelectedListener(this);
        BTsave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                if (key){
                    key=false;
                    ExpeditionEntity expeditionEntity=getExpeditionEntity();
                    AVObject expeditions = new AVObject("expeditions");
                    expeditions.put("name", expeditionEntity.getName());
                    expeditions.put("account", expeditionEntity.getAccount());
                    expeditions.put("targetTime", expeditionEntity.getTargetTime());
                    expeditions.put("server", expeditionEntity.getServer());
                    expeditions.put("tag", expeditionEntity.getTag());
                    expeditions.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            key=true;
                            if (e == null) {
                                UtilDialog.showNormalToast("提交成功！");
                                ExpeditionActivity.refresh();
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
            case R.id.tv_targetTime:
                showDialog(DATE_DIALOG);
                break;
        }
    }

    public ExpeditionEntity getExpeditionEntity() {
        ExpeditionEntity expeditionEntity=new ExpeditionEntity();
        expeditionEntity.setName(ETname.getText().toString());
        expeditionEntity.setAccount(ETaccount.getText().toString());
        expeditionEntity.setTargetTime(getDate());
        expeditionEntity.setServer(spinnerValue);
        expeditionEntity.setTag(ETtag.getText().toString());
        return expeditionEntity;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }
    public String getDate(){
        return String.valueOf(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = (String) SPserver.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
