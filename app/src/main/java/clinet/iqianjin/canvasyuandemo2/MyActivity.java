package clinet.iqianjin.canvasyuandemo2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chuck on 2017/7/7.
 */

public class MyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        init();

        if (NotificationsUtils.isNotificationEnabled(MyActivity.this)) {
            Toast.makeText(MyActivity.this, "打开了", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MyActivity.this, "关闭了", Toast.LENGTH_SHORT).show();
        }
//        NotificationsUtils.goToSystemSetting(MyActivity.this);
    }


    private int noStr;

    private void init() {
        String jsonData = "{ \"code\":2,\"stu_name\":\"John\",\"stu_sex\":\"male\" }";

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            if (jsonObject == null) {
                return;
            }
            if (jsonObject.has("code") && !jsonObject.isNull("code")) {
                noStr = jsonObject.getInt("code");
            }

            Toast.makeText(MyActivity.this, noStr + "", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
