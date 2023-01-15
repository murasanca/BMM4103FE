// Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SelectableGroupAdapter extends RecyclerView.Adapter<SelectableGroupViewHolder>
{
	Context context;
	DatabaseReference groupsDatabaseReference;
	FirebaseAuth firebaseAuth;
	FirebaseDatabase firebaseDatabase;
	
	List<SelectableGroupClass>selectableGroupClassList;
	
	public SelectableGroupAdapter(Context context,DatabaseReference groupsDatabaseReference,FirebaseAuth firebaseAuth,FirebaseDatabase firebaseDatabase,List<SelectableGroupClass>selectableGroupClassList)
	{
		this.context=context;
		this.firebaseAuth=firebaseAuth;
		this.firebaseDatabase=firebaseDatabase;
		this.selectableGroupClassList=selectableGroupClassList;
		this.groupsDatabaseReference=groupsDatabaseReference;
	}
	
	@NonNull
	@Override
	public SelectableGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new SelectableGroupViewHolder(LayoutInflater.from(context).inflate(R.layout.group_selectable,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull SelectableGroupViewHolder holder,int position)
	{
		holder.description.setText(selectableGroupClassList.get(position).getDescription());
		holder.imageView.setImageResource(R.drawable.ic_round_groups_24);
		holder.name.setText(selectableGroupClassList.get(position).getName());
		
		holder.itemView.setOnClickListener
		(
			v->
			{
				if(selectableGroupClassList.get(position).getSelected())
				{
					ContactAdapter.databaseReferenceList.add(groupsDatabaseReference.child(holder.name.getText().toString()));
					
					holder.itemView.setBackgroundResource(R.color.blue);
					selectableGroupClassList.get(position).setSelected(false);
				}
				else //if(!selectableGroupClassList.get(position).getSelected())
				{
					ContactAdapter.databaseReferenceList.remove(groupsDatabaseReference.child(holder.name.getText().toString()));
					
					holder.itemView.setBackgroundResource(R.drawable.bmm4103fe);
					selectableGroupClassList.get(position).setSelected(true);
				}
			}
		);
	}
	
	@Override
	public int getItemCount()
	{
		return selectableGroupClassList.size();
	}
}

// 201913709082
// Murat Sancak