package com.example.insertgetdeletdata;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    EditText email,name,address,phone,age,yoursalf,birthday;
    LottieAnimationView loading;
    TextView submit;

    ImageView calender;

    RadioGroup rediogroopRegisterGendr;
    RadioButton redioButtonRegisterGenderSelected,radio_male,radio_female;

    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        yoursalf = findViewById(R.id.yoursalf);
        birthday = findViewById(R.id.birthday);
        loading = findViewById(R.id.loading);
        submit = findViewById(R.id.submit);
        calender = findViewById(R.id.calender);
        radio_male = findViewById(R.id.radio_male);
        radio_female = findViewById(R.id.radio_female);

        rediogroopRegisterGendr = findViewById(R.id.gender_groop);
        rediogroopRegisterGendr.clearCheck();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");


        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int date = calendar.get(calendar.DAY_OF_MONTH);
                int manth = calendar.get(calendar.MONTH);
                int year = calendar.get(calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String s_day = String.valueOf(dayOfMonth);
                                int i = month+1;
                                String s_manth = String.valueOf(i);
                                String s_year = String.valueOf(year);

                                if (s_day.length()==1){
                                    s_day="0"+s_day;
                                }
                                if (s_manth.length()==1){
                                    s_manth = "0"+s_manth;
                                }

                                birthday.setText(s_day+"/"+s_manth+"/"+year);



                            }
                        },

                        year,
                        manth,
                        date
                );
                datePickerDialog.show();
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int date = calendar.get(calendar.DAY_OF_MONTH);
                int manth = calendar.get(calendar.MONTH);
                int year = calendar.get(calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String s_day = String.valueOf(dayOfMonth);
                                int i = month+1;
                                String s_manth = String.valueOf(i);
                                String s_year = String.valueOf(year);

                                if (s_day.length()==1){
                                    s_day="0"+s_day;
                                }
                                if (s_manth.length()==1){
                                    s_manth = "0"+s_manth;
                                }

                                birthday.setText(s_day+"/"+s_manth+"/"+year);



                            }
                        },

                        year,
                        manth,
                        date
                );
                datePickerDialog.show();

            }
        });


        radio_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rediogroopRegisterGendr.setBackgroundResource(R.drawable.bottom_shade);
            }
        });

        radio_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rediogroopRegisterGendr.setBackgroundResource(R.drawable.bottom_shade);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectGenderId = rediogroopRegisterGendr.getCheckedRadioButtonId();
                redioButtonRegisterGenderSelected = findViewById(selectGenderId);

                String s_email = email.getText().toString();
                String s_name = name.getText().toString();
                String s_address  = address.getText().toString();
                String s_phone = phone.getText().toString();
                String s_age = age.getText().toString();
                int gander_id = rediogroopRegisterGendr.getCheckedRadioButtonId();
                String s_gender ;
                String s_birth = birthday.getText().toString();
                String s_about = yoursalf.getText().toString();
                String ID = databaseReference.push().getKey();
                loading.setVisibility(View.VISIBLE);


                if (TextUtils.isEmpty(s_email)){
                    Toast.makeText(MainActivity.this, "Enter Your Email Address", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Email");
                    email.requestFocus();
                    //num,lowe,cou,sim,upe
                }else if (!Patterns.EMAIL_ADDRESS.matcher(s_email).matches()){
                    Toast.makeText(MainActivity.this, "Enter Your  Valied Email Address", Toast.LENGTH_SHORT).show();
                    email.setError("Email");
                    email.requestFocus();
                }else if (TextUtils.isEmpty(s_name)){
                    Toast.makeText(MainActivity.this, "Enter Your Nick Name", Toast.LENGTH_SHORT).show();
                    name.setError("Enter Nick Name");
                    name.requestFocus();
                }else if (TextUtils.isEmpty(s_address)){
                    Toast.makeText(MainActivity.this, "Enter Your Address", Toast.LENGTH_SHORT).show();
                    address.setError("Address");
                    address.requestFocus();
                }else if (TextUtils.isEmpty(s_phone)){
                    Toast.makeText(MainActivity.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    phone.setError("Phone");
                    phone.requestFocus();
                }else if (TextUtils.isEmpty(s_age)){
                    Toast.makeText(MainActivity.this, "Enter Your Age", Toast.LENGTH_SHORT).show();
                    age.setError("Age");
                    age.requestFocus();
                }else if (gander_id==-1){
                    Toast.makeText(MainActivity.this, "Enter select your Gender", Toast.LENGTH_SHORT).show();
                    rediogroopRegisterGendr.setBackgroundResource(R.drawable.error);

                }else if (TextUtils.isEmpty(s_birth)){
                    Toast.makeText(MainActivity.this, "Enter Date Off Birth", Toast.LENGTH_SHORT).show();
                    birthday.setError("Date off birth");
                    birthday.requestFocus();
                }else if (TextUtils.isEmpty(s_about)) {
                    Toast.makeText(MainActivity.this, "About Yourself", Toast.LENGTH_SHORT).show();
                    yoursalf.setError("About Yourself");
                }else {
                    s_gender = redioButtonRegisterGenderSelected.getText().toString();

                    InsertData(s_email,s_name,s_address,s_phone,s_age,s_gender,s_birth,s_about,ID);

                }

            }
        });
    }

    public void InsertData(String s_email, String s_name, String s_address, String s_phone, String s_age, String s_gender, String s_birth, String s_about, String ID){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.child("email").getRef().setValue(s_email);
                snapshot.child("name").getRef().setValue(s_name);
                snapshot.child("address").getRef().setValue(s_address);
                snapshot.child("phone").getRef().setValue(s_phone);
                snapshot.child("age").getRef().setValue(s_age);
                snapshot.child("gender").getRef().setValue(s_gender);
                snapshot.child("birthday").getRef().setValue(s_birth);
                snapshot.child("about").getRef().setValue(s_about);
                Toast.makeText(MainActivity.this, "Data Insert Success", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Somethink working is error", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);

            }
        });

    }
}