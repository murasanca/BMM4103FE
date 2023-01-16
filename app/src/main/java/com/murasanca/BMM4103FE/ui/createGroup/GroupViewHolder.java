 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createGroup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.murasanca.BMM4103FE.R;

 public class GroupViewHolder extends RecyclerView.ViewHolder
{
	ImageView imageView;
	TextView description,name,position;
	
	public GroupViewHolder(@NonNull View itemView)
	{
		super(itemView);
		
		description=itemView.findViewById(R.id.gDescriptionTextView);
		imageView=itemView.findViewById(R.id.gImageView);
		name=itemView.findViewById(R.id.gNameTextView);
		position=itemView.findViewById(R.id.gNumberTextView);
	}
}

 // 201913709082
// Murat Sancak