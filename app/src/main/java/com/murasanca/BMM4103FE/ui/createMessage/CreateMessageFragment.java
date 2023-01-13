 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createMessage;

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
import com.murasanca.BMM4103FE.MessageAdapter;
import com.murasanca.BMM4103FE.MessageClass;
import com.murasanca.BMM4103FE.R;
import com.murasanca.BMM4103FE.databinding.FragmentCreateMessageBinding;

import java.util.ArrayList;
import java.util.Objects;

 public class CreateMessageFragment extends Fragment
{
	private FragmentCreateMessageBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		CreateMessageViewModel createMessageViewModel=new ViewModelProvider(this).get(CreateMessageViewModel.class);
		
		binding=FragmentCreateMessageBinding.inflate(inflater,container,false);
		View root=binding.getRoot();
		
		FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
		FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
		DatabaseReference messagesDatabaseReference=firebaseDatabase.getReference("Users").child(Objects.requireNonNull(firebaseAuth.getUid())).child("Messages");
		
		ArrayList<MessageClass>messageClassArrayList=new ArrayList<>();
		RecyclerView messagesRecyclerView=binding.messagesRecyclerView;
		messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		MessageAdapter messageAdapter=new MessageAdapter(root.getContext().getApplicationContext(),messageClassArrayList);
		messagesRecyclerView.setAdapter(messageAdapter);
		
		messagesDatabaseReference.addChildEventListener
		(
			new ChildEventListener()
			{
				@Override
				public void onChildAdded(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				{
					messageClassArrayList.add(new MessageClass(snapshot.getKey(),snapshot.child("Message").getValue(String.class)));
					
					messageAdapter.notifyItemInserted(messageClassArrayList.size());
				}
				
				@Override
				public void onChildChanged(@NonNull DataSnapshot snapshot,@Nullable String previousChildName)
				{
					int index=-1;
					for(int i=0;i<messageClassArrayList.size();++i)
						if(messageClassArrayList.get(i).getName().equals(snapshot.getKey()))
						{
							index=i;
							break;
						}
					if(-1!=index)
					{
						messageClassArrayList.set(index,new MessageClass(snapshot.getKey(),snapshot.child("Message").getValue(String.class)));
						
						messageAdapter.notifyItemChanged(index);
					}
				}
				
				@Override
				public void onChildRemoved(@NonNull DataSnapshot snapshot)
				{
					int index=-1;
					for(int i=0;i<messageClassArrayList.size();++i)
						if(messageClassArrayList.get(i).getName().equals(snapshot.getKey()))
						{
							index=i;
							break;
						}
					if(-1!=index)
					{
						messageClassArrayList.remove(index);
						
						messageAdapter.notifyItemRemoved(index);
					}
				}
				
				@Override
				public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
				{
					int fromPosition=messageClassArrayList.indexOf(new MessageClass(snapshot.getKey(),null));
					if(-1!=fromPosition)
					{
						MessageClass messageClass=messageClassArrayList.get(fromPosition);
						messageClassArrayList.remove(fromPosition);
						int toPosition=1+fromPosition;
						messageClassArrayList.add(toPosition,messageClass);
						
						messageAdapter.notifyItemMoved(fromPosition, toPosition);
					}
				}
				
				@Override
				public void onCancelled(@NonNull DatabaseError error)
				{
					Toast.makeText(root.getContext(),error.toString(),Toast.LENGTH_SHORT).show();
				}
			}
		);
		
		Button messageButton=binding.messageButton;
		EditText
				messageEditText=binding.messageEditText,
				messageNameEditText=binding.messageNameEditText;
		
		messageButton.setOnClickListener
		(
			v->
			{
				String
						message=messageEditText.getText().toString(),
						messageName=messageNameEditText.getText().toString();
				
				if(!messageName.isEmpty())
					if(messageButton.getText().equals(getResources().getText(R.string.deleteMessageButton)))
						messagesDatabaseReference. child(messageName).removeValue();
					else //if(messageButton.getText().equals(getResources().getText(R.string.createMessageButton))||messageButton.getText().equals(getResources().getText(R.string.updateMessageButton)))
						messagesDatabaseReference.child(messageName).child("Message").setValue(message);
					
				messageEditText.setText(null);
				messageNameEditText.setText(null);
			}
		);
		
		messageEditText.addTextChangedListener
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
						for(MessageClass messageClass:messageClassArrayList)
							if(messageClass.getName().equals(messageNameEditText.getText().toString()))
							{
								messageButton.setText(getResources().getText(R.string.deleteMessageButton));
								messageButton.setBackgroundColor(getResources().getColor(R.color.red));
								
								break;
							}
							else
							{
								messageButton.setText(R.string.createMessageButton);
								messageButton.setBackgroundColor(getResources().getColor(R.color.green));
							}
					else //if(!s.toString().isEmpty())
						for(MessageClass messageClass:messageClassArrayList)
							if(messageClass.getName().equals(messageNameEditText.getText().toString()))
							{
								messageButton.setText(R.string.updateMessageButton);
								messageButton.setBackgroundColor(getResources().getColor(R.color.blue));
								
								break;
							}
							else
							{
								messageButton.setText(R.string.createMessageButton);
								messageButton.setBackgroundColor(getResources().getColor(R.color.green));
							}
				}
			}
		);
		
		messageNameEditText.addTextChangedListener
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
					{
						messageButton.setText(R.string.createMessageButton);
						messageButton.setBackgroundColor(getResources().getColor(R.color.green));
					}
					else
						for(MessageClass messageClass:messageClassArrayList)
							if(messageClass.getName().equals(s.toString()))
								if(messageEditText.getText().toString().isEmpty())
								{
									messageButton.setText(R.string.deleteMessageButton);
									messageButton.setBackgroundColor(getResources().getColor(R.color.red));
									
									break;
								}
								else
								{
									messageButton.setText(R.string.updateMessageButton);
									messageButton.setBackgroundColor(getResources().getColor(R.color.blue));
								}
							else
							{
								messageButton.setText(R.string.createMessageButton);
								messageButton.setBackgroundColor(getResources().getColor(R.color.green));
							}
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