 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.murasanca.BMM4103FE.databinding.ActivityNavigationDrawerBinding;

public class NavigationDrawerActivity extends AppCompatActivity
{
	
	private AppBarConfiguration mAppBarConfiguration;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags
		(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		);
		
		com.murasanca.BMM4103FE.databinding.ActivityNavigationDrawerBinding binding=ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		setSupportActionBar(binding.appBarNavigationDrawer.toolbar);
		DrawerLayout drawer=binding.drawerLayout;
		NavigationView navigationView=binding.navView;
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		mAppBarConfiguration=new AppBarConfiguration.Builder
				(
						R.id.nav_addMembers2Group,
						R.id.nav_createGroup,
						R.id.nav_createMessage,
						R.id.nav_sendMessage,
						R.id.nav_logout
				).setOpenableLayout(drawer).build();
		NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment_content_navigation_drawer);
		NavigationUI.setupActionBarWithNavController(this,navController,mAppBarConfiguration);
		NavigationUI.setupWithNavController(navigationView,navController);
		
		navigationView.setNavigationItemSelectedListener
		(
			item ->
			{
				if(item.getItemId()==R.id.nav_logout)
				{
					FirebaseAuth.getInstance().signOut();
					
					Intent NavigationDrawerActivity2MainActivity=new Intent(NavigationDrawerActivity.this,MainActivity.class);
					NavigationDrawerActivity2MainActivity.putExtra("mainFrameLayoutVisibility",View.VISIBLE);
					NavigationDrawerActivity2MainActivity.putExtra("splashScreenFrameLayoutVisibility",View.GONE);
					
					startActivity(NavigationDrawerActivity2MainActivity);
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					
					return false;
				}
				return NavigationUI.onNavDestinationSelected(item,navController);
			}
		);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_drawer,menu);
		return true;
	}
	
	@Override
	public boolean onSupportNavigateUp()
	{
		NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment_content_navigation_drawer);
		return NavigationUI.navigateUp(navController,mAppBarConfiguration)||super.onSupportNavigateUp();
	}
}

 // 201913709082
// Murat Sancak