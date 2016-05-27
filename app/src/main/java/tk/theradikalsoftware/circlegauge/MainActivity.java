package tk.theradikalsoftware.circlegauge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import tk.theradikalsoftware.trcirclegauge.CircleGaugeView;

public class MainActivity extends AppCompatActivity {
    CircleGaugeView gaugeView;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gaugeView = (CircleGaugeView) findViewById(R.id.circleGauge);
        dibujar();
    }

    private void dibujar(){
        new Thread() {
            public void run() {
                for (x = 0; x <= 120; x++) {
                    try {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gaugeView.SetValue(x);
                                if(x > 0 && x <= 50){
                                    gaugeView.SetSweetColor("#4CAF50");
                                }else if(x > 51 && x <= 80){
                                    gaugeView.SetSweetColor("#FFC107");
                                }else if(x > 81 && x <= 120){
                                    gaugeView.SetSweetColor("#F44336");
                                }
                                Log.d("THREAD", "DIBUJA: " + x);
                            }
                        });
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
