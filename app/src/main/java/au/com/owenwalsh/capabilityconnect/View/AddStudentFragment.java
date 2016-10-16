package au.com.owenwalsh.capabilityconnect.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import au.com.owenwalsh.capabilityconnect.Database.StudentLogic;
import au.com.owenwalsh.capabilityconnect.Model.Student;
import au.com.owenwalsh.capabilityconnect.R;

public class AddStudentFragment extends Fragment {
    private StudentLogic studentLogic;
    private Student student;

    private long feedback;
    private EditText input_firstName;
    private EditText input_lastName;
    private EditText input_email;
    private EditText input_zID;
    private Button btn_addStudent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_add_student, container, false);
        input_firstName = (EditText) v.findViewById(R.id.input_first_name);
        input_lastName = (EditText) v.findViewById(R.id.input_last_name);
        input_email = (EditText) v.findViewById(R.id.input_email);
        input_zID = (EditText) v.findViewById(R.id.input_zid);
        btn_addStudent = (Button) v.findViewById(R.id.btn_add_student);



        btn_addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
           }
        });
        return v;
    }

   private void addStudent() {
        if(!validateStudent()){
            addStudentFailed();
            return;
        }
        btn_addStudent.setEnabled(false);
        //add logic here for adding student
        String zID = input_zID.getText().toString();
        String firstName = input_firstName.getText().toString();
        String lastName = input_lastName.getText().toString();
        String email = input_email.getText().toString();
        student = new Student(zID, firstName,lastName, email);
        studentLogic = new StudentLogic(getContext());
        feedback = studentLogic.insertStudent(student);
        if(feedback > 0){
            addStudentSuccessfull();
        }else{
            addStudentFailed();
        }
    }

    private void addStudentFailed() {
        Toast.makeText(getContext(), "woops something went wrong! Please try Again.", Toast.LENGTH_SHORT).show();
        btn_addStudent.setEnabled(true);
    }

    private void addStudentSuccessfull(){
        Toast.makeText(getContext(), "Student added successfully!", Toast.LENGTH_SHORT).show();
        btn_addStudent.setEnabled(true);
    }

    private boolean validateStudent() {
        boolean validated = true;
        //validation logic here
        String firstName = input_firstName.getText().toString();
        String lastName = input_lastName.getText().toString();
        String email = input_email.getText().toString();

        if (firstName.isEmpty()) {
            input_firstName.setError("First name cannot be empty");
            validated = false;
        } else {
                input_firstName.setError(null);
            }
        if (lastName.isEmpty()){
            input_lastName.setError("Last name cannot be empty");
            validated = false;
        } else {
            input_lastName.setError(null);
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("Email is not valid");
            validated = false;
        } else {
            input_email.setError(null);
        }
        return validated;
    }
}


