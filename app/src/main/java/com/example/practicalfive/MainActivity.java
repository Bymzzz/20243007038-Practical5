package com.example.practicalfive;

import android.os.Bundle;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // 1. 找到输入框
        EditText editTextMunicipality = findViewById(R.id.editTextMunicipality);

        // 2. 设置键盘监听器（当用户点击键盘上的 Go / Done 时触发）
        editTextMunicipality.setOnEditorActionListener((v, actionId, event) -> {
            // 判断是否按下了 Go 或 Done 键
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {

                // 获取用户输入的文本
                String userInput = editTextMunicipality.getText().toString();

                // 3. 使用 TextProcessor 进行校验
                String normalizedName = TextProcessor.processMunicipality(userInput);

                // 4. 判断校验结果
                if (normalizedName == null) {
                    // 校验失败：弹出提示，清空输入框，留在当前页面
                    Toast.makeText(MainActivity.this, "Invalid municipality.", Toast.LENGTH_SHORT).show();
                    editTextMunicipality.setText("");
                } else {
                    // 校验成功：暂时只弹出一个成功的提示
                    Toast.makeText(MainActivity.this, "Valid input: " + normalizedName, Toast.LENGTH_SHORT).show();

                    // 💡 这里就是以后要写 Intent 跳转的地方，等建好第二个页面再放开：

                    Intent intent = new Intent(MainActivity.this, Municipality.class);
                    intent.putExtra("EXTRA_MUNICIPALITY", normalizedName);
                    startActivity(intent);

                }
                return true; // 表示事件已处理
            }
            return false; // 其他按键不处理
        });
    }
}