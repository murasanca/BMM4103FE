 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.sendMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.murasanca.BMM4103FE.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder>
{
	Context context;
	
	List<GroupClass> groupClassList;
	
	public GroupAdapter(Context context,List<GroupClass> groupClassList)
	{
	    this.context=context;
	    this.groupClassList=groupClassList;
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
				    holder.itemView.setBackgroundResource(R.drawable.bmm4103fe);
			        groupClassList.get(position).setSelected(false);
					
					SendMessageFragment.groupName=null;
				}
				else //if(!groupClassList.get(position).getSelected())
			    {
				    holder.itemView.setBackgroundResource(R.color.blue);
					groupClassList.get(position).setSelected(true);
					
				    SendMessageFragment.groupName=holder.name.getText().toString();
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