package com.craveretailtestproject.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ChatViewModel : ViewModel(){

    val _mTitle = MutableLiveData<String>()


     val _mBodyDescrp = MutableLiveData<String>()

    var callBackMessages : SendMessage? =  null


   open  fun onSendButtonClicked(){
       callBackMessages?.onMessageSend()

    }


}