 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

 import android.view.View;
 import android.widget.ImageView;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.RecyclerView;

 import com.google.firebase.database.DatabaseReference;
 import com.murasanca.BMM4103FE.R;

 public class ContactViewHolder extends RecyclerView.ViewHolder
{
	ImageView imageView;
	TextView contact,phone,position;
	
	public ContactViewHolder(@NonNull View itemView)
	{
		super(itemView);
		
		contact=itemView.findViewById(R.id.contactTextView);
		imageView=itemView.findViewById(R.id.contactImageView);
		phone=itemView.findViewById(R.id.phoneTextView);
		position=itemView.findViewById(R.id.contactPositionTextView);
	}
}

 // 201913709082
// Murat Sancak