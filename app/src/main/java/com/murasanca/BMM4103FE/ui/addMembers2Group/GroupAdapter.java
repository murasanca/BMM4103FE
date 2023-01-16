 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.murasanca.BMM4103FE.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder>
{
	Context context;
	DatabaseReference groupsDatabaseReference;
	FirebaseAuth firebaseAuth;
	FirebaseDatabase firebaseDatabase;
	
	List<GroupClass> groupClassList;
	
	public GroupAdapter(Context context,DatabaseReference groupsDatabaseReference,FirebaseAuth firebaseAuth,FirebaseDatabase firebaseDatabase,List<GroupClass> groupClassList)
	{
		this.context=context;
		this.firebaseAuth=firebaseAuth;
		this.firebaseDatabase=firebaseDatabase;
		this.groupClassList=groupClassList;
		this.groupsDatabaseReference=groupsDatabaseReference;
	}
	
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.group_selectable,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull GroupViewHolder holder,int position)
	{
		holder.description.setText(groupClassList.get(position).getDescription());
		holder.imageView.setImageResource(R.drawable.ic_round_groups_24);
		holder.name.setText(groupClassList.get(position).getName());
		
		holder.itemView.setOnClickListener
		(
			v->
			{
				if(groupClassList.get(position).getSelected())
				{
					ContactAdapter.databaseReferenceList.add(groupsDatabaseReference.child(holder.name.getText().toString()));
					
					holder.itemView.setBackgroundResource(R.drawable.bmm4103fe);
					groupClassList.get(position).setSelected(false);
				}
				else //if(!groupClassList.get(position).getSelected())
				{
					ContactAdapter.databaseReferenceList.remove(groupsDatabaseReference.child(holder.name.getText().toString()));
					
					holder.itemView.setBackgroundResource(R.color.blue);
					groupClassList.get(position).setSelected(true);
				}
			}
		);
	}
	
	@Override
	public int getItemCount()
	{
		return groupClassList.size();
	}
}

 // 201913709082
// Murat Sancak