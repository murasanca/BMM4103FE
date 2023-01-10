 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.createGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class createGroupViewModel extends ViewModel
{
	
	private final MutableLiveData<String> mText;
	
	public createGroupViewModel()
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