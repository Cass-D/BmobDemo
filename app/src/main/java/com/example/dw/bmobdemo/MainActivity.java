package com.example.dw.bmobdemo;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {
    private EditText mName,mFeedback,mQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化BmobSDK
        Bmob.initialize(this,"8b778a0a182fa7fd4e96ccb61ddebf4a");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);

        mName=(EditText)findViewById(R.id.name);
        mFeedback=(EditText)findViewById(R.id.feedback);
        mQuery=(EditText)findViewById(R.id.query_et);
    }
    /*
    * 信息推送功能
    * */
    public void pushAll(View view){
        BmobPushManager push=new BmobPushManager();
        push.pushMessageAll("这是给所有设备推送的一条消息。");
       /*
       //第二种推送方式
        // 创建Installation表的BmobQuery对象
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        // 并添加条件为设备类型属于android
        query.addWhereEqualTo("deviceType", "android");
        // 设置推送条件给bmobPushManager对象。
        push.setQuery(query);
        // 设置推送消息，服务端会根据上面的查询条件，来进行推送这条消息
        push.pushMessage("这是一条推送给所有Android设备的消息。");*/
    }
    /*
    * 添加数据
    * */
    public void submit(View view){
        String name=mName.getText().toString();
        String feedback=mFeedback.getText().toString();
        if(name.equals("") || feedback.equals("")){return;}
        Feedback feedbackObj=new Feedback();
        feedbackObj.setName(name);
        feedbackObj.setFeedback(feedback);
        feedbackObj.save(new SaveListener<String>(){
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"submit success",Toast.LENGTH_LONG).show();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    /*
    * 查询一条指定内容数据
    * */
    public void queryFeedback(View view){
        String str=mQuery.getText().toString();
        if(str.equals("")){
            return;
        }
        BmobQuery<Feedback> query=new BmobQuery<Feedback>();
        query.addWhereEqualTo("name",str);
        //query.setLimit(2);
        //query.addWhereNotEqualTo("name",str);
        query.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> list, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Query");
                    String str=" ";
                    for(Feedback feedback : list){
                        str +=feedback.getName()+":"+feedback.getFeedback()+"\n";
                    }
                    builder.setMessage(str);
                    builder.create().show();
                }else{
                    Toast.makeText(MainActivity.this,"查询失败"+ e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /*
    * 查询所有数据
    * */
    public void queryAll(View view){
        BmobQuery<Feedback> query=new BmobQuery<Feedback>();
        query.findObjects(new FindListener<Feedback>() {
            @Override
            public void done(List<Feedback> list, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Query");
                    String str=" ";
                    for(Feedback feedback : list){
                        str +=feedback.getName()+":"+feedback.getFeedback()+"\n";
                    }
                    builder.setMessage(str);
                    builder.create().show();
                }else{
                    Toast.makeText(MainActivity.this,"查询失败"+ e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        /*query.getObject(new FindListener<Feedback>()
        {

            @Override
            public void done(List<Feedback> list, BmobException e) {
                if(e==null){
                    Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Query");
                    String str=" ";
                    for(Feedback feedback : list)
                        str +=feedback.getName()+":"+feedback.getFeedback()+"\n";
                    }
                    builder.setMessage(str);
                    builder.create().show();
                }else{
                    Toast.makeText(MainActivity.this,"查询失败"+ e.getMessage(),Toast.LENGTH_LONG).show());
                }
            }
        });*/
       /* BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject("6b6c11c537", new >QueryListener<Person>() {
            @Override
            public void done(Person object,BmobException e) {
                if(e==null){
                    toast("查询成功");
                }else{
                    toast("查询失败：" + e.getMessage());
                }
            }
        });*/
    }
}

