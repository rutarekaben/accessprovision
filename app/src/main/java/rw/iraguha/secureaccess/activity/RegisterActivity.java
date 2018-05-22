package rw.iraguha.secureaccess.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import rw.iraguha.secureaccess.R;
import rw.iraguha.secureaccess.activity.ui.FaceView;
import rw.iraguha.secureaccess.model.FaceResult;
import rw.iraguha.secureaccess.utils.ImageUtils;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private static final int RC_HANDLE_WRITE_EXTERNAL_STORAGE_PERM = 3;
    private static int PICK_IMAGE_REQUEST = 5;
    public FirebaseUser mUser;
    private EditText firstname,lastname,idnumber,phonenumber;
    private NiceSpinner mSpinner;
    private ImageView imagePreview;
    private Button choose_image,register;
    private AlertDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private byte[] selectedFace;
    // Create a storage reference from our app
    private StorageReference mStorageRef;
    private static final int MAX_FACE = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add people");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //construct sport dialog
        progressDialog = new SpotsDialog(this);
        //setting up firestore instance
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phonenumber = findViewById(R.id.phonenumber);
        idnumber = findViewById(R.id.idnumber);
        imagePreview = findViewById(R.id.imagePreview);
        choose_image = findViewById(R.id.choose_image);
        register = findViewById(R.id.register);
        mSpinner = findViewById(R.id.mSpinner);
        List<String> dataset = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.rank)));
        mSpinner.attachDataSource(dataset);

        choose_image.setOnClickListener(v->{
            int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (rc == PackageManager.PERMISSION_GRANTED) {
                getImage();
            } else {
                requestWriteExternalPermission();
            }
        });

        register.setOnClickListener(v->{
            if(isValid()){
                progressDialog.show();
                //adding data to firestore
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("firstname", firstname.getText().toString());
                user.put("lastname", lastname.getText().toString());
                user.put("phonenumber", phonenumber.getText().toString());
                user.put("idnumber", idnumber.getText().toString());
                user.put("rank", mSpinner.getSelectedIndex());
                user.put("isActive", true);
                user.put("isAdmin", false);
                // Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            Log.d(TAG, "Employee added with ID: " + documentReference.getId());
                            progressDialog.setMessage("starting uploading your face ...");
                            StorageReference innerFaceRef = mStorageRef.child(String.format("faces/%s.png",phonenumber.getText().toString().trim()));
                            UploadTask uploadTask = innerFaceRef.putBytes(selectedFace);
                            uploadTask.addOnFailureListener(exception -> {
                                // Handle unsuccessful uploads
                                Toasty.error(this,"Error occured while saving your face!",Toast.LENGTH_LONG,true).show();
                            }).addOnSuccessListener(taskSnapshot -> {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                progressDialog.dismiss();
                                Toasty.success(getApplicationContext(),"Employee Added Successfully",Toast.LENGTH_SHORT,true).show();
                            });
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error adding Employee in db", e);
                            progressDialog.dismiss();
                            Toasty.error(getApplicationContext(),"Error Occurred with adding Employee",Toast.LENGTH_SHORT,true).show();
                        });

            }else{
                Toasty.error(this,"Validation Failed!",Toast.LENGTH_LONG,true).show();
            }
        });
        register.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getActionMasked()){
                case 0:
                    register.setTextColor(Color.BLACK);
                    break;
                case 1:
                    register.setTextColor(Color.WHITE);
                    break;
            }
            return false;
        });
    }

    public void getImage() {
        // Create intent to Open Image applications like Gallery, Google Photos
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start the Intent
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
        } catch (ActivityNotFoundException i) {
            Toasty.error(RegisterActivity.this, "Your Device can not select image from gallery.", Toast.LENGTH_LONG,true).show();
        }
    }

    private Boolean isValid(){
        if(TextUtils.isEmpty(firstname.getText())){
            return  false;
        }

        if(TextUtils.isEmpty(lastname.getText())){
            return  false;
        }

        if(TextUtils.isEmpty(idnumber.getText())){
            return  false;
        }

        if(!TextUtils.isDigitsOnly(idnumber.getText())){
            return false;
        }

        if(idnumber.getText().length() != 16){
            return false;
        }
        if(!TextUtils.isDigitsOnly(phonenumber.getText())){
            return false;
        }
        if(phonenumber.getText().length() <10 || phonenumber.getText().length() > 12){
            return false;
        }
        if(imagePreview.getDrawable() == null){
            return false;
        }
        if(selectedFace == null){
            return false;
        }
        return true;
    }

    private void requestWriteExternalPermission() {
        Log.w(TAG, "Write External permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_WRITE_EXTERNAL_STORAGE_PERM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_WRITE_EXTERNAL_STORAGE_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Write External permission granted");
            // we have permission
            getImage();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            Bitmap bitmap = ImageUtils.getBitmap(ImageUtils.getRealPathFromURI(this, uri), 2048, 1232);
            if (bitmap != null) {
                Bitmap resImage = detectFace(bitmap);
                if(resImage != null){
                    //getting bye Array from image
                    selectedFace = rw.iraguha.secureaccess.awsutils.ImageUtils.convertBitmapToBytes(resImage);
                    imagePreview.setVisibility(View.VISIBLE);
                    imagePreview.setImageBitmap(resImage);
                }else
                {
                    Toasty.error(this, "No Face Detected In This Image Try Another", Toast.LENGTH_LONG, true).show();
                }
            }
            else
            {
                Toasty.error(this, "Cann't open this image.", Toast.LENGTH_LONG, true).show();
            }
        }
    }

    private Bitmap detectFace(Bitmap bitmap) {
        android.media.FaceDetector fdet_ = new android.media.FaceDetector(bitmap.getWidth(), bitmap.getHeight(), MAX_FACE);
        android.media.FaceDetector.Face[] fullResults = new android.media.FaceDetector.Face[MAX_FACE];
        fdet_.findFaces(bitmap, fullResults);
        ArrayList<FaceResult> faces_ = new ArrayList<>();
        for (int i = 0; i < MAX_FACE; i++) {
            if (fullResults[i] != null) {
                PointF mid = new PointF();
                fullResults[i].getMidPoint(mid);
                float eyesDis = fullResults[i].eyesDistance();
                float confidence = fullResults[i].confidence();
                float pose = fullResults[i].pose(android.media.FaceDetector.Face.EULER_Y);
                Rect rect = new Rect(
                        (int) (mid.x - eyesDis * 1.20f),
                        (int) (mid.y - eyesDis * 0.55f),
                        (int) (mid.x + eyesDis * 1.20f),
                        (int) (mid.y + eyesDis * 1.85f));
                if (rect.height() * rect.width() > 100 * 100) {
                    FaceResult faceResult = new FaceResult();
                    faceResult.setFace(0, mid, eyesDis, confidence, pose, System.currentTimeMillis());
                    faces_.add(faceResult);
                    Bitmap cropedFace = ImageUtils.cropFace(faceResult, bitmap, 0);
                    if (cropedFace != null) {
                        return  cropedFace;
                    }
                }
            }
        }
        return null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
