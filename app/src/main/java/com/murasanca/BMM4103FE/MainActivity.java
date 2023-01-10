 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
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
		
		setContentView(R.layout.activity_main);
		
		Intent
			MainActivity2HomeActivity=new Intent(MainActivity.this,HomeActivity.class),
			MainActivity2NavigationDrawerActivity=new Intent(MainActivity.this,NavigationDrawerActivity.class);
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		FrameLayout
			mainFrameLayout=findViewById(R.id.mainFrameLayout),
			splashScreenFrameLayout=findViewById(R.id.splashScreenFrameLayout);
		mainFrameLayout.setVisibility(getIntent().getIntExtra("mainFrameLayoutVisibility",View.GONE));
		splashScreenFrameLayout.setVisibility(getIntent().getIntExtra("splashScreenFrameLayoutVisibility",View.VISIBLE));
		
		//firebaseAuth.signOut();
		
		if(firebaseAuth.getCurrentUser()==null)
		{
			new Handler().postDelayed
			(
				()->
				{
					splashScreenFrameLayout.setVisibility(View.GONE);
					mainFrameLayout.setVisibility(View.VISIBLE);
				},
				2048
			);
		}
		else //if(firebaseAuth.getCurrentUser()!=null)
		{
			new Handler().postDelayed
			(
				()->
				{
					startActivity(MainActivity2NavigationDrawerActivity);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				},
				2048
			);
		}
		
		findViewById(R.id.loginMainButton).setOnClickListener
		(
			v->
			{
				MainActivity2HomeActivity.putExtra("loginFrameLayoutVisibility",View.VISIBLE);
				MainActivity2HomeActivity.putExtra("signUpFrameLayoutVisibility",View.GONE);
				startActivity(MainActivity2HomeActivity);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		);
		
		findViewById(R.id.signUpMainButton).setOnClickListener
		(
			v->
			{
				MainActivity2HomeActivity.putExtra("loginFrameLayoutVisibility",View.GONE);
				MainActivity2HomeActivity.putExtra("signUpFrameLayoutVisibility",View.VISIBLE);
				startActivity(MainActivity2HomeActivity);
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		);
	}
}

 // 201913709082
// Murat Sancak