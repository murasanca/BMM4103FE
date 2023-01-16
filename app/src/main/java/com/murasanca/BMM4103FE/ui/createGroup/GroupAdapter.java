 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createGroup;

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
	
	public GroupAdapter(Context context,List<GroupClass>groupClassList)
	{
		this.context=context;
		this.groupClassList=groupClassList;
	}
	
	@NonNull
	@Override
	public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.group,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull GroupViewHolder holder,int position)
	{
		holder.description.setText(groupClassList.get(position).getDescription());
		holder.imageView.setImageResource(R.drawable.ic_round_groups_24);
		holder.name.setText(groupClassList.get(position).getName());
		if(10>position)
			holder.position.setText("0".concat(String.valueOf(1+position)));
		else
			holder.position.setText(String.valueOf(1+position));
	}
	
	@Override
	public int getItemCount()
	{
		return groupClassList.size();
	}
}

 // 201913709082
// Murat Sancak