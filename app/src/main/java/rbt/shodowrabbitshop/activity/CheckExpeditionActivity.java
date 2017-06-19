package rbt.shodowrabbitshop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.Calendar;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.ExpeditionEntity;

public class CheckExpeditionActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View update;
    private EditText ETname;//买家ID
    private EditText ETaccount;//账号
    private TextView TVtargetTime;//期限
    private Spinner SPserver;//服务器
    private String spinnerValue;
    private EditText ETtag;//备注
    int mYear, mMonth, mDay;//时间
    final int DATE_DIALOG = 1;
    private Button Btsumit;
    private ExpeditionEntity expeditionEntity;//上个页面传来的数据
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
        setContentView(R.layout.activity_check_expedition);
        setTitle("查看远征");
        titleShow();
        initView();
        initData();
    }

    private void initData() {
        expeditionEntity = (ExpeditionEntity) getIntent().getExtras().get("data");
        final Calendar ca = Calendar.getInstance();
        String targetTime=expeditionEntity.getTargetTime();
        String a[]=targetTime.split("-");
        mYear= Integer.parseInt(a[0]);
        mMonth=Integer.parseInt(a[1])-1;
        mDay=Integer.parseInt(a[2]);
        ETname.setText(expeditionEntity.getName());
        ETaccount.setText(expeditionEntity.getAccount());
        TVtargetTime.setText(expeditionEntity.getTargetTime());
        ETtag.setText(expeditionEntity.getTag());
        Btsumit.setClickable(false);
        ETname.setCursorVisible(false);
        ETaccount.setCursorVisible(false);
        TVtargetTime.setCursorVisible(false);
        ETtag.setCursorVisible(false);
        ETname.setEnabled(false);
        ETaccount.setEnabled(false);
        TVtargetTime.setEnabled(false);
        TVtargetTime.setClickable(false);
        ETtag.setEnabled(false);
        setSpinnerItemSelectedByValue(SPserver, expeditionEntity.getServer());
    }

    private void initView() {
        ETname= (EditText) findViewById(R.id.et_name);
        ETaccount= (EditText) findViewById(R.id.et_account);
        TVtargetTime= (TextView) findViewById(R.id.tv_targetTime);
        SPserver= (Spinner) findViewById(R.id.sp_server);
        ETtag= (EditText) findViewById(R.id.et_tag);
        Btsumit = (Button) findViewById(R.id.submit);
        update = LayoutInflater.from(this).inflate(R.layout.button_update, null);
        Btsumit.setOnClickListener(this);
        SPserver.setOnItemSelectedListener(this);
        TVtargetTime.setOnClickListener(this);
        setRightUpdate();
    }

    private void setRightUpdate() {
        setRightFunctionView(update, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.INVISIBLE);
                ETname.setCursorVisible(true);
                ETtag.setCursorVisible(true);
                ETaccount.setCursorVisible(true);
                Btsumit.setVisibility(View.VISIBLE);
                Btsumit.setClickable(true);
                ETname.setEnabled(true);
                ETaccount.setEnabled(true);
                TVtargetTime.setEnabled(true);
                ETtag.setEnabled(true);
                TVtargetTime.setClickable(true);
                setTitle("修改远征");
                titleShow();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                ExpeditionEntity expeditionEntityUpdate = getExpeditionEntity();
                AVObject expeditions = AVObject.createWithoutData("expeditions", expeditionEntity.getId());
                expeditions.put("name", expeditionEntityUpdate.getName());
                expeditions.put("account", expeditionEntityUpdate.getAccount());
                expeditions.put("targetTime", expeditionEntityUpdate.getTargetTime());
                expeditions.put("server", expeditionEntityUpdate.getServer());
                expeditions.put("tag", expeditionEntityUpdate.getTag());
                expeditions.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            UtilDialog.showNormalToast("修改成功!");
                            ExpeditionActivity.refresh();
                            finish();
                        } else {
                            UtilDialog.showNormalToast("修改失败!");
                        }
                    }
                });
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
    public void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerValue = (String) SPserver.getSelectedItem();
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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
