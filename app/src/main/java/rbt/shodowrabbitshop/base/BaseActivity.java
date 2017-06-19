package rbt.shodowrabbitshop.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import rbt.shodowrabbitshop.R;

/**
 * Created by Administrator on 2017/1/8.
 */
public class BaseActivity extends AppCompatActivity {
    private TextView title;
    private Button functionLeft;
    private LinearLayout functionRight;
    private ToolBarBase toolbarBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一个参数是下一个页面进入的动画，第二个参数是原页面退出的动画
        overridePendingTransition(R.anim.gk_push_right_in, R.anim.gk_push_right_out);
    }
    @Override
    public void setContentView(int layoutResID) {
        toolbarBase = new ToolBarBase(this, layoutResID);
        setContentView(toolbarBase.getContentView());
        functionLeft = (Button) findViewById(R.id.function_left);
        functionRight = (LinearLayout) findViewById(R.id.function_right);
        title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbarBase.getToolBar());
        setHomeBackBtn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /***
     * @param listener 返回键重写
     */
    public void setHomeBackBtn(View.OnClickListener listener) {
        functionLeft.setOnClickListener(listener);
    }

    /***
     * @param functionRight 右功能键的图标
     * @param listener      右功能键的功能
     */
    public void setRightFunctionView(View functionRight, View.OnClickListener listener) {
        this.functionRight.addView(functionRight, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        functionRight.setOnClickListener(listener);
    }

    /***
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    /***
     * 显示标题
     */
    public void titleShow() {
        toolbarBase.getToolBar().setVisibility(View.VISIBLE);
    }

    /***
     * 隐藏标题
     */
    public void titleHide() {
        toolbarBase.getToolBar().setVisibility(View.GONE);
    }

    /***
     * 隐藏返回键
     */
    public void BackBtnHide() {
        functionLeft.setVisibility(View.INVISIBLE);
        functionLeft.setClickable(false);
    }

    /***
     * 显示返回键
     */
    public void BackBtnShow() {
        functionLeft.setVisibility(View.VISIBLE);
        functionLeft.setClickable(true);
    }

    /***
     * @return 返回右功能键
     */
    public LinearLayout getFunctionRight() {
        return functionRight;
    }

    /***
     * 退出的时候右进右出的动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.gk_push_left_in, R.anim.gk_push_left_out);
    }
}
