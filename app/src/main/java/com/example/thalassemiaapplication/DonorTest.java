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

public class DonorTest extends AppCompatActivity {

    Uri imageUri;
    ImageView img;
    ProgressBar PB;
    EditText TDname, TDage,TDdob,TDadd,TDemail, TDphone, TDall;
    RadioButton maleD, femaleD,bloodAP,bloodAN,bloodBP,bloodBN,bloodABP,bloodABN,bloodOP,bloodON, eatenYes,eatenNo,sleptYes,sleptNo,drugYes,drugNo,medicineYes,medicineNo,donationInLast3yes,donationInLast3no,dangueInLast3Yes,dangueInLast3No, aidsYes,aidsNo,unprotectedYes,unprotectedNo, DentalYes,DentalNo,TattoYes,TattoNo, DogbiteYes,DogbiteNo,PragnentYes,PragnentNo,childlessthan1,childgreaterthan1,AbortionYes,AbortionNo;
    String eat,sleep,drug,medicine,donation3,dangue,aids,unprotected,dental,tatto,dogbite,pragnent,child1,abortion;
    StorageReference storageReference;
    Button uploadbtn1, uploadbtn2;
    Button SubT;
    DatabaseReference dbr;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("TEST_DONOR");

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    String uid ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_test);

        TDname = findViewById(R.id.editText1) ;
        TDage = findViewById(R.id.editText3);
        TDemail = findViewById(R.id.editText2);
        TDphone = findViewById(R.id.editText4) ;
        TDdob= findViewById(R.id.editText6);
        TDadd= findViewById(R.id.editText7);
        TDall = findViewById(R.id.editText5);


        //RadioButton

        maleD = findViewById(R.id.radioMale) ;
        femaleD = findViewById(R.id.radioFemale) ;

        bloodAP = findViewById(R.id.radioBGApos) ;
        bloodAN = findViewById(R.id.radioBGAneg) ;
        bloodBP = findViewById(R.id.radioBGBpos) ;
        bloodBN = findViewById(R.id.radioBGBneg) ;
        bloodABP = findViewById(R.id.radioBGABpos) ;
        bloodABN = findViewById(R.id.radioBGABneg) ;
        bloodOP = findViewById(R.id.radioBGOpos) ;
        bloodON = findViewById(R.id.radioBGOneg) ;

        eatenYes = findViewById(R.id.TestingyesD1);
        eatenNo = findViewById(R.id.TestingnoD1);
        sleptYes = findViewById(R.id.TestingyesD2);
        sleptNo = findViewById(R.id.TestingnoD2);
        drugYes = findViewById(R.id.TestingyesD3);
        drugNo = findViewById(R.id.TestingnoD3);

        medicineYes = findViewById(R.id.TestingyesD4);
        medicineNo = findViewById(R.id.TestingnoD4);

        donationInLast3yes = findViewById(R.id.TestingyesD5);
        donationInLast3no = findViewById(R.id.TestingnoD5);


        aidsYes = findViewById(R.id.TestingyesD11);
        aidsNo = findViewById(R.id.TestingnoD11);

        unprotectedYes = findViewById(R.id.TestingyesD12);
        unprotectedNo = findViewById(R.id.TestingnoD12);

        DentalYes = findViewById(R.id.TestingyesD13);
        DentalNo = findViewById(R.id.TestingnoD13);

        TattoYes = findViewById(R.id.TestingyesD15);
        TattoNo = findViewById(R.id.TestingnoD15);

        DogbiteYes = findViewById(R.id.TestingyesD16);
        DogbiteNo = findViewById(R.id.TestingnoD16);

        PragnentYes = findViewById(R.id.TestingyesD17);
        PragnentNo = findViewById(R.id.TestingnoD17);

        childlessthan1 = findViewById(R.id.TestingyesD18);
        childgreaterthan1 = findViewById(R.id.TestingnoD18);

        AbortionYes = findViewById(R.id.TestingyesD19);
        AbortionNo = findViewById(R.id.TestingnoD19);

        uploadbtn1 = (Button)findViewById(R.id.upload_ButtonD);

        uploadbtn2 = (Button)findViewById(R.id.BloodReportButtonD);

        dbr = FirebaseDatabase.getInstance().getReference().child("DONOR");
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

        SubT = findViewById(R.id.SubmitTD);
        SubT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( validateTName() != true | validateTPhone() != true | validateDAll() != true | validateTAge() != true | validateTDOB() != true | validateTAdd() != true | validateTEmail()!=true)
                {   //
                    return;
                }
//
                String TDonorName = TDname.getText().toString();
                String TDonorPhone = TDphone.getText().toString();
                String TDonorAge = TDage.getText().toString();
                String TDonorDob = TDdob.getText().toString();
                String TDonorAdd = TDadd.getText().toString();
                String TDonorAll = TDall.getText().toString();
//
                String TDonorEmail = TDemail.getText().toString();
                if (eatenYes.isChecked())
                {
                    eat = eatenYes.getText().toString();
                }
                else if (eatenNo.isChecked())
                {
                    eat = eatenNo.getText().toString();
                }
//
                if (sleptYes.isChecked())
                {
                    sleep = sleptYes.getText().toString();
                }
                else if (sleptNo.isChecked())
                {
                    sleep = sleptNo.getText().toString();
                }

                if (drugYes.isChecked())
                {
                    drug = drugYes.getText().toString();
                }
                else if (drugNo.isChecked())
                {
                    drug = drugNo.getText().toString();
                }

                if (medicineYes.isChecked())
                {
                    medicine = medicineYes.getText().toString();
                }
                else if (medicineNo.isChecked())
                {
                    medicine = medicineNo.getText().toString();
                }

                if (donationInLast3yes.isChecked())
                {
                    donation3 = donationInLast3yes.getText().toString();
                }
                else if (donationInLast3no.isChecked())
                {
                    donation3 = donationInLast3no.getText().toString();
                }
//
               /* if (dangueInLast3Yes.isChecked())
                {
                    dangue = dangueInLast3Yes.getText().toString();
                }
                else if (dangueInLast3No.isChecked())
                {
                    dangue = dangueInLast3No.getText().toString();
                }

                */
//
                if (aidsYes.isChecked())
                {
                    aids = aidsYes.getText().toString();
                }
                else if (aidsNo.isChecked())
                {
                    aids = aidsNo.getText().toString();
                }
//
                if (unprotectedYes.isChecked())
                {
                    unprotected = unprotectedYes.getText().toString();
                }
                else if (unprotectedNo.isChecked())
                {
                    unprotected = unprotectedNo.getText().toString();
                }

                if (DentalYes.isChecked())
                {
                    dental = DentalYes.getText().toString();
                }
                else if (DentalNo.isChecked())
                {
                    dental = DentalNo.getText().toString();
                }

//
                if (TattoYes.isChecked())
                {
                    tatto = TattoYes.getText().toString();
                }
                else if (TattoNo.isChecked())
                {
                    tatto = TattoNo.getText().toString();
                }
//
                if (DogbiteYes.isChecked())
                {
                    dogbite = DogbiteYes.getText().toString();
                }
                else if (DogbiteNo.isChecked())
                {
                    dogbite = DogbiteNo.getText().toString();
                }

                if (PragnentYes.isChecked())
                {
                    pragnent = PragnentYes.getText().toString();
                }
                else if (PragnentNo.isChecked())
                {
                    pragnent = PragnentNo.getText().toString();
                }

                if (childlessthan1.isChecked())
                {
                    child1 = childlessthan1.getText().toString();
                }
                else if (childgreaterthan1.isChecked())
                {
                    child1 = childgreaterthan1.getText().toString();
                }

                if (AbortionYes.isChecked())
                {
                    abortion = AbortionYes.getText().toString();
                }
                else if (AbortionNo.isChecked())
                {
                    abortion = AbortionNo.getText().toString();
                }

                HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Name", TDonorName);
                userMap.put("Email",TDonorEmail);
                userMap.put("Phone",TDonorPhone);
                userMap.put("Age", TDonorAge);
                userMap.put("DOB", TDonorDob);
                userMap.put("Address", TDonorAdd);
                userMap.put("Allergies",TDonorAll);
                userMap.put("Eat", eat);
                userMap.put("Sleep", sleep);
                userMap.put("Drug", drug);
                userMap.put("Medicine", medicine);
                userMap.put("DonatedInLast3Months", donation3);
                //userMap.put("Dengue/Malaria", dangue);
                userMap.put("AIDS", aids);
                userMap.put("Unprotected_Sex", unprotected);
                //userMap.put("Dental/Operation", dental);
                userMap.put("Tattoo", tatto);
                userMap.put("Dog_Bite", dogbite);
                userMap.put("Pregnant", pragnent);
                userMap.put("Child_Less_Than1_year", child1);
                userMap.put("Abortion", abortion);

                user = FirebaseAuth.getInstance().getCurrentUser();
                uid = user.getUid();
                root.child(uid).setValue(userMap);
                Toast.makeText(DonorTest.this,"Done!",Toast.LENGTH_SHORT).show();


                //root.child("TEST").setValue(userMap);
                Toast.makeText(DonorTest.this,"Your test has been submitted now you can wait for your schedule!",Toast.LENGTH_SHORT).show();
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

        StorageReference reference = storageReference.child("DONOR/"+currentTimeMillis()+".pdf");
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
                Toast.makeText(DonorTest.this,"File uploaded",Toast.LENGTH_SHORT).show();
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

    public boolean validateTName() {
        String name = TDname.getText().toString();

        if (name.isEmpty()) {
            TDname.setError("Invalid");
            return false;
        } else {
            TDname.setError(null);
            return true;
        }
    }

    public boolean validateTPhone() {
        String name = TDphone.getText().toString();

        if (name.isEmpty()) {
            TDphone.setError("Invalid");
            return false;
        } else {
            TDphone.setError(null);
            return true;
        }
    }

    public boolean validateTAge() {
        String age = TDage.getText().toString();

        if (age.isEmpty()) {
            TDage.setError("Invalid");
            return false;
        } else {
            TDage.setError(null);
            return true;
        }
    }

    public boolean validateTDOB() {
        String dob = TDdob.getText().toString();
        if (dob.isEmpty()) {
            TDdob.setError("Invalid");
            return false;
        } else {
            TDdob.setError(null);
            return true;
        }
    }

    public boolean validateTAdd() {
        String add = TDadd.getText().toString();

        if (add.isEmpty()) {
            TDadd.setError("Invalid");
            return false;
        } else {
            TDadd.setError(null);
            return true;
        }
    }

    public boolean validateDAll() {
        String add = TDall.getText().toString();

        if (add.isEmpty()) {
            TDall.setError("Invalid");
            return false;
        } else {
            TDall.setError(null);
            return true;
        }
    }

    public boolean validateTEmail() {
        String Re = TDemail.getText().toString();
        if (Re.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Re).matches()) {
            TDemail.setError("Invalid Email Id");
            return false;
        } else {
            TDemail.setError(null);
            return true;
        }
    }
}