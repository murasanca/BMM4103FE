package com.murasanca.BMM4103FE.ui.sendMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.murasanca.BMM4103FE.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>
{
	Context context;
	
	List<MessageClass> messageClassList;
	
	public MessageAdapter(Context context,List<MessageClass> messageClassList)
	{
		this.context=context;
		this.messageClassList=messageClassList;
	}
	
	@NonNull
	@Override
	public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.message_selectable,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull MessageViewHolder holder,int position)
	{
		holder.message.setText(messageClassList.get(position).getMessage());
		holder.name.setText(messageClassList.get(position).getName());
		
		holder.itemView.setOnClickListener
		(
			v->
			{
				if(messageClassList.get(position).getSelected())
				{
					holder.itemView.setBackgroundResource(R.drawable.bmm4103fe);
					messageClassList.get(position).setSelected(false);
					
					SendMessageFragment.message=null;
				}
				else //if(!messageClassList.get(position).getSelected())
				{
					holder.itemView.setBackgroundResource(R.color.blue);
					messageClassList.get(position).setSelected(true);
					
					SendMessageFragment.message=holder.message.getText().toString();
				}
			}
		);
	}
	
	@Override
	public int getItemCount()
	{
		return messageClassList.size();
	}
}