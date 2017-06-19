package rbt.shodowrabbitshop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.entity.AccountEntity;

public class CheckAccountActivity2 extends BaseActivity implements View.OnClickListener {
    private TextView bb1;
    private TextView bb2;
    private TextView bb3;
    private TextView bb4;
    private TextView bb5;
    private TextView bb6;
    private TextView bb7;
    private TextView bb8;
    private TextView bb9;
    private TextView bb10;
    private TextView bb11;
    private TextView av1;
    private TextView cv1;
    private TextView cv2;
    private TextView cv3;
    private TextView cv4;
    private TextView cv5;
    private TextView cv6;
    private TextView ss1;
    private TextView ss2;
    private TextView cv7;
    private TextView cv8;
    private TextView cv9;
    private TextView cl1;
    private TextView bb12;
    private Button submit;
    private TextView TVnum;
    private TextView TVprice;
    private TextView TVtag;
    private TextView TVserver;
    private AccountEntity accountEntity;//上个页面传来的数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_account2);
        setTitle("账号详细");
        titleShow();
        initView();
        initData();
    }

    private void initData() {
        accountEntity= (AccountEntity) getIntent().getExtras().get("data");
        TVnum.setText(accountEntity.getNum());
        TVprice.setText(accountEntity.getPrice());
        TVtag.setText(accountEntity.getTag());
        TVserver.setText(accountEntity.getServer());
        List<String> ships=accountEntity.getShips();
        for (String a :ships){
            if (a.equals("密苏里")){
                bb1.setTextColor(getResources().getColor(R.color.color11_red));
                continue;
            }
            if (a.equals("狮")){
                bb2.setTextColor(getResources().getColor(R.color.color11_red));
                continue;
            }
            if (a.equals("俾斯麦")){
                bb3.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("提尔比茨")){
                bb4.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("华盛顿")){
                bb5.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("南达科他")){
                bb6.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("北卡罗莱纳")){
                bb7.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("前卫")){
                bb8.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("黎塞留")){
                bb9.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("安德烈·亚多利亚")){
                bb10.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("维内托")){
                bb11.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("大凤")){
                av1.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("企业")){
                cv1.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("赤城")){
                cv2.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("加贺")){
                cv3.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("埃塞克斯")){
                cv4.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("翔鹤")){
                cv5.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("瑞鹤")){
                cv6.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("大青花鱼")){
                ss1.setTextColor(getResources().getColor(R.color.color26_yellow));
                continue;
            }
            if (a.equals("射水鱼")){
                ss2.setTextColor(getResources().getColor(R.color.color26_yellow));
            }
            if (a.equals("皇家方舟")){
                cv7.setTextColor(getResources().getColor(R.color.color26_yellow));
            }
            if (a.equals("大黄蜂")){
                cv8.setTextColor(getResources().getColor(R.color.color25_pink));
            }
            if (a.equals("约克城")){
                cv9.setTextColor(getResources().getColor(R.color.color25_pink));
            }
            if (a.equals("约克公爵")){
                bb12.setTextColor(getResources().getColor(R.color.color26_yellow));
            }
            if (a.equals("大淀")){
                cl1.setTextColor(getResources().getColor(R.color.color26_yellow));
            }
        }
    }

    private void initView() {
        bb1= (TextView) findViewById(R.id.bb1);
        bb2= (TextView) findViewById(R.id.bb2);
        bb3= (TextView) findViewById(R.id.bb3);
        bb4= (TextView) findViewById(R.id.bb4);
        bb5= (TextView) findViewById(R.id.bb5);
        bb6= (TextView) findViewById(R.id.bb6);
        bb7= (TextView) findViewById(R.id.bb7);
        bb8= (TextView) findViewById(R.id.bb8);
        bb9= (TextView) findViewById(R.id.bb9);
        bb10= (TextView) findViewById(R.id.bb10);
        bb11= (TextView) findViewById(R.id.bb11);
        av1= (TextView) findViewById(R.id.av1);
        cv1= (TextView) findViewById(R.id.cv1);
        cv2= (TextView) findViewById(R.id.cv2);
        cv3= (TextView) findViewById(R.id.cv3);
        cv4= (TextView) findViewById(R.id.cv4);
        cv5= (TextView) findViewById(R.id.cv5);
        cv6= (TextView) findViewById(R.id.cv6);
        ss1= (TextView) findViewById(R.id.ss1);
        ss2= (TextView) findViewById(R.id.ss2);
        cv7= (TextView) findViewById(R.id.cv7);
        cv8= (TextView) findViewById(R.id.cv8);
        cv9= (TextView) findViewById(R.id.cv9);
        cl1= (TextView) findViewById(R.id.cl1);
        bb12= (TextView) findViewById(R.id.bb12);
        submit= (Button) findViewById(R.id.submit);
        TVnum = (TextView) findViewById(R.id.et_num);
        TVprice = (TextView) findViewById(R.id.et_price);
        TVtag = (TextView) findViewById(R.id.et_tag);
        TVserver= (TextView) findViewById(R.id.tv_server);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                finish();
                break;
        }
    }
}
