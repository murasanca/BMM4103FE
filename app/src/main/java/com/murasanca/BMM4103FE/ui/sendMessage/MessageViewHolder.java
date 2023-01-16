package com.murasanca.BMM4103FE.ui.sendMessage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.murasanca.BMM4103FE.R;

public class MessageViewHolder extends RecyclerView.ViewHolder
{
	TextView message,name;
	
	public MessageViewHolder(@NonNull View itemView)
	{
		super(itemView);
		
		message=itemView.findViewById(R.id.mTextView);
		name=itemView.findViewById(R.id.mNameTextView);
	}
}