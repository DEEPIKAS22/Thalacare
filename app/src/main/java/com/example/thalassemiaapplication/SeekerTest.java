package com.example.thalassemiaapplication;

import static java.lang.System.currentTimeMillis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;

public class SeekerTest extends AppCompatActivity {


    //ActivityMainBinding binding;
    Uri imageUri;
    ImageView img;
    EditText  TSname, TSage,TSdob,TSadd,TSemail, TSphone;
    RadioButton bloodAP, bloodAN,bloodBP, bloodBN, bloodABP, bloodABN, bloodOP, bloodON,drugYes,drugNo, aidsYes,aidsNo,TattoYes,TattoNo;
    String drug,aids,tatto ;
    StorageReference storageReference;
    Button uploadbtn1, uploadbtn2;
    Button SubT;
    DatabaseReference dbr;
    String EmailIDType ;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("TEST_SEEKER");

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    String uid ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_test);

        TSname = findViewById(R.id.TestingReqName);
        TSage = findViewById(R.id.TestingReqAge);
        TSemail = findViewById(R.id.TestingReqMail);
        TSphone = findViewById(R.id.TestingReqPhone) ;
        TSdob = findViewById(R.id.TestingReqDOB);
        TSadd = findViewById(R.id.TestingReqAddress);
        SubT = findViewById(R.id.SubmitTR);

        //RadioButton

        bloodAP = findViewById(R.id.radioBGAposR) ;
        bloodAN = findViewById(R.id.radioBGAnegR) ;
        bloodBP = findViewById(R.id.radioBGBposR) ;
        bloodBN = findViewById(R.id.radioBGBnegR) ;
        bloodABP = findViewById(R.id.radioBGABposR) ;
        bloodABN = findViewById(R.id.radioBGABnegR) ;
        bloodOP = findViewById(R.id.radioBGOposR) ;
        bloodON = findViewById(R.id.radioBGOnegR) ;

        drugYes = findViewById(R.id.Testingyes3R);
        drugNo = findViewById(R.id.Testingno3R);

        aidsYes = findViewById(R.id.Testingyes11R);
        aidsNo = findViewById(R.id.Testingno11R);

        TattoYes = findViewById(R.id.Testingyes15R);
        TattoNo = findViewById(R.id.Testingno15R);

        uploadbtn1 = (Button) findViewById(R.id.upload_Button);
        uploadbtn2 = (Button) findViewById(R.id.BloodReportButton);

        dbr = FirebaseDatabase.getInstance().getReference().child("REQUESTER");
        storageReference = FirebaseStorage.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        uploadbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });

        uploadbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPDFFile();
            }
        });

        SubT = findViewById(R.id.SubmitTR);
        SubT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateTAge() != true | validateTDOB() != true | validateTAdd() != true | validateTEmail() != true) {   //
                    return;
                }
//
                String TSeekerName = TSname.getText().toString();
                String TSeekerAge = TSage.getText().toString();
                String TSeekerEmail = TSemail.getText().toString();
                EmailIDType = TSeekerEmail ;
                String TSeekerPhone = TSphone.getText().toString();
                String TSeekerDob = TSdob.getText().toString();
                String TSeekerAdd = TSadd.getText().toString();

                if (drugYes.isChecked()) {
                    drug = drugYes.getText().toString();
                } else if (drugNo.isChecked()) {
                    drug = drugNo.getText().toString();
                }

                if (aidsYes.isChecked()) {
                    aids = aidsYes.getText().toString();
                } else if (aidsNo.isChecked()) {
                    aids = aidsNo.getText().toString();
                }

//
                if (TattoYes.isChecked()) {
                    tatto = TattoYes.getText().toString();
                } else if (TattoNo.isChecked()) {
                    tatto = TattoNo.getText().toString();
                }
//
                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Name", TSeekerName);
                userMap.put("Email", TSeekerEmail);
                userMap.put("PhoneNo", TSeekerPhone);
                userMap.put("Age", TSeekerAge);

                userMap.put("DOB", TSeekerDob);
                userMap.put("Address", TSeekerAdd);

                userMap.put("Drug", drug);
                userMap.put("AIDS", aids);
                userMap.put("Tattoo", tatto);

                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();
                root.child(uid).setValue(userMap);
                Toast.makeText(SeekerTest.this, "Done!", Toast.LENGTH_SHORT).show();



                Toast.makeText(SeekerTest.this, "Your test has been submitted now you can wait for your schedule!", Toast.LENGTH_SHORT).show();
            }
        });

    }




    private void selectPDFFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData()!=null)
        {
            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading...");
        progressDialog.show();

        StorageReference reference = storageReference.child("SEEKER/"+currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri url = uri.getResult();
                //
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uploadPDF pdf = new uploadPDF(uri.toString());
                        String uid = user.getUid();
                        //dbr.child(uid).setValue(uri.toString());
                        String modelId = dbr.push().getKey();
                        dbr.child(modelId).setValue(pdf);
                    }
                });  //
                Toast.makeText(SeekerTest.this,"File uploaded",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded: "+(int)progress+"%");
            }
        });
    }

    public boolean validateTAge() {
        String age = TSage.getText().toString();

        if (age.isEmpty()) {
            TSage.setError("Invalid");
            return false;
        } else {
            TSage.setError(null);
            return true;
        }
    }

    public boolean validateTDOB() {
        String dob = TSdob.getText().toString();
        if (dob.isEmpty()) {
            TSdob.setError("Invalid");
            return false;
        } else {
            TSdob.setError(null);
            return true;
        }
    }

    public boolean validateTAdd() {
        String add = TSadd.getText().toString();

        if (add.isEmpty()) {
            TSadd.setError("Invalid");
            return false;
        } else {
            TSadd.setError(null);
            return true;
        }
    }

    public boolean validateTEmail() {
        String Re = TSemail.getText().toString();
        if (Re.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Re).matches()) {
            TSemail.setError("Invalid Email Id");
            return false;
        } else {
            TSemail.setError(null);
            return true;
        }
    }

}