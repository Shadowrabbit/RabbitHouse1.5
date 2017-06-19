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
import rbt.shodowrabbitshop.activity.CheckAccountActivity;
import rbt.shodowrabbitshop.activity.CheckAccountActivity2;
import rbt.shodowrabbitshop.activity.TaskActivity;
import rbt.shodowrabbitshop.base.UtilDialog;
import rbt.shodowrabbitshop.entity.AccountEntity;
import rbt.shodowrabbitshop.entity.DeleteInterface;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class CheckAdapter extends BaseAdapter {
    private List<AccountEntity> data;
    private Context context;
    private DeleteInterface delete;
    private int type;

    public CheckAdapter(List<AccountEntity> data, Context context,DeleteInterface delete,int type) {
        this.data = data;
        this.context = context;
        this.delete=delete;
        this.type=type;
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
            view=View.inflate(context, R.layout.item_accounts,null);
            holder=new Holder();
            holder.tv_num= (TextView) view.findViewById(R.id.TV_num_data);
            holder.tv_ships= (TextView) view.findViewById(R.id.TV_ships_data);
            holder.tv_price= (TextView) view.findViewById(R.id.TV_price_data);
            holder.tv_server= (TextView) view.findViewById(R.id.TV_server_data);
            holder.ll_item= (LinearLayout) view.findViewById(R.id.ll_item);
            view.setTag(holder);
        }
        AccountEntity accountEntity=data.get(position);
        holder.tv_num.setText(accountEntity.getNum());
        holder.tv_ships.setText(accountEntity.getShips().size()+"");
        holder.tv_price.setText(accountEntity.getPrice());
        holder.tv_server.setText(accountEntity.getServer());
        switch (type){
            case 1:
                holder.ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(context, CheckAccountActivity2.class);
                        it.putExtra("data", data.get(position));
                        context.startActivity(it);
                    }
                });
                holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        String text = "标题<font color='#e85e4f'>" + data.get(position).getNum() + "</font>，确定删除吗？";
                        alertDialog.setTitle("删除").setMessage(Html.fromHtml(text)).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                AVQuery.doCloudQueryInBackground("delete from accounts where objectId='" + data.get(position).getId() + "'", new CloudQueryCallback<AVCloudQueryResult>() {
                                    @Override
                                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                                        if (e == null) {
                                            UtilDialog.showNormalToast("删除成功!");
                                            delete.onDelete();
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
                break;
        }
        return view;
    }
    public class Holder{
        public TextView tv_num;
        public TextView tv_ships;
        public TextView tv_price;
        public TextView tv_server;
        public LinearLayout ll_item;
    }
}
