package com.cqc.notificatondemo01;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private NotificationManager manager;
    private static final int NotificationId = 1;
    private PendingIntent pendingIntent;
    private Notification.Builder builder;
    private Notification notification;
    private Button btn_open;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_open = (Button) findViewById(R.id.btn_open);
        btn_close = (Button) findViewById(R.id.btn_close);

        //打开通知
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initNotification();
            }
        });

        //关闭通知
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.cancel(NotificationId);//取消通知（根据id）
//                manager.cancelAll();//关闭所有通知
            }
        });
    }


    //创建通知对象并设置参数
    private void initNotification() {
        //获取 NotificationManager
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //创建pendingIntent对象
        setPendingIntent();

        //获取bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        builder = new Notification.Builder(this);
        builder.setContentTitle("标题")
                .setContentText("内容内容内容内容内容内容内容")
                .setSubText("子内容子内容子内容子内容子内容")
                .setTicker("摘要")//通知显示的内容
                .setSmallIcon(R.mipmap.ic_launcher)//设置小图标，4.x在右边，5.x在左边
                .setLargeIcon(bitmap)//设置大图标
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)//设置默认的呼吸灯和震动
                .setContentIntent(pendingIntent)//打开通知后做什么
                .setAutoCancel(true);//点击后自动消失

        notification = builder.build();

        //发布通知
        manager.notify(NotificationId, notification);
    }

    private void setPendingIntent() {
        Intent intent = new Intent(this, NewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
    }

}
