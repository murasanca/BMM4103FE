 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createGroup;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.murasanca.BMM4103FE.GroupAdapter;
import com.murasanca.BMM4103FE.GroupClass;
import com.murasanca.BMM4103FE.R;
import com.murasanca.BMM4103FE.databinding.FragmentCreateGroupBinding;

import java.util.ArrayList;

 public class CreateGroupFragment extends Fragment
{
	private FragmentCreateGroupBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		CreateGroupViewModel createGroupViewModel=new ViewModelProvider(this).get(CreateGroupViewModel.class);
		
		binding=FragmentCreateGroupBinding.inflate(inflater,container,false);
		View root=binding.getRoot();
		
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
		DatabaseReference groupsDatabaseReference=firebaseDatabase.getReference("Users").child(firebaseAuth.getUid()).child("Groups");
		
		ArrayList<GroupClass>groupsClassArrayList=new ArrayList<>();
		RecyclerView groupsRecyclerView=binding.groupsRecyclerView;
		RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
		groupsRecyclerView.setLayoutManager(layoutManager);
		GroupAdapter groupAdapter=new GroupAdapter(root.getContext().getApplicationContext(),groupsClassArrayList);
		groupsRecyclerView.setAdapter(groupAdapter);
		
		groupsDatabaseReference.addChildEventListener(new ChildEventListener(){
			@Override
			public void onChildAdded(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
			{
				groupsClassArrayList.add(new GroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
				
				groupAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onChildChanged(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
			{
				int index=-1;
				for(int i=0;i<groupsClassArrayList.size();++i)
					if(groupsClassArrayList.get(i).getName().equals(snapshot.getKey()))
					{
						index=i;
						break;
					}
				if(-1!=index)
				{
					groupsClassArrayList.set(index,new GroupClass(snapshot.getKey(),snapshot.child("Description").getValue(String.class)));
					
					groupAdapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onChildRemoved(@NonNull DataSnapshot snapshot)
			{
				int index=-1;
				for(int i=0;i<groupsClassArrayList.size();++i)
					if(groupsClassArrayList.get(i).getName().equals(snapshot.getKey()))
					{
						index=i;
						break;
					}
				if(-1!=index)
				{
					groupsClassArrayList.remove(index);
					
					groupAdapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onChildMoved(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
			{
				int index=groupsClassArrayList.indexOf(new GroupClass(snapshot.getKey(),null));
				if(-1!=index)
				{
					GroupClass groupClass=groupsClassArrayList.get(index);
					groupsClassArrayList.remove(index);
					groupsClassArrayList.add(1+index,groupClass);
					
					groupAdapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError error)
			{
				Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
			}
		});
		
		Button groupButton=binding.groupButton;
		EditText
				groupDescriptionEditText=binding.groupDescriptionEditText,
				groupNameEditText=binding.groupNameEditText;
		
		groupButton.setOnClickListener(v->
		{
			String
					groupDescription=groupDescriptionEditText.getText().toString(),
					groupName=groupNameEditText.getText().toString();
			
			if(!groupName.isEmpty())
				if(groupButton.getText().equals("CREATE GROUP"))
					groupsDatabaseReference.child(groupName).child("Description").setValue(groupDescription);
				else //if(groupButton.getText().equals("DELETE GROUP"))
					groupsDatabaseReference.child(groupName).removeValue();
				
			groupDescriptionEditText.setText(null);
			groupNameEditText.setText(null);
		});
		
		groupNameEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s,int start,int count,int after){}
			
			@Override
			public void onTextChanged(CharSequence s,int start,int before,int count){}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if(!s.toString().isEmpty())
					for(GroupClass groupClass:groupsClassArrayList)
						if(groupClass.getName().equals(s.toString()))
						{
							groupButton.setText("DELETE GROUP");
							groupButton.setBackgroundColor(getResources().getColor(R.color.red));
						}
						else
						{
							groupButton.setText("CREATE GROUP");
							groupButton.setBackgroundColor(getResources().getColor(R.color.green));
						}
			}
		});
		
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