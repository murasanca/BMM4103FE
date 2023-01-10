 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.addMembers2Group;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMembers2GroupViewModel extends ViewModel
{
	
	private final MutableLiveData<String> mText;
	
	public AddMembers2GroupViewModel()
	{
		mText=new MutableLiveData<>();
		mText.setValue("This is home fragment");
	}
	
	public LiveData<String> getText()
	{
		return mText;
	}
}

 // 201913709082
// Murat Sancak