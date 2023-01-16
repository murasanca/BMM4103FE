 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createMessage;

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
	List<MessageClass>messageClassList;
	
	public MessageAdapter(Context context,List<MessageClass> messageClassList)
	{
		this.context=context;
		this.messageClassList=messageClassList;
	}
	
	@NonNull
	@Override
	public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
	{
		return new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.message,parent,false));
	}
	
	@Override
	public void onBindViewHolder(@NonNull MessageViewHolder holder,int position)
	{
		holder.message.setText(messageClassList.get(position).getMessage());
		holder.name.setText(messageClassList.get(position).getName());
		
		if(10>position)
			holder.position.setText("0".concat(String.valueOf(1+position)));
		else
			holder.position.setText(String.valueOf(1+position));
	}
	
	@Override
	public int getItemCount()
	{
		return messageClassList.size();
	}
}

 // 201913709082
// Murat Sancak