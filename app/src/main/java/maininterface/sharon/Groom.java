package maininterface.sharon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Groom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groom);

        ImageView ivCut = (ImageView) findViewById(R.id.ivCut);
        ImageView ivWash = (ImageView) findViewById(R.id.ivWash);
        ImageView ivCare = (ImageView) findViewById(R.id.ivCare);

        ivCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Groom.this,"Hair Cut selected",Toast.LENGTH_SHORT).show();
            }
        });

        ivWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Groom.this,"Washing selected",Toast.LENGTH_SHORT).show();
            }
        });

        ivCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Groom.this,"Pet Care selected",Toast.LENGTH_SHORT).show();
            }
        });

    }
}