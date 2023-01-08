 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags
		(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		);
		
		setContentView(R.layout.activity_home);
		
		FrameLayout
			loginFrameLayout=findViewById(R.id.loginFrameLayout),
			signUpFrameLayout=findViewById(R.id.signUpFrameLayout);
		loginFrameLayout.setVisibility(getIntent().getIntExtra("loginFrameLayoutVisibility",View.VISIBLE));
		signUpFrameLayout.setVisibility(getIntent().getIntExtra("signUpFrameLayoutVisibility",View.GONE));
		
		Intent HomeActivity2NavigationDrawerActivity=new Intent(HomeActivity.this,NavigationDrawerActivity.class);
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		
		EditText
			emailLoginEditText=findViewById(R.id.emailLoginEditText),
			emailSignUpEditText=findViewById(R.id.emailSignUpEditText),
			passwordLoginEditText=findViewById(R.id.passwordLoginEditText),
			passwordSignUpEditText=findViewById(R.id.passwordSignUpEditText);
		
		// Login Button at Login Frame Layout
		findViewById(R.id.loginLoginButton).setOnClickListener
		(
			v->
			{
				if(!(emailLoginEditText.getText()==null&&null==passwordLoginEditText.getText()))
					firebaseAuth.signInWithEmailAndPassword(emailLoginEditText.getText().toString(),passwordLoginEditText.getText().toString()).addOnCompleteListener
					(
						this,task ->
						{
							if(task.isSuccessful())
							{
								startActivity(HomeActivity2NavigationDrawerActivity);
								overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
							}
							else
								Toast.makeText(getApplicationContext(),"Task is not successful.",Toast.LENGTH_SHORT).show();
						}
					);
			}
		);
		
		// Login Button at Sign Up Frame Layout
		findViewById(R.id.loginSignUpButton).setOnClickListener
		(
			v->
			{
				loginFrameLayout.setVisibility(View.VISIBLE);
				signUpFrameLayout.setVisibility(View.GONE);
			}
		);
		
		// Sign Up Button at Login Frame Layout
		findViewById(R.id.signUpLoginButton).setOnClickListener
		(
			v->
			{
				loginFrameLayout.setVisibility(View.GONE);
				signUpFrameLayout.setVisibility(View.VISIBLE);
			}
		);
		
		// Sign Up Button at Sign Up Frame Layout
		findViewById(R.id.signUpSignUpButton).setOnClickListener
		(
			v->firebaseAuth.createUserWithEmailAndPassword(emailSignUpEditText.getText().toString(),passwordSignUpEditText.getText().toString()).addOnCompleteListener
			(
				this,task ->
				{
					if(task.isSuccessful())
					{
						startActivity(HomeActivity2NavigationDrawerActivity);
						overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					}
					else
						Toast.makeText(getApplicationContext(),"Task is not successful.",Toast.LENGTH_SHORT).show();
				}
				
			)
		);
	}
}

 // 201913709082
// Murat Sancak