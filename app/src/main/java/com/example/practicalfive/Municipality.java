package com.example.practicalfive;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
        String validName = TextProcessor.processMunicipality(incomingName);

        if (validName == null) {
            Toast.makeText(this, "Error: Invalid municipality data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 3. 准备数据和变量
        String[] infoArray = getResources().getStringArray(R.array.municipalities_array);

        int colorResId = 0;
        int imageResId = 0;
        int arrayIndex = 0;
        String websiteUrl = "";

        // 4. 根据城市名匹配对应的 颜色、图片 和 数组索引
        // 注意：数组索引严格按照 arrays.xml 中的顺序：北京(0)、上海(1)、重庆(2)、天津(3)
        switch (validName) {
            case "Beijing":
                colorResId = R.color.municipality_beijing;
                imageResId = R.drawable.beijing;
                arrayIndex = 0;
                websiteUrl = "https://english.beijing.gov.cn/";
                break;
            case "Shanghai":
                colorResId = R.color.municipality_shanghai;
                imageResId = R.drawable.shanghai;
                arrayIndex = 1;
                websiteUrl = "https://english.shanghai.gov.cn/";
                break;
            case "Chongqing":
                colorResId = R.color.municipality_chongqing;
                imageResId = R.drawable.chongqing;
                arrayIndex = 2;
                websiteUrl = "https://english.cq.gov.cn/";
                break;
            case "Tianjin":
                colorResId = R.color.municipality_tianjin;
                imageResId = R.drawable.tianjin;
                arrayIndex = 3;
                websiteUrl = "https://en.tj.gov.cn/";
                break;
        }

        // 5. 获取具体的颜色值
        int themeColor = ContextCompat.getColor(this, colorResId);
        int lightTextColor = ContextCompat.getColor(this, R.color.color_textlight);

        // 6. 动态更新 UI
        // 设置城市名称：文字、背景色、文字颜色（白色，保证对比度）
        tvCityName.setText(validName);
        tvCityName.setBackgroundColor(themeColor);
        tvCityName.setTextColor(lightTextColor);

        // 设置城市图片
        ivCityImage.setImageResource(imageResId);

        // 设置信息介绍文字：内容、文字颜色（对应主题色）
        tvCityInfo.setText(infoArray[arrayIndex]);
        tvCityInfo.setTextColor(themeColor);

        // 设置按钮背景色（使用 BackgroundTintList 改变按钮颜色）
        btnVisitSite.setBackgroundTintList(ColorStateList.valueOf(themeColor));
        btnVisitSite.setTextColor(lightTextColor);
        final String finalWebsiteUrl = websiteUrl;

        btnVisitSite.setOnClickListener(v -> {
            // 创建隐式 Intent，动作是 ACTION_VIEW，数据是网址
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalWebsiteUrl));
            try {
                startActivity(intent); // 让系统寻找浏览器并打开
            } catch (ActivityNotFoundException e) {
                // 如果用户的手机/模拟器上没有安装任何浏览器，会进入这里
                Toast.makeText(Municipality.this, "No application found to open this website.", Toast.LENGTH_SHORT).show();
            }
        });
        // =================================================================
    }

}
