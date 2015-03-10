package com.aalasolutions.apps.applocker.lock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.aalasolutions.apps.applocker.R;

/**
 * Created by Athar on 3/10/2015.
 */
public class ErrorDialog extends Activity implements android.view.View.OnClickListener {

    public Context c;
    public Dialog d;
    public Button ok;
    String open;

    public ErrorDialog(Context a, String open) {

        this.open = open;
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    public ErrorDialog() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        final Context context=this;
        ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);
        ok.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               String packageName= getIntent().getStringExtra("package");
                Intent intent = LockService.getLockIntent(context, packageName);
                intent.setAction(LockService.ACTION_COMPARE);
                intent.putExtra(LockService.EXTRA_PACKAGENAME, packageName);
                startService(intent);
                finish();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                finish();
                break;
            default:
                break;
        }
    }
}
