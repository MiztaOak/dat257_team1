package com.dat257.team1.LFG.view.messageFeed;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dat257.team1.LFG.R;
import com.dat257.team1.LFG.model.Message;
import com.dat257.team1.LFG.viewmodel.MessageViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MessageFragment extends Fragment {

    private ImageButton createNewMessage;
    private TextInputEditText msg;
    private String chatId;
    private String chatName;
    private MsgAdapter msgAdapter;

    private MessageViewModel messageViewModel;
    private MutableLiveData<List<Message>> messages;
    private MutableLiveData<String> currentChatId;
    private MutableLiveData<Boolean> isMessageSent;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            chatId = bundle.getString("chatId");
            chatName = bundle.getString("chatName");
        }

        msg = view.findViewById(R.id.etxt_chat_message);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(chatName);

        createNewMessage = view.findViewById(R.id.create_message);
        createNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (msg.getText() != null) {
                    messageViewModel.sendMessage(chatId, msg.getText().toString());
                } else {
                    msg.setError("empty");
                }
            }
        });

        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.onCreate();

        currentChatId = messageViewModel.getMutableLiveDataChatId();
        currentChatId.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                messageViewModel.loadChat(s);
            }
        });

        messages = messageViewModel.getMutableLiveDataMessages();
        ListView listView = view.findViewById(R.id.messages_view);
        msgAdapter = new MsgAdapter(getContext(), messages);
        listView.setAdapter(msgAdapter);
        messages.observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                msgAdapter.notifyDataSetChanged();
            }
        });

        isMessageSent = messageViewModel.getMutableLiveDataIsMessageSent();
        isMessageSent.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (msg.getText() != null && aBoolean) {
                            msg.getText().clear();
                            hideSoftKeyboard(getActivity());
                            msg.clearFocus();
                        }
                    }
                }
        );

        messageViewModel.setMutableChatId(chatId);
    }

    @Override
    public void onPause() {
        messageViewModel.cleanup();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onPause();
    }

    @Override
    public void onStop() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        messageViewModel.cleanup();
        super.onStop();
    }
}

