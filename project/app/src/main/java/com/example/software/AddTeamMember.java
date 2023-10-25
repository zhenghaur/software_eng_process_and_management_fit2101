package com.example.software;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.software.provider.Members;
import com.example.software.provider.TaskViewModel;

public class AddTeamMember extends AppCompatActivity {

    EditText memberNameInput;
    EditText memberEmailInput;
    Button addMemberButton;

    static TaskViewModel mTeamMemberViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_team_member);
        memberNameInput = findViewById(R.id.memberNameInput);
        memberEmailInput = findViewById(R.id.memberEmailInput);
        addMemberButton = findViewById(R.id.addMemberButton);

        mTeamMemberViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String memberNameInString = memberNameInput.getText().toString();
                String memberEmailInString = memberEmailInput.getText().toString();

                if (memberNameInString.isEmpty() | memberEmailInString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                Members member = new Members(memberNameInString, memberEmailInString);
                mTeamMemberViewModel.addTeamMember(member);

                Toast.makeText(AddTeamMember.this, "Team Member: " + memberNameInString + " has been" +
                        " added.", Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    });
    }
}
