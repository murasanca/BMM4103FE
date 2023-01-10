 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.sendMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.murasanca.BMM4103FE.databinding.FragmentSendMessageBinding;

public class SendMessageFragment extends Fragment
{
	private FragmentSendMessageBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		SendMessageViewModel sendMessageViewModel=new ViewModelProvider(this).get(SendMessageViewModel.class);
		
		binding=FragmentSendMessageBinding.inflate(inflater,container,false);
		View root=binding.getRoot();
		
		return root;
	}
	
	@Override
	public void onDestroyView()
	{
		super.onDestroyView();
		binding=null;
	}
}

 // 201913709082
// Murat Sancak