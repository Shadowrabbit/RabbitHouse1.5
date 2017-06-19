package rbt.shodowrabbitshop.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.adapter.CheckAdapter;
import rbt.shodowrabbitshop.base.BaseActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.AccountEntity;
import rbt.shodowrabbitshop.entity.DeleteInterface;

public class SearchAccountActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener, DeleteInterface {
    private int server;//1代表当前服务器安卓，2代表ios
    //左右分页
    private TextView seg_left;
    private TextView seg_right;
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
    private Button submit;
    private PullToRefreshListView listview;
    private List<AccountEntity> tmpData;//适配器数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_account);
        setTitle("搜索账号");
        titleShow();
        initView();
    }

    private void initView() {
        seg_left = (TextView) findViewById(R.id.seg_left);
        seg_right = (TextView) findViewById(R.id.seg_right);
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
        submit = (Button) findViewById(R.id.submit);
        listview = (PullToRefreshListView) findViewById(R.id.account_data);
        seg_left.setOnClickListener(this);
        seg_right.setOnClickListener(this);
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
        submit.setOnClickListener(this);
        setRateLeft();
    }

    private void setRateLeft() {
        seg_left.setSelected(true);
        seg_right.setSelected(false);
        seg_left.setTextSize(18);
        seg_right.setTextSize(16);
        server = 1;
    }

    private void setRateRight() {
        seg_left.setSelected(false);
        seg_right.setSelected(true);
        seg_left.setTextSize(16);
        seg_right.setTextSize(18);
        server = 2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.seg_left:
                setRateLeft();
                break;
            case R.id.seg_right:
                setRateRight();
                break;
            case R.id.submit:
                List<String> list = getSearchShips();
                AVQuery<AVObject> query1 = new AVQuery<>("accounts");
                query1.whereContainsAll("ships", list);
                AVQuery<AVObject> query2 = new AVQuery<>("accounts");
                if (server == 1) {
                    query2.whereContains("server", "安卓");
                } else {
                    query2.whereContains("server", "ios");
                }
                AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1, query2));
                query.limit(999);
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e == null) {
                            tmpData = new ArrayList<AccountEntity>();//查询到的临时数据
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
                            View view = View.inflate(SearchAccountActivity.this, R.layout.activity_check_account, null);
                            listview = (PullToRefreshListView) view.findViewById(R.id.account_data);
                            LinearLayout LLsegment= (LinearLayout) view.findViewById(R.id.ll_segment);
                            LLsegment.setVisibility(View.GONE);
                            listview.setOnItemClickListener(SearchAccountActivity.this);
                            CheckAdapter checkAdapter = new CheckAdapter(tmpData, SearchAccountActivity.this,SearchAccountActivity.this,2);
                            listview.setAdapter(checkAdapter);
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SearchAccountActivity.this);
                            alertDialog.setView(view).setTitle("查询结果").setCancelable(false).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        } else {
                            UtilDialog.showNormalToast("获取失败！");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public List<String> getSearchShips() {
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
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(SearchAccountActivity.this, CheckAccountActivity2.class);
        it.putExtra("data", tmpData.get(position - 1));
        startActivity(it);
    }
    @Override
    public void onDelete() {

    }
}
