> 写的不详细，参考：http://blog.csdn.net/coder_pig/article/details/48805043

> Notification:就是服务器推送的消息，显示在手机最顶部的任务栏的消息。

如图：
![这里写图片描述](http://img.blog.csdn.net/20160906204656507)

##有关的类和方法##
###类：###
NotificationManager：通知管理器：可以发送和取消通知
Notification：通知对象，可以设置属性
###方法：###
```
.setContentTitle("标题")
.setContentText("内容内容内容内容内容内容内容")
.setSubText("子内容子内容子内容子内容子内容")
.setTicker("摘要")//通知显示的内容
.setSmallIcon(R.mipmap.ic_launcher)//设置小图标，4.x在右边，5.x在左边
.setLargeIcon(bitmap)//设置大图标
.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)//设置默认的呼吸灯和震动
.setContentIntent(pendingIntent)//打开通知后做什么
.setAutoCancel(true);//点击后自动消失

notify(NotificationId, notification);发送通知
```

##下面通过一个Demo演示通知的使用##

> MainActivity有2个Button，一个打开通知，一个取消通知。

![这里写图片描述](http://img.blog.csdn.net/20160906205336099)
###逻辑代码###
```
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
```


###创建通知并设置属性  + 发布通知 + 点击通知跳转到新Activity###

```
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
```

###获取PendingIntent###
```
private void setPendingIntent() {
       Intent intent = new Intent(this, NewActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);
   }
```
Ticker：
![这里写图片描述](http://img.blog.csdn.net/20160906210755032)
ContentTitle + ContentText + SubText + LargeIcon + SmallIcon
![这里写图片描述](http://img.blog.csdn.net/20160906210928588)

##Bug##
1 点击通知，开启的activity是空白页，但是我们设置的activity是有内容的，这是为什么呢？

> 原因：重写的onCreate(...)方法有2种：第一种：用protected修饰，且只有1个参数：Bundle;第二种：用public修饰，且有2个参数：Bundle + PersistableBundle。

![这里写图片描述](http://img.blog.csdn.net/20160906210608282)



> 有的博客说到：
Android中notification点击进入新activity，会打开多个相同activity，需要在Intent设置如下flag ：intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
这种情况我还没遇到。


