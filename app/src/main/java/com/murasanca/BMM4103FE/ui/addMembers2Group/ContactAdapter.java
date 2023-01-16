 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

 import android.content.Context;
 import android.view.LayoutInflater;
 import android.view.ViewGroup;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.RecyclerView;

 import com.google.firebase.database.DatabaseReference;
 import com.murasanca.BMM4103FE.R;

 import java.util.ArrayList;
 import java.util.List;
 
public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder>
{
	Context context;
	List<ContactClass>contactClassList;
	
	public static List<DatabaseReference>databaseReferenceList=new ArrayList<>();
	
	public ContactAdapter(Context context,List<ContactClass>contactClassList)
	{
		this.context=context;
		this.contactClassList=contactClassList;
	}
	
	@NonNull
	@Override
	public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_selectable,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull ContactViewHolder holder,int position)
	{
		holder.contact.setText(contactClassList.get(position).getContact());
		holder.imageView.setImageResource(R.drawable.round_contact_phone_24);
		holder.phone.setText(contactClassList.get(position).getPhone());
		
		if(10>position)
			holder.position.setText("0".concat(String.valueOf(1+position)));
		else
			holder.position.setText(String.valueOf(1+position));
		
		holder.itemView.setOnClickListener
		(
			v->
			{
				if(contactClassList.get(position).getSelected())
				{
					for(DatabaseReference databaseReference:databaseReferenceList)
						databaseReference.child("Members").child(holder.phone.getText().toString().trim()).setValue(false);
					
					contactClassList.get(position).setSelected(false);
					holder.itemView.setBackgroundResource(R.drawable.bmm4103fe);
				}
				else //if(!contactClassList.get(position).getSelected())
				{
					for(DatabaseReference databaseReference:databaseReferenceList)
						databaseReference.child("Members").child(holder.phone.getText().toString().trim()).setValue(true);
					
					contactClassList.get(position).setSelected(true);
					holder.itemView.setBackgroundResource(R.color.blue);
				}
			}
		);
	}
	
	@Override
	public int getItemCount()
	{
		return contactClassList.size();
	}
}

 // 201913709082
// Murat Sancak