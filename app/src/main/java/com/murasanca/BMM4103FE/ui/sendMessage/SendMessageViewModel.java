 // Murat Sancak
// 201913709082

package com.murasanca.BMM4103FE.ui.sendMessage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendMessageViewModel extends ViewModel
{
	
	private final MutableLiveData<String> mText;
	
	public SendMessageViewModel()
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