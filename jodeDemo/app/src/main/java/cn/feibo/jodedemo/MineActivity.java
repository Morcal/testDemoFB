package cn.feibo.jodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MineActivity extends AppCompatActivity  {

    private RelativeLayout settingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initView());
        initEvent();

    }

    private void initEvent() {
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MineActivity.this,FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

    private View initView(){
       View root=LayoutInflater.from(this).inflate(R.layout.layout_footer,null);
       LinearLayout contain= (LinearLayout) root.findViewById(R.id.contain);
       View content=LayoutInflater.from(this).inflate(R.layout.activity_mine,null);
        settingLayout= (RelativeLayout) content.findViewById(R.id.item_setting);
        contain.addView(content);
       return root;
   }


}
