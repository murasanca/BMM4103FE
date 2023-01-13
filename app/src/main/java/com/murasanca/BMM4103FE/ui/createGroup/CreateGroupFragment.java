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
import java.util.Objects;

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
		DatabaseReference groupsDatabaseReference=firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("Groups");
		
		ArrayList<GroupClass>groupClassArrayList=new ArrayList<>();
		RecyclerView groupsRecyclerView=binding.groupsRecyclerView;
		groupsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		GroupAdapter groupAdapter=new GroupAdapter(root.getContext().getApplicationContext(),groupClassArrayList);
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
		
		Button groupButton=binding.groupButton;
		EditText
				groupDescriptionEditText=binding.groupDescriptionEditText,
				groupNameEditText=binding.groupNameEditText;
		
		groupButton.setOnClickListener
		(
			v->
			{
				String
						groupDescription=groupDescriptionEditText.getText().toString(),
						groupName=groupNameEditText.getText().toString();
				
				if(!groupName.isEmpty())
					if(groupButton.getText().equals(getResources().getText(R.string.deleteGroupButton)))
						groupsDatabaseReference.child(groupName).removeValue();
					else //if(groupButton.getText().equals(getResources().getText(R.string.createGroupButton))||groupButton.getText().equals(getResources().getText(R.string.updateGroupButton)))
						groupsDatabaseReference.child(groupName).child("Description").setValue(groupDescription);
					
				groupDescriptionEditText.setText(null);
				groupNameEditText.setText(null);
			}
		);
		
		groupDescriptionEditText.addTextChangedListener
		(
			new TextWatcher()
			{
				@Override
				public void beforeTextChanged(CharSequence s,int start,int count,int after){}
				
				@Override
				public void onTextChanged(CharSequence s,int start,int before,int count){}
				
				@Override
				public void afterTextChanged(Editable s)
				{
					if(s.toString().isEmpty())
						for(GroupClass groupClass:groupClassArrayList)
							if(groupClass.getName().equals(groupNameEditText.getText().toString()))
							{
								groupButton.setText(getResources().getText(R.string.deleteGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.red));
								
								break;
							}
							else
							{
								groupButton.setText(getResources().getText(R.string.createGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.green));
							}
					else //if(!s.toString().isEmpty())
						for(GroupClass groupClass:groupClassArrayList)
							if(groupClass.getName().equals(groupNameEditText.getText().toString()))
							{
								groupButton.setText(getResources().getText(R.string.updateGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.blue));
								
								break;
							}
							else
							{
								groupButton.setText(getResources().getText(R.string.createGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.green));
							}
				}
			}
		);
		
		groupNameEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s,int start,int count,int after){}
			
			@Override
			public void onTextChanged(CharSequence s,int start,int before,int count){}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if(s.toString().isEmpty())
				{
					groupButton.setText(getResources().getText(R.string.createGroupButton));
					groupButton.setBackgroundColor(getResources().getColor(R.color.green));
				}
				else
					for(GroupClass groupClass:groupClassArrayList)
						if(groupClass.getName().equals(s.toString()))
							if(groupDescriptionEditText.getText().toString().isEmpty())
							{
								groupButton.setText(getResources().getText(R.string.deleteGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.red));
								
								break;
							}
							else
							{
								groupButton.setText(getResources().getText(R.string.updateGroupButton));
								groupButton.setBackgroundColor(getResources().getColor(R.color.blue));
							}
						else
						{
							groupButton.setText(getResources().getText(R.string.createGroupButton));
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