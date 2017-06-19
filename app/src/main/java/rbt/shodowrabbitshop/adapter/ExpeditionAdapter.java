package rbt.shodowrabbitshop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.activity.CheckExpeditionActivity;
import rbt.shodowrabbitshop.activity.ExpeditionActivity;
import rbt.shodowrabbitshop.base.DateUtil;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.ExpeditionEntity;

/**
 * Created by Administrator on 2017/2/19.
 */
public class ExpeditionAdapter extends BaseAdapter {
    private List<ExpeditionEntity> data;
    private Context context;

    public ExpeditionAdapter(List<ExpeditionEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        Holder holder;//缓存
        if(convertView!=null){
            view=convertView;
            holder= (Holder) view.getTag();
        }else{
            view=View.inflate(context, R.layout.item_expedition_data,null);
            holder=new Holder();
            holder.tv_account= (TextView) view.findViewById(R.id.tv_account);
            holder.tv_targetTime= (TextView) view.findViewById(R.id.tv_targetTime);
            holder.ll_expedition= (LinearLayout) view.findViewById(R.id.ll_expedition);
            view.setTag(holder);
        }
        ExpeditionEntity expeditionEntity=data.get(position);
        holder.tv_account.setText(expeditionEntity.getAccount());
        holder.tv_targetTime.setText(expeditionEntity.getTargetTime());
        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now=dateFormat.format(date);
        if (DateUtil.compareTwoTime(expeditionEntity.getTargetTime(), now)){
            holder.tv_targetTime.setTextColor(context.getResources().getColor(R.color.color11_red));
        }else{
            holder.tv_targetTime.setTextColor(context.getResources().getColor(R.color.color10_green));
        }
        holder.ll_expedition.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                String text = "标题<font color='#e85e4f'>" + data.get(position).getAccount() + "</font>，确定删除吗？";
                alertDialog.setTitle("删除").setMessage(Html.fromHtml(text)).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        AVQuery.doCloudQueryInBackground("delete from expeditions where objectId='" + data.get(position).getId() + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                            @Override
                            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                if (e == null) {
                                    UtilDialog.showNormalToast("删除成功!");
                                    ExpeditionActivity.refresh();
                                    dialog.dismiss();
                                } else {
                                    UtilDialog.showNormalToast("删除失败!");
                                }
                            }
                        });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return true;
            }
        });
        holder.ll_expedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, CheckExpeditionActivity.class);
                it.putExtra("data",data.get(position));
                context.startActivity(it);
            }
        });
        return view;
    }
    public class Holder{
        public LinearLayout ll_expedition;
        public TextView tv_account;
        public TextView tv_targetTime;
    }
}
