package rbt.shodowrabbitshop.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

import rbt.shodowrabbitshop.R;


public class UtilDialog {
    public static ProgressDialog loadingDialog;
    public static void showTopToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -300);
        toast.show();
    }
    /**
     * 显示没有可用网络连接 在启动界面，关闭程序 在主界面，什么也不做
     */
    public static void showNetDisconnectDialog() {
        showNormalToast("当前没有可用网络!");
    }
    public static void showNormalToast(String message) {
        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }
    public static void showNormalToast(int messageRes) {
        Toast.makeText(MyApplication.getInstance(), messageRes, Toast.LENGTH_SHORT).show();
    }
    public static void showDialogLoading(Context context, String message, DialogInterface.OnKeyListener listener, boolean cancleAble) {
        dismissLoadingDialog(context);
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage(message);
        loadingDialog.setOnKeyListener(listener);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCancelable(cancleAble);
        if (!((Activity) context).isFinishing()) {
            loadingDialog.show();
        }
    }
    public static void dismissLoadingDialog(Context context) {
        if (context != null && !((Activity) context).isFinishing()
                && loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }
}
