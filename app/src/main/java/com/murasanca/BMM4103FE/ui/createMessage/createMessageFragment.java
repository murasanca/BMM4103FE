 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createMessage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.murasanca.BMM4103FE.databinding.FragmentCreateMessageBinding;

public class createMessageFragment extends Fragment
{
	private FragmentCreateMessageBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		createMessageViewModel createMessageViewModel=new ViewModelProvider(this).get(createMessageViewModel.class);
		
		binding=FragmentCreateMessageBinding.inflate(inflater,container,false);
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