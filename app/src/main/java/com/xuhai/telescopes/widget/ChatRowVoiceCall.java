package com.xuhai.telescopes.widget;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.chat.TextMessageBody;
import com.xuhai.easeui.widget.chatrow.EaseChatRow;
import com.xuhai.telescopes.Constant;

public class ChatRowVoiceCall extends EaseChatRow {

    private TextView contentvView;
    private ImageView iconView;

    public ChatRowVoiceCall(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                    com.xuhai.easeui.R.layout.ease_row_received_voice_call : com.xuhai.easeui.R.layout.ease_row_sent_voice_call, this);
        // 视频通话
        }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                    com.xuhai.easeui.R.layout.ease_row_received_video_call : com.xuhai.easeui.R.layout.ease_row_sent_video_call, this);
        }
    }

    @Override
    protected void onFindViewById() {
        contentvView = (TextView) findViewById(com.xuhai.easeui.R.id.tv_chatcontent);
        iconView = (ImageView) findViewById(com.xuhai.easeui.R.id.iv_call_icon);
    }

    @Override
    protected void onSetUpView() {
        TextMessageBody txtBody = (TextMessageBody) message.getBody();
        contentvView.setText(txtBody.getMessage());
    }
    
    @Override
    protected void onUpdateView() {
        
    }

    @Override
    protected void onBubbleClick() {
        
    }

  

}
