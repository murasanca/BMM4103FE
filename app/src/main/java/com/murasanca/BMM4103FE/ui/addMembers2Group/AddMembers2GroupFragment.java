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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.murasanca.BMM4103FE.ContactAdapter;
import com.murasanca.BMM4103FE.ContactClass;
import com.murasanca.BMM4103FE.SelectableGroupAdapter;
import com.murasanca.BMM4103FE.SelectableGroupClass;
import com.murasanca.BMM4103FE.databinding.FragmentAddMembersToGroupBinding;

import java.util.ArrayList;
import java.util.Objects;

public class AddMembers2GroupFragment extends Fragment
{
	private FragmentAddMembersToGroupBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		AddMembers2GroupViewModel addMembers2GroupViewModel=new ViewModelProvider(this).get(AddMembers2GroupViewModel.class);
		
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
				Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&
				requireContext().checkSelfPermission(Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_DENIED
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
		
		 ArrayList<SelectableGroupClass>selectableGroupClassArrayList=new ArrayList<>();
		 RecyclerView selectableGroupsRecyclerView=binding.selectableGroupRecyclerView;
		 selectableGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
		 SelectableGroupAdapter selectableGroupAdapter=new SelectableGroupAdapter(root.getContext().getApplicationContext(),groupsDatabaseReference,firebaseAuth,firebaseDatabase,selectableGroupClassArrayList);
		 selectableGroupsRecyclerView.setAdapter(selectableGroupAdapter);
		
		 groupsDatabaseReference.addChildEventListener
		(
			new ChildEventListener()
			{
				 @Override
				 public void onChildAdded(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 selectableGroupClassArrayList.add(new SelectableGroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
					
					 selectableGroupAdapter.notifyItemInserted(selectableGroupClassArrayList.size());
				 }
				
				 @Override
				 public void onChildChanged(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 int index=-1;
					 for(int i=0;i<selectableGroupClassArrayList.size();++i)
						 if(selectableGroupClassArrayList.get(i).getName().equals(snapshot.getKey()))
						 {
							 index=i;
							 break;
						 }
					 if(-1!=index)
					 {
						 selectableGroupClassArrayList.set(index,new SelectableGroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
						
						 selectableGroupAdapter.notifyItemChanged(index);
					 }
				 }
				
				 @Override
				 public void onChildRemoved(@NonNull DataSnapshot snapshot)
				 {
					 int index=-1;
					 for(int i=0;i<selectableGroupClassArrayList.size();++i)
						 if(selectableGroupClassArrayList.get(i).getName().equals(snapshot.getKey()))
						 {
							 index=i;
							 break;
						 }
					 if(-1!=index)
					 {
						 selectableGroupClassArrayList.remove(index);
						
						 selectableGroupAdapter.notifyItemRemoved(index);
					 }
				 }
				
				 @Override
				 public void onChildMoved(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				 {
					 int fromPosition=selectableGroupClassArrayList.indexOf(new SelectableGroupClass(snapshot.getKey(),null));
					 if(-1!=fromPosition)
					 {
						 SelectableGroupClass selectableGroupClass=selectableGroupClassArrayList.get(fromPosition);
						 selectableGroupClassArrayList.remove(fromPosition);
						 int toPosition=1+fromPosition;
						 selectableGroupClassArrayList.add(toPosition,selectableGroupClass);
						
						 selectableGroupAdapter.notifyItemMoved(fromPosition, toPosition);
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