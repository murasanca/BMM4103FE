// Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectableGroupViewHolder extends RecyclerView.ViewHolder
{
	ImageView imageView;
	TextView description,name;
	
	public SelectableGroupViewHolder(@NonNull View itemView)
	{
		super(itemView);
		
		description=itemView.findViewById(R.id.gDescriptionTextView);
		imageView=itemView.findViewById(R.id.gImageView);
		name=itemView.findViewById(R.id.gNameTextView);
	}
}

// 201913709082
// Murat Sancak