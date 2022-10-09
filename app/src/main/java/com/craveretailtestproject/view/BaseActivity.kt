package com.craveretailtestproject.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.craveretailtestproject.R
import com.craveretailtestproject.databinding.ActivityMainBinding
import com.craveretailtestproject.model.PusherDataModel
import com.craveretailtestproject.viewmodel.SendMessage
import com.craveretailtestproject.viewmodel.ChatViewModel
import com.google.gson.Gson
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.channel.ChannelEventListener
import com.pusher.client.channel.PusherEvent
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange


class BaseActivity : AppCompatActivity(), SendMessage {
    private lateinit var binding: ActivityMainBinding

    private var options: PusherOptions? = null
    private var pusher: Pusher? = null
    var pusherdata: PusherDataModel? = null
    private var views: View? = null
    private var channel: Channel ?=  null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        views = binding.root
        initview()

    }

    private fun initview() {

        var mainActivityModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        binding.chatviewmodel = mainActivityModel
        mainActivityModel.callBackMessages = this

        supportFragmentManager.beginTransaction().add(R.id.chatFragment, ChatFragment()).commit()
        options = PusherOptions().setCluster(getString(R.string.cluster))
        pusher = Pusher(getString(R.string.pusherapp_key), options)
        pusher!!.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(change: ConnectionStateChange) {
            }

            override fun onError(message: String?, code: String?, e: Exception?) {
                Log.e("some error", e.toString())
            }
        }, ConnectionState.ALL)
        channel   = pusher!!.subscribe(getString(R.string.channel))

    }


    fun fetchDataFrompushtry() {

        channel?.bind(getString(R.string.event), object : ChannelEventListener {
            override fun onEvent(event: PusherEvent) {

                val jsonInfo: String = event!!.data
                val gsons = Gson()
                val info: PusherDataModel = gsons.fromJson(jsonInfo, PusherDataModel::class.java)

                binding!!.chatviewmodel!!._mTitle.postValue(info!!.title)
                binding!!.chatviewmodel!!._mBodyDescrp.postValue(info!!.message)

                pusherdata = info


            }

            override fun onSubscriptionSucceeded(channelName: String?) {
            }
        })


    }

    override fun onMessageSend() {

        fetchDataFrompushtry()
    }


}