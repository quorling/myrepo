package maininterface.sharon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.huawei.agconnect.appmessaging.AGConnectAppMessaging;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.entity.AAIDResult;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.service.AccountAuthService;

public class Menu extends AppCompatActivity implements View.OnClickListener {
    private CardView groomingcard,stuffcard, historycard, searchaddresscard;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private static final String TAG = "AppMessaging";
    private AGConnectAppMessaging appMessaging;
    private AccountAuthService mAuthService;
    private AccountAuthParams mAuthParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dl=(DrawerLayout) findViewById(R.id.dl);
        abdt= new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener (new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();

                if (id==R.id.profile)
                {
                    Toast.makeText(Menu.this,"Profile",Toast.LENGTH_SHORT).show();
                }
                if (id==R.id.payment)
                {
                    Toast.makeText(Menu.this,"Payment",Toast.LENGTH_SHORT).show();
                }
                if (id==R.id.settings)
                {
                    Toast.makeText(Menu.this,"Settings",Toast.LENGTH_SHORT).show();
                }
                if (id==R.id.logout)
                {
                    Task<Void> signOutTask = mAuthService.signOut();
                    signOutTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i(TAG, "signOut Success");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Log.i(TAG, "signOut fail");
                        }
                    });
                }
                return true;
            }
        });


        setTitle("Purr-fect Pets");

        groomingcard = (CardView) findViewById(R.id.grooming);
        stuffcard = (CardView) findViewById(R.id.stuff);
        historycard = (CardView) findViewById(R.id.history);
        searchaddresscard = (CardView) findViewById(R.id.searchaddress);
        //add click listener to card
        groomingcard.setOnClickListener(this);
        stuffcard.setOnClickListener(this);
        historycard.setOnClickListener(this);
        searchaddresscard.setOnClickListener(this);

        //get your device's aaid for testing asynchronously
        HmsInstanceId inst  = HmsInstanceId.getInstance(this);
        Task<AAIDResult> idResult =  inst.getAAID();
        idResult.addOnSuccessListener(new OnSuccessListener<AAIDResult>() {
            @Override
            public void onSuccess(AAIDResult aaidResult) {
                String aaid = aaidResult.getId();
                //textView.setText(aaid);
                Log.d(TAG, "getAAID success:" + aaid );

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "getAAID failure:" + e);
            }
        });

        appMessaging = AGConnectAppMessaging.getInstance();

    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        return abdt.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.grooming: i= new Intent(this,Groom.class);startActivity(i); break;
            case R.id.stuff : i= new Intent(this,Stuff.class);startActivity(i);break;
            case R.id.history : i= new Intent(this,History.class);startActivity(i);break;
            case R.id.searchaddress : i= new Intent(this,SearchAddress.class);startActivity(i);break;
            default: break;
        }

    }


}