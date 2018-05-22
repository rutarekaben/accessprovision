package rw.iraguha.secureaccess.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.rilixtech.CountryCodePicker;

import java.util.List;

import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    AppCompatEditText _phoneText;
    Button _loginButton;
    CountryCodePicker ccp;
    TextView login_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            login_title = findViewById(R.id.login_title);
            _phoneText = findViewById(R.id.input_phone);
            _loginButton = findViewById(R.id.btn_login);
            ccp          = findViewById(R.id.ccp);
            ccp.registerPhoneNumberTextView(_phoneText);
            _loginButton.setOnClickListener(v -> {
                if(is_validated()){
                    Dexter.withActivity(this)
                            .withPermission(Manifest.permission.READ_SMS)
                            .withListener(new PermissionListener() {
                                @Override public void onPermissionGranted(PermissionGrantedResponse response) {
                                    goToVerify();
                                }
                                @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                                    Toasty.error(getBaseContext(),"You will verify it manually!",Toast.LENGTH_SHORT,true).show();
                                    goToVerify();
                                }
                                @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                    Toasty.error(getBaseContext(),"You will verify it manually!",Toast.LENGTH_SHORT,true).show();
                                    goToVerify();
                                }
                            }).check();
                }
            });
    }

    public void goToVerify(){
        Intent mIntent = new Intent(this,VerifyActivity.class);
        mIntent.putExtra("phone",ccp.getFullNumber());
        startActivity(mIntent);
    }

    public Boolean is_validated(){
        if(!_phoneText.getText().toString().isEmpty()){
            return  true;
        }
        return false;
    }

    //permission requests

    public void requestPermission(){
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECEIVE_SMS

        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.isAnyPermissionPermanentlyDenied()){
                    showSettingsDialog();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Needed Permissions");
        builder.setMessage("Allow Some permission for the application to run properly");
        builder.setPositiveButton("GOTO SETTINGS",(dialog,which) ->{
           dialog.cancel();
           openSettings();
        });
        builder.setNegativeButton("Cancel",(dialog,which) ->{
           builder.show();
        });
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivityForResult(intent,101);
    }

}
