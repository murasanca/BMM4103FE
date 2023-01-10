 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.murasanca.BMM4103FE.databinding.FragmentAddMembersToGroupBinding;

public class addMembers2GroupFragment extends Fragment
{
	private FragmentAddMembersToGroupBinding binding;
	
	public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		addMembers2GroupViewModel addMembers2GroupViewModel=new ViewModelProvider(this).get(addMembers2GroupViewModel.class);
		
		binding=FragmentAddMembersToGroupBinding.inflate(inflater,container,false);
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