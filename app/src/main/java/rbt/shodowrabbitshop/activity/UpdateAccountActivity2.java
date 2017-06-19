package rbt.shodowrabbitshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.AccountEntity;

public class UpdateAccountActivity2 extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    private CheckBox bb1;
    private CheckBox bb2;
    private CheckBox bb3;
    private CheckBox bb4;
    private CheckBox bb5;
    private CheckBox bb6;
    private CheckBox bb7;
    private CheckBox bb8;
    private CheckBox bb9;
    private CheckBox bb10;
    private CheckBox bb11;
    private CheckBox av1;
    private CheckBox cv1;
    private CheckBox cv2;
    private CheckBox cv3;
    private CheckBox cv4;
    private CheckBox cv5;
    private CheckBox cv6;
    private CheckBox ss1;
    private CheckBox ss2;
    private CheckBox cv7;
    private CheckBox cv8;
    private CheckBox cv9;
    private CheckBox cl1;
    private CheckBox bb12;
    private Button update;
    private EditText ETnum;
    private EditText ETlevel;
    private EditText ETresource;
    private EditText ETprice;
    private EditText ETtag;
    private Spinner SPserver;//服务器下拉列表
    private String spinnerValue;//spinner中选中的值
    private AccountEntity accountEntity;//上个页面传来的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account2);
        setTitle("修改账号");
        titleShow();
        initView();
        initData();
    }

    private void initData() {
        accountEntity = (AccountEntity) getIntent().getExtras().get("data");
        ETnum.setText(accountEntity.getNum());
        ETprice.setText(accountEntity.getPrice());
        ETtag.setText(accountEntity.getTag());
        setSpinnerItemSelectedByValue(SPserver, accountEntity.getServer());
        List<String> ships = accountEntity.getShips();
        for (String a : ships) {
            if (a.equals("密苏里")) {
                bb1.setChecked(true);
                continue;
            }
            if (a.equals("狮")) {
                bb2.setChecked(true);
                continue;
            }
            if (a.equals("俾斯麦")) {
                bb3.setChecked(true);
                continue;
            }
            if (a.equals("提尔比茨")) {
                bb4.setChecked(true);
                continue;
            }
            if (a.equals("华盛顿")) {
                bb5.setChecked(true);
                continue;
            }
            if (a.equals("南达科他")) {
                bb6.setChecked(true);
                continue;
            }
            if (a.equals("北卡罗莱纳")) {
                bb7.setChecked(true);
                continue;
            }
            if (a.equals("前卫")) {
                bb8.setChecked(true);
                continue;
            }
            if (a.equals("黎塞留")) {
                bb9.setChecked(true);
                continue;
            }
            if (a.equals("安德烈·亚多利亚")) {
                bb10.setChecked(true);
                continue;
            }
            if (a.equals("维内托")) {
                bb11.setChecked(true);
                continue;
            }
            if (a.equals("大凤")) {
                av1.setChecked(true);
                continue;
            }
            if (a.equals("企业")) {
                cv1.setChecked(true);
                continue;
            }
            if (a.equals("赤城")) {
                cv2.setChecked(true);
                continue;
            }
            if (a.equals("加贺")) {
                cv3.setChecked(true);
                continue;
            }
            if (a.equals("埃塞克斯")) {
                cv4.setChecked(true);
                continue;
            }
            if (a.equals("翔鹤")) {
                cv5.setChecked(true);
                continue;
            }
            if (a.equals("瑞鹤")) {
                cv6.setChecked(true);
                continue;
            }
            if (a.equals("大青花鱼")) {
                ss1.setChecked(true);
                continue;
            }
            if (a.equals("射水鱼")) {
                ss2.setChecked(true);
            }
            if (a.equals("皇家方舟")) {
                cv7.setChecked(true);
            }
            if (a.equals("大黄蜂")) {
                cv8.setChecked(true);
            }
            if (a.equals("约克城")) {
                cv9.setChecked(true);
            }
            if (a.equals("大淀")) {
                cl1.setChecked(true);
            }
            if (a.equals("约克公爵")) {
                bb12.setChecked(true);
            }
        }
    }

    private void initView() {
        bb1 = (CheckBox) findViewById(R.id.bb1);
        bb2 = (CheckBox) findViewById(R.id.bb2);
        bb3 = (CheckBox) findViewById(R.id.bb3);
        bb4 = (CheckBox) findViewById(R.id.bb4);
        bb5 = (CheckBox) findViewById(R.id.bb5);
        bb6 = (CheckBox) findViewById(R.id.bb6);
        bb7 = (CheckBox) findViewById(R.id.bb7);
        bb8 = (CheckBox) findViewById(R.id.bb8);
        bb9 = (CheckBox) findViewById(R.id.bb9);
        bb10 = (CheckBox) findViewById(R.id.bb10);
        bb11 = (CheckBox) findViewById(R.id.bb11);
        av1 = (CheckBox) findViewById(R.id.av1);
        cv1 = (CheckBox) findViewById(R.id.cv1);
        cv2 = (CheckBox) findViewById(R.id.cv2);
        cv3 = (CheckBox) findViewById(R.id.cv3);
        cv4 = (CheckBox) findViewById(R.id.cv4);
        cv5 = (CheckBox) findViewById(R.id.cv5);
        cv6 = (CheckBox) findViewById(R.id.cv6);
        ss1 = (CheckBox) findViewById(R.id.ss1);
        ss2 = (CheckBox) findViewById(R.id.ss2);
        cv7 = (CheckBox) findViewById(R.id.cv7);
        cv8 = (CheckBox) findViewById(R.id.cv8);
        cv9 = (CheckBox) findViewById(R.id.cv9);
        cl1 = (CheckBox) findViewById(R.id.cl1);
        bb12=(CheckBox) findViewById(R.id.bb12);
        update = (Button) findViewById(R.id.update);
        ETnum = (EditText) findViewById(R.id.et_num);
        ETlevel = (EditText) findViewById(R.id.et_level);
        ETresource = (EditText) findViewById(R.id.et_resource);
        ETprice = (EditText) findViewById(R.id.et_price);
        ETtag = (EditText) findViewById(R.id.et_tag);
        SPserver = (Spinner) findViewById(R.id.sp_server);
        bb1.setOnCheckedChangeListener(this);
        bb2.setOnCheckedChangeListener(this);
        bb3.setOnCheckedChangeListener(this);
        bb4.setOnCheckedChangeListener(this);
        bb5.setOnCheckedChangeListener(this);
        bb6.setOnCheckedChangeListener(this);
        bb7.setOnCheckedChangeListener(this);
        bb8.setOnCheckedChangeListener(this);
        bb9.setOnCheckedChangeListener(this);
        bb10.setOnCheckedChangeListener(this);
        bb11.setOnCheckedChangeListener(this);
        cv1.setOnCheckedChangeListener(this);
        cv2.setOnCheckedChangeListener(this);
        cv3.setOnCheckedChangeListener(this);
        cv4.setOnCheckedChangeListener(this);
        cv5.setOnCheckedChangeListener(this);
        cv6.setOnCheckedChangeListener(this);
        av1.setOnCheckedChangeListener(this);
        ss1.setOnCheckedChangeListener(this);
        ss2.setOnCheckedChangeListener(this);
        cv7.setOnCheckedChangeListener(this);
        cv8.setOnCheckedChangeListener(this);
        cv9.setOnCheckedChangeListener(this);
        cl1.setOnCheckedChangeListener(this);
        bb12.setOnCheckedChangeListener(this);
        update.setOnClickListener(this);
        SPserver.setOnItemSelectedListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update:
                AccountEntity updatedAccountEntity = getAccountEntity();
                AVObject accounts = AVObject.createWithoutData("accounts", accountEntity.getId());
                accounts.put("num", updatedAccountEntity.getNum());
                accounts.put("ships", updatedAccountEntity.getShips());
                accounts.put("price", updatedAccountEntity.getPrice());
                accounts.put("tag", updatedAccountEntity.getTag());
                accounts.put("server", updatedAccountEntity.getServer());
                accounts.put("ships_num",updatedAccountEntity.getShips().size());
                accounts.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            UtilDialog.showNormalToast("修改成功!");
                            setResult(0);
                            finish();
                        } else {
                            UtilDialog.showNormalToast("修改失败!");
                        }
                    }
                });
                break;
        }
    }

    public AccountEntity getAccountEntity() {
        String num = ETnum.getText().toString();
        String level = ETlevel.getText().toString();
        String resource = ETresource.getText().toString();
        String price = ETprice.getText().toString();
        String tag = ETtag.getText().toString();
        String server = spinnerValue;
        List<String> list = new ArrayList<>();
        if (bb1.isChecked()) {
            String bb1 = "密苏里";
            list.add(bb1);
        }
        if (bb2.isChecked()) {
            String bb2 = "狮";
            list.add(bb2);
        }
        if (bb3.isChecked()) {
            String bb3 = "俾斯麦";
            list.add(bb3);
        }
        if (bb4.isChecked()) {
            String bb4 = "提尔比茨";
            list.add(bb4);
        }
        if (bb5.isChecked()) {
            String bb5 = "华盛顿";
            list.add(bb5);
        }
        if (bb6.isChecked()) {
            String bb6 = "南达科他";
            list.add(bb6);
        }
        if (bb7.isChecked()) {
            String bb7 = "北卡罗莱纳";
            list.add(bb7);
        }
        if (bb8.isChecked()) {
            String bb8 = "前卫";
            list.add(bb8);
        }
        if (bb9.isChecked()) {
            String bb9 = "黎塞留";
            list.add(bb9);
        }
        if (bb10.isChecked()) {
            String bb10 = "安德烈·亚多利亚";
            list.add(bb10);
        }
        if (bb11.isChecked()) {
            String bb11 = "维内托";
            list.add(bb11);
        }
        if (av1.isChecked()) {
            String av1 = "大凤";
            list.add(av1);
        }
        if (cv1.isChecked()) {
            String cv1 = "企业";
            list.add(cv1);
        }
        if (cv2.isChecked()) {
            String cv2 = "赤城";
            list.add(cv2);
        }
        if (cv3.isChecked()) {
            String cv3 = "加贺";
            list.add(cv3);
        }
        if (cv4.isChecked()) {
            String cv4 = "埃塞克斯";
            list.add(cv4);
        }
        if (cv5.isChecked()) {
            String cv5 = "翔鹤";
            list.add(cv5);
        }
        if (cv6.isChecked()) {
            String cv6 = "瑞鹤";
            list.add(cv6);
        }
        if (ss1.isChecked()) {
            String ss1 = "大青花鱼";
            list.add(ss1);
        }
        if (ss2.isChecked()) {
            String ss2 = "射水鱼";
            list.add(ss2);
        }
        if (cv7.isChecked()) {
            String cv7 = "皇家方舟";
            list.add(cv7);
        }
        if (cv8.isChecked()) {
            String cv8 = "大黄蜂";
            list.add(cv8);
        }
        if (cv9.isChecked()) {
            String cv9 = "约克城";
            list.add(cv9);
        }
        if (cl1.isChecked()) {
            String cl1 = "大淀";
            list.add(cl1);
        }
        if (bb12.isChecked()) {
            String bb12 = "约克公爵";
            list.add(bb12);
        }
        return new AccountEntity(num, list,  price, tag, server);
    }

    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {
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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
