package com.example.practicalfive;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Municipality extends AppCompatActivity {

    private TextView tvCityName;
    private TextView tvCityInfo;
    private ImageView ivCityImage;
    private Button btnVisitSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        // 1. 找到所有的 UI 组件
        tvCityName = findViewById(R.id.tvCityName);
        tvCityInfo = findViewById(R.id.tvCityInfo);
        ivCityImage = findViewById(R.id.ivCityImage);
        btnVisitSite = findViewById(R.id.btnVisitSite);

        // 2. 接收从 MainActivity 传过来的城市名
        String incomingName = getIntent().getStringExtra("EXTRA_MUNICIPALITY");

        // 3. 安全校验：如果传入数据不合法（或者为空），提示错误并关闭当前页面
        String validName = TextProcessor.processMunicipality(incomingName);

        if (validName == null) {
            Toast.makeText(this, "Error: Invalid municipality data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 4. 更新界面：把传过来的名字显示到顶部 TextView 上
        tvCityName.setText(validName);
        tvCityInfo.setText("Data successfully received for " + validName + ".");
    }
}