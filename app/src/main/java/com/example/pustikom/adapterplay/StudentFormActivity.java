package com.example.pustikom.adapterplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by eka on 04/11/16.
 */

public class StudentFormActivity extends AppCompatActivity {
    private AppCompatSpinner genderSpinner;
    private int actionMode;
    private Student student;
    private static final String ADD_MODE="Add Student";
    private static final String EDIT_MODE="Edit Student";
    private EditText nimText, nameText, mailText, phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        genderSpinner = (AppCompatSpinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.gender_array,R.layout.support_simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        Intent intent = getIntent();

        //register Views
        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.floatingSaveButton);
        FloatingActionButton cancelButton = (FloatingActionButton) findViewById(R.id.floatingCancelButton);
        nimText = (EditText) findViewById(R.id.edit_nim);
        nameText = (EditText) findViewById(R.id.edit_nama);
        mailText = (EditText) findViewById(R.id.edit_email);
        phoneText = (EditText) findViewById(R.id.edit_phone);

        //setup listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actionMode = intent.getIntExtra("mode",0);
        switch (actionMode){
            case 0:
                setTitle("Add Student");
                break;
            case 1:
                setTitle("Edit Student");
                //Todo: case edit preload all edit text with passed data
                student = (Student) intent.getSerializableExtra("Student");
                nimText.setText(student.getNoreg());
                nameText.setText(student.getName());
                mailText.setText(student.getMail());
                phoneText.setText(student.getPhone());
                genderSpinner.setSelection(student.getGender());
                break;
        }

        //setup listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = nimText.getText().toString();
                String name = nameText.getText().toString();
                int genderId = genderSpinner.getSelectedItemPosition();
                String mail = mailText.getText().toString();
                String phone = phoneText.getText().toString();
                student = new Student(nim,name,genderId,mail,phone);
                if(validateStudent(student)) {
                    saveStudent(student,actionMode);
                    finish();
                }
            }
        });
    }

    /**
     * Todo: implement validation the criterias are
     * 1. NIM must be all numbers and 8 digits
     * 2. Name must not be empty
     * 3. Any other field are optionals
     * @param student
     * @return true if all field validated, false otherwise
     */
    private boolean validateStudent(Student student){
        //put your code here, set validated to false if input not conformed
        //use nameText.setError("Please input name"); or nimText.setError("NIM must be 8 character"); in case input invalidated
        //change isValidated to false for each error found
        boolean isValidated=true;

        if(student.getNoreg().length()!=10) {
            nimText.setError("NIM must be 10 character");
            isValidated=false;
        }

        if(student.getName().length()==0){
            nameText.setError("Please input name");
            isValidated=false;
        }

        if(student.getMail() != isValidEmail(String email)){
            mailText.setError("Email are not valid");
            isValidated=false;
        }

        if(student.getPhone().length() < 9) {
            phoneText.setError("Phone number must be equal or more than 9 digits");
            isValidated = false;
        }
        return isValidated;
    }
    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Todo: implement save data
     * @param student
     */
    private void saveStudent(Student student,int mode){
        if(mode==0){
            //add current student to global StudentList
            Student.getStudentList().add(student.getId(),student);
        } else{
            Student.getStudentList().set(student.getId(),student);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        //TODO: Only load menu when in edit Mode
        if(actionMode==1)
            inflater.inflate(R.menu.edit_student_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.deleteStudentItem:
                //Todo: Implement action for delete student
                int id=student.getId();
                Student.getStudentList().remove(id);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
