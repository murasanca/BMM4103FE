 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.sendMessage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.murasanca.BMM4103FE.databinding.FragmentSendMessageBinding;

import java.util.ArrayList;
import java.util.Objects;

public class SendMessageFragment extends Fragment
{
	private FragmentSendMessageBinding binding;
	
	public static String
		message=null,
		groupName=null;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		binding=FragmentSendMessageBinding.inflate(inflater,container,false);
		View root=binding.getRoot();
		
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
		DatabaseReference databaseReference=firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid()));
		
		ArrayList<GroupClass>groupClassArrayList=new ArrayList<>();
		RecyclerView groupsRecyclerView=binding.groupRecyclerView;
		groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
		GroupAdapter groupAdapter=new GroupAdapter(root.getContext().getApplicationContext(),groupClassArrayList);
		groupsRecyclerView.setAdapter(groupAdapter);
		
		ArrayList<MessageClass>messageClassArrayList=new ArrayList<>();
		RecyclerView messagesRecyclerView=binding.messagesRecyclerView;
		messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
		MessageAdapter messageAdapter=new MessageAdapter(root.getContext().getApplicationContext(),messageClassArrayList);
		messagesRecyclerView.setAdapter(messageAdapter);
		
		databaseReference.addValueEventListener
		(
			new ValueEventListener()
			{
				@Override
				public void onDataChange(@NonNull DataSnapshot snapshot)
				{
					groupClassArrayList.clear();
					for(DataSnapshot dataSnapshot:snapshot.child("Groups").getChildren())
					{
						groupClassArrayList.add(new GroupClass(dataSnapshot.getKey(),dataSnapshot.child("Description").getValue(String.class)));
						
						groupAdapter.notifyItemInserted(groupClassArrayList.size()-1);
					}
					
					messageClassArrayList.clear();
					for(DataSnapshot dataSnapshot: snapshot.child("Messages").getChildren())
					{
						messageClassArrayList.add(new MessageClass(dataSnapshot.getKey(),dataSnapshot.child("Message").getValue(String.class)));
						messageAdapter.notifyItemInserted(messageClassArrayList.size()-1);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError error)
				{
					Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
				}
			}
		);
		
		if
		(
			Build.VERSION_CODES.M<=Build.VERSION.SDK_INT&&
			PackageManager.PERMISSION_DENIED==requireContext().checkSelfPermission(Manifest.permission.SEND_SMS)
		)
			registerForActivityResult(new ActivityResultContracts.RequestPermission(),callback->{}).launch(Manifest.permission.SEND_SMS);
		
		binding.sendBulkMessageButton.setOnClickListener
		(
			v->
			{
				if(groupName!=null)
					databaseReference.addValueEventListener
					(
						new ValueEventListener()
						{
							@Override
							public void onDataChange(@NonNull DataSnapshot snapshot)
							{
								for(DataSnapshot dataSnapshot:snapshot.child("Groups").child(groupName).child("Members").getChildren())
									if(Boolean.TRUE.equals(dataSnapshot.getValue(Boolean.class)))
										SmsManager.getDefault().sendTextMessage(dataSnapshot.getKey(),null,message,null,null);
							}
							
							@Override
							public void onCancelled(@NonNull DatabaseError error)
							{
								Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
							}
						}
					);
			}
		);
		
		return root;
	}
	
	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		binding=null;
	}
}

 // 201913709082
// Murat Sancak