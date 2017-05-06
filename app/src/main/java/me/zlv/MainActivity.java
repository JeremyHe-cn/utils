package me.zlv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import me.zlv.utils.ReflectUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content = "";
        TextView contentTv = (TextView) findViewById(R.id.content_tv);
        List<Class> classList = ReflectUtils.listAllClass(this);
        content = "class num: " + classList.size();

        classList = ReflectUtils.listClass(this, "me.zlv.utils");
        content += "\r\n me.zlv: " + classList.size();

        contentTv.setText(content);
    }
}
