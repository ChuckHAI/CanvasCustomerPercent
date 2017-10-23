package clinet.iqianjin.canvasyuandemo2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements CircleNumberProgress.CallBackForFinish {


    private CircleNumberProgress cnp_citcleNumberProgress;

    private String url = "http://10.10.235.94:8087/C4000/M4001/";

    private String testURL = "10.10.133.188";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_progress_bar);
        cnp_citcleNumberProgress = (CircleNumberProgress) findViewById(R.id.cnp_citcleNumberProgress);
        cnp_citcleNumberProgress.setCallBack(this);

        Button btn_numberProgressBar = (Button) findViewById(R.id.btn_numberProgressBar);
        btn_numberProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnp_citcleNumberProgress.startProgress();
//                DataCleanManager.cleanApplicationData(MainActivity.this);
            }
        });

        Log.i("===========", String.valueOf(this.getFilesDir()));

        Log.i("===========", testURL.replaceAll("\\.", "#"));

    }

    @Override
    public void onIntentMethord() {
        Intent intent = new Intent(MainActivity.this, MyActivity.class);
        startActivity(intent);

//        Toast.makeText(MainActivity.this, "666", Toast.LENGTH_SHORT).show();
    }
}

