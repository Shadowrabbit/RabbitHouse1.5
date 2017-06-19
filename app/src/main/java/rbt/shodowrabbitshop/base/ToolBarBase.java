package rbt.shodowrabbitshop.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import rbt.shodowrabbitshop.R;

/**
 * Created by ASUS on 2016/9/7.
 */
public class ToolBarBase {
    private Context context;//设置主题需要
    private FrameLayout contentView;//最终返回的布局
    private Toolbar toolbar;//toolbar
    private LayoutInflater inflater;
    private static int[] ATTRS={R.attr.windowActionBarOverlay,R.attr.actionBarSize};
    public ToolBarBase(Context context, int layoutId){
        this.context=context;
        inflater=LayoutInflater.from(context);
        initContentView();//创建一个新的帧布局，全屏
        initUserView(layoutId);//填充自定义布局
        initToolBar();//填充toolbar
    }
    private void initToolBar() {
        View toolbarView = inflater.inflate(R.layout.actionbar,contentView);//这一步把toolbar填充到contentView了，不懂的话百度
        this.toolbar = (Toolbar) toolbarView.findViewById(R.id.toolbar);
    }
    @SuppressWarnings("ResourceType")
    private void initUserView(int layoutId) {
        View userView=inflater.inflate(layoutId,null);
        FrameLayout.LayoutParams params =new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(ATTRS);//获取主题
        boolean overly = typedArray.getBoolean(0, false);//是否悬浮
        int toolBarSize = (int) typedArray.getDimension(1,(int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();//回收
        params.topMargin = overly ? 0 : toolBarSize;//如果悬浮的话，上边距设为0，否则为默认actionbar高度，我们正常使用的都是不悬浮的
        contentView.addView(userView, params);
    }
    private void initContentView() {
        contentView=new FrameLayout(context);
        ViewGroup.LayoutParams params =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(params);
    }
    /***
     *
     * @return 返回最终视图
     */
    public FrameLayout getContentView() {
        return contentView;
    }
    /***
     *
     * @return 返回toolbar对象
     */
    public Toolbar getToolBar() {
        return toolbar;
    }
}
