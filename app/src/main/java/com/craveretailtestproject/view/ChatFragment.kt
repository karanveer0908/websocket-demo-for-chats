package com.craveretailtestproject.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.craveretailtestproject.databinding.ActivityChatBinding
import com.craveretailtestproject.viewmodel.ChatViewModel
import com.craveretailtestproject.viewmodel.SendMessage
import com.google.android.material.snackbar.Snackbar


class ChatFragment : Fragment()  {

    private lateinit var binding: ActivityChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initview()
    }

    private fun initview() {

        var chat = ViewModelProvider(requireActivity())
            .get(ChatViewModel::class.java)
        binding.chatviewmodel = chat
        chat._mTitle.observe(viewLifecycleOwner, binding.tvTitile::setText)
        chat._mBodyDescrp.observe(viewLifecycleOwner, binding.tvDescription::setText)

    }



}