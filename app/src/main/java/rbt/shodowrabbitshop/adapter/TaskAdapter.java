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

import java.util.List;

import rbt.shodowrabbitshop.R;
import rbt.shodowrabbitshop.activity.CheckTaskActivity;
import rbt.shodowrabbitshop.activity.TaskActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.TaskEntity;

/**
 * Created by Administrator on 2017/1/15.
 */
public class TaskAdapter extends BaseAdapter {
    private List<TaskEntity> data;
    private Context context;

    public TaskAdapter(List<TaskEntity> data, Context context) {
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
            view=View.inflate(context, R.layout.item_task,null);
            holder=new Holder();
            holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            holder.tv_updateTime= (TextView) view.findViewById(R.id.tv_updateTime);
            holder.tv_createTime= (TextView) view.findViewById(R.id.tv_createTime);
            holder.ll_task= (LinearLayout) view.findViewById(R.id.ll_task);
            view.setTag(holder);
        }
        TaskEntity taskEntity=data.get(position);
        holder.tv_title.setText(taskEntity.getTitle());
        holder.tv_updateTime.setText("修改时间"+taskEntity.getUpdateTime());
        holder.tv_createTime.setText("创建时间"+taskEntity.getCreateTime());
        holder.ll_task.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                String text = "标题<font color='#e85e4f'>" + data.get(position).getTitle() + "</font>，确定删除吗？";
                alertDialog.setTitle("删除").setMessage(Html.fromHtml(text)).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        AVQuery.doCloudQueryInBackground("delete from tasks where objectId='" + data.get(position).getId() + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                            @Override
                            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                if (e == null) {
                                    UtilDialog.showNormalToast("删除成功!");
                                    TaskActivity.refresh();
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
        holder.ll_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, CheckTaskActivity.class);
                it.putExtra("data",data.get(position));
                context.startActivity(it);
            }
        });
        return view;
    }
    public class Holder{
        public LinearLayout ll_task;
        public TextView tv_title;
        public TextView tv_updateTime;
        public TextView tv_createTime;
    }
}
