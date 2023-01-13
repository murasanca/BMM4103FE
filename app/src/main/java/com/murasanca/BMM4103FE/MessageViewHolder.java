 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE;

 import android.view.View;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.RecyclerView;

 public class MessageViewHolder extends RecyclerView.ViewHolder
{
	TextView message,name,position;
	
	public MessageViewHolder(@NonNull View itemView)
	{
		super(itemView);
		
		message=itemView.findViewById(R.id.mTextView);
		name=itemView.findViewById(R.id.mNameTextView);
		position=itemView.findViewById(R.id.mNumberTextView);
	}
}

 // 201913709082
// Murat Sancak