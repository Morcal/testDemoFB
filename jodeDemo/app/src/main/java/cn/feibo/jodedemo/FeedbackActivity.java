package cn.feibo.jodedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.feibo.jodedemo.Dao.IEntityListener;
import cn.feibo.jodedemo.Dao.ReturnCode;
import cn.feibo.jodedemo.adapter.FeedBackAdapter;
import cn.feibo.jodedemo.manager.FeedbackManager;
import cn.feibo.jodedemo.manager.LoadListener;
import cn.feibo.jodedemo.manager.OperateManager;
import cn.feibo.jodedemo.model.BaseListData;
import cn.feibo.jodedemo.model.Feedback;
import cn.feibo.jodedemo.model.Response;

/**
 * Created by Administrator on 2015/12/28.
 */
public class FeedbackActivity extends Activity {
    private String content;
    private List<Feedback> list;
    private Button butFeedBack;
    private EditText etContent;
    private Button btnSend;
    private ListView lvFeedBack;
    private FeedbackManager manager;
    private FeedBackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        manager = new FeedbackManager();
        adapter = new FeedBackAdapter(this);
        initView();
        lvFeedBack.setAdapter(adapter);
    }

    private void initView() {
        butFeedBack = (Button) findViewById(R.id.but_feedback);
        etContent = (EditText) findViewById(R.id.et_content);
        btnSend = (Button) findViewById(R.id.btn_send);
        lvFeedBack = (ListView) findViewById(R.id.lv_feedback);
        butFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.clearFocus();
                manager.loadFeedback(new IEntityListener<BaseListData<Feedback>>() {
                    @Override
                    public void result(Response<BaseListData<Feedback>> response) {
                        Log.i("FeedbackActivity", "reCode: " + response.rsCode);
                        if (response.rsCode == ReturnCode.RS_SUCCESS) {
                            BaseListData<Feedback> data = response.data;
                            list = data.items;
                            Log.i("FeedbackActivity", ";list size:" + list.size());
                            adapter.setItems(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将内容提交
                content = etContent.getText().toString().trim();
                sendFeedBack(content);
            }
        });
    }

    private void sendFeedBack(String content) {
        OperateManager.putFeedack(content, new FeedBackputListener());

    }

    private class FeedBackputListener implements LoadListener {
        @Override
        public void onSuccess() {
            // 将content添加到list
            addContent2List(content);
            Toast.makeText(FeedbackActivity.this, "意见已提交", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFail(int code) {
            Toast.makeText(FeedbackActivity.this, "建议提交失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void addContent2List(String content) {
        Feedback feedback = new Feedback();
        feedback.type = Feedback.TYPE_USER;
        feedback.content = content;
        feedback.author.avatar = adapter.getUserAvatarurl();
        adapter.getItems().add(feedback);
        adapter.notifyDataSetChanged();

    }
}
