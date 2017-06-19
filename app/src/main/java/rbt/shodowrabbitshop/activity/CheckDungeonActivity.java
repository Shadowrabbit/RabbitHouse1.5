package rbt.shodowrabbitshop.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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
import rbt.shodowrabbitshop.entity.DungeonEntity;

public class CheckDungeonActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private View update;
    private EditText ETname;//买家ID
    private EditText ETaccount;//账号
    private TextView TVtargetTime;//期限
    private Spinner SPserver;//服务器
    private Spinner SPdungeonType;//战役类型
    private Spinner SPdifficulty;//难度
    private String spinnerValue;//服务器值
    private String spinnerValue2;//战役类型值
    private String spinnerValue3;//战役难度值
    private EditText ETtag;//备注
    int mYear, mMonth, mDay;//时间
    final int DATE_DIALOG = 1;
    private Button Btsumit;
    private DungeonEntity dungeonEntity;//上个页面传来的数据
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
        setContentView(R.layout.activity_check_dungeon);
        setTitle("查看战役");
        titleShow();
        initView();
        initData();
    }
    private void initData() {
        dungeonEntity = (DungeonEntity) getIntent().getExtras().get("data");
        ETname.setText(dungeonEntity.getName());
        ETaccount.setText(dungeonEntity.getAccount());
        TVtargetTime.setText(dungeonEntity.getTargetTime());
        String targetTime=dungeonEntity.getTargetTime();
        String a[]=targetTime.split("-");
        mYear= Integer.parseInt(a[0]);
        mMonth=Integer.parseInt(a[1])-1;
        mDay=Integer.parseInt(a[2]);
        ETtag.setText(dungeonEntity.getTag());
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
        setSpinnerItemSelectedByValue(SPserver, dungeonEntity.getServer());
        setSpinnerItemSelectedByValue(SPdungeonType, dungeonEntity.getDungeonType());
        setSpinnerItemSelectedByValue(SPdifficulty, dungeonEntity.getDifficulty());
    }
    private void initView() {
        ETname= (EditText) findViewById(R.id.et_name);
        ETaccount= (EditText) findViewById(R.id.et_account);
        TVtargetTime= (TextView) findViewById(R.id.tv_targetTime);
        SPserver= (Spinner) findViewById(R.id.sp_server);
        SPdungeonType= (Spinner) findViewById(R.id.sp_dungeon_type);
        SPdifficulty= (Spinner) findViewById(R.id.sp_difficulty);
        ETtag= (EditText) findViewById(R.id.et_tag);
        Btsumit = (Button) findViewById(R.id.submit);
        update = LayoutInflater.from(this).inflate(R.layout.button_update, null);
        Btsumit.setOnClickListener(this);
        TVtargetTime.setOnClickListener(this);
        SPserver.setOnItemSelectedListener(this);
        SPdungeonType.setOnItemSelectedListener(this);
        SPdifficulty.setOnItemSelectedListener(this);
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
                setTitle("修改战役");
                titleShow();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                DungeonEntity dungeonEntityUpdate = getDungeonEntity();
                AVObject expeditions = AVObject.createWithoutData("dungeonDatas", dungeonEntity.getId());
                expeditions.put("name", dungeonEntityUpdate.getName());
                expeditions.put("account", dungeonEntityUpdate.getAccount());
                expeditions.put("targetTime", dungeonEntityUpdate.getTargetTime());
                expeditions.put("server", dungeonEntityUpdate.getServer());
                expeditions.put("dungeonType", dungeonEntityUpdate.getDungeonType());
                expeditions.put("difficulty", dungeonEntityUpdate.getDifficulty());
                expeditions.put("tag", dungeonEntityUpdate.getTag());
                expeditions.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            UtilDialog.showNormalToast("修改成功!");
                            DungeonActivity.refresh();
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
    public DungeonEntity getDungeonEntity() {
        DungeonEntity dungeonEntity=new DungeonEntity();
        dungeonEntity.setName(ETname.getText().toString());
        dungeonEntity.setAccount(ETaccount.getText().toString());
        dungeonEntity.setTargetTime(getDate());
        dungeonEntity.setServer(spinnerValue);
        dungeonEntity.setDungeonType(spinnerValue2);
        dungeonEntity.setDifficulty(spinnerValue3);
        dungeonEntity.setTag(ETtag.getText().toString());
        return dungeonEntity;
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
                spinnerValue2 = (String) SPdungeonType.getSelectedItem();
                spinnerValue3 = (String) SPdifficulty.getSelectedItem();
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
