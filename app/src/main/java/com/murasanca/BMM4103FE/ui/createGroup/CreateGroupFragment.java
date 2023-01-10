 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createGroup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.murasanca.BMM4103FE.databinding.FragmentCreateGroupBinding;

public class CreateGroupFragment extends Fragment
{
	private FragmentCreateGroupBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		CreateGroupViewModel createGroupViewModel=new ViewModelProvider(this).get(CreateGroupViewModel.class);
		
		binding=FragmentCreateGroupBinding.inflate(inflater,container,false);
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