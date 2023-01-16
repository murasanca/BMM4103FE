 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.murasanca.BMM4103FE.databinding.FragmentAddMembersToGroupBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddMembers2GroupFragment extends Fragment
{
	private FragmentAddMembersToGroupBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		binding=FragmentAddMembersToGroupBinding.inflate(inflater,container,false);
		View root=binding.getRoot();
		
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
		DatabaseReference groupsDatabaseReference=firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("Groups");
		
		ArrayList<ContactClass>contactClassArrayList=new ArrayList<>();
		RecyclerView contactsRecyclerView=binding.contactRecyclerView;
		contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		ContactAdapter contactAdapter=new ContactAdapter(root.getContext().getApplicationContext(),contactClassArrayList);
		contactsRecyclerView.setAdapter(contactAdapter);
		
		ActivityResultLauncher<String>activityResultLauncher=registerForActivityResult
		(
			new ActivityResultContracts.RequestPermission(),
			callback->
			{
				 try(Cursor cursor=requireActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null))
				 {
					 while(cursor.moveToNext())
					 {
						 int contactIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME), phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
						
						 if(-1<contactIndex&&-1<phoneIndex)
						 {
							 contactClassArrayList.add(new ContactClass(cursor.getString(contactIndex),cursor.getString(phoneIndex)));
							
							 contactAdapter.notifyItemInserted(contactClassArrayList.size()-1);
						 }
					 }
				 }
			}
		);
		
		if
		(
			Build.VERSION_CODES.M<=Build.VERSION.SDK_INT&&
			PackageManager.PERMISSION_DENIED==requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS)
		)
			 activityResultLauncher.launch(Manifest.permission.READ_CONTACTS);
		 else
		 {
			 Cursor cursor=requireActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
			 while(cursor.moveToNext())
			 {
				 int
						 contactIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME),
						 phoneIndex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
				
				 if (-1 <contactIndex&&-1<phoneIndex)
				 {
					 contactClassArrayList.add(new ContactClass(cursor.getString(contactIndex), cursor.getString(phoneIndex)));
					
					 contactAdapter.notifyItemInserted(contactClassArrayList.size()-1);
				 }
			 }
			 cursor.close();
		 }
		
		 ArrayList<GroupClass> groupClassArrayList=new ArrayList<>();
		 RecyclerView groupsRecyclerView=binding.groupRecyclerView;
		 groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
		 GroupAdapter groupAdapter=new GroupAdapter(root.getContext().getApplicationContext(),groupsDatabaseReference,firebaseAuth,firebaseDatabase,groupClassArrayList);
		 groupsRecyclerView.setAdapter(groupAdapter);
		
		 groupsDatabaseReference.addChildEventListener
		(
			new ChildEventListener()
			{
				 @Override
				 public void onChildAdded(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 groupClassArrayList.add(new GroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
					
					 groupAdapter.notifyItemInserted(groupClassArrayList.size());
				 }
				
				 @Override
				 public void onChildChanged(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 int index=-1;
					 for(int i=0;i<groupClassArrayList.size();++i)
						 if(groupClassArrayList.get(i).getName().equals(snapshot.getKey()))
						 {
							 index=i;
							 break;
						 }
					 if(-1!=index)
					 {
						 groupClassArrayList.set(index,new GroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
						
						 groupAdapter.notifyItemChanged(index);
					 }
				 }
				
				 @Override
				 public void onChildRemoved(@NonNull DataSnapshot snapshot)
				 {
					 int index=-1;
					 for(int i=0;i<groupClassArrayList.size();++i)
						 if(groupClassArrayList.get(i).getName().equals(snapshot.getKey()))
						 {
							 index=i;
							 break;
						 }
					 if(-1!=index)
					 {
						 groupClassArrayList.remove(index);
						
						 groupAdapter.notifyItemRemoved(index);
					 }
				 }
				
				 @Override
				 public void onChildMoved(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 int fromPosition=groupClassArrayList.indexOf(new GroupClass(snapshot.getKey(),null));
					 if(-1!=fromPosition)
					 {
						 GroupClass groupClass=groupClassArrayList.get(fromPosition);
						 groupClassArrayList.remove(fromPosition);
						 int toPosition=1+fromPosition;
						 groupClassArrayList.add(toPosition,groupClass);
						
						 groupAdapter.notifyItemMoved(fromPosition, toPosition);
					 }
				 }
				
				 @Override
				 public void onCancelled(@NonNull DatabaseError error)
				 {
					 Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
				 }
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