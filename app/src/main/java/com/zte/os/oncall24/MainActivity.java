package com.zte.os.oncall24;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.zte.os.oncall24.service.LoginService;
import com.zte.os.oncall24.service.LoginStateListener;
import com.zte.os.oncall24.service.QueryAbnormalKpiService;

public class MainActivity extends Activity implements LoginStateListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleLoginBtn(View view) {
        EditText userName = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        new LoginService().login(this, userName.getText().toString().trim(), password.getText().toString().trim());
    }

    @Override
    public void loginSuccess(final String userName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent startServiceIntent = new Intent(MainActivity.this, QueryAbnormalKpiService.class);
                startServiceIntent.putExtra("username",userName);
                startService(startServiceIntent);
                new AlertDialog.Builder(MainActivity.this).setTitle("Login Success").setMessage("Login Success! \n\nYou will receive abnormal Kpi notification!")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MainActivity.this, ShowAbNormalKpiActivity.class));
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public void loginFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
