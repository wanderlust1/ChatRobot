package com.wanderlust.chattingrobot_mvp.view.implement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.wanderlust.chattingrobot_mvp.R;
import com.wanderlust.chattingrobot_mvp.model.beans.TextBean;
import com.wanderlust.chattingrobot_mvp.view.IChatView;
import com.wanderlust.chattingrobot_mvp.view.adapters.ChatAdapter;
import com.wanderlust.chattingrobot_mvp.presenter.implement.ChatPresenterImpl;
import com.wanderlust.chattingrobot_mvp.presenter.IChatPresenter;
import com.wanderlust.chattingrobot_mvp.utils.ItemSpaceDecoration;
import com.wanderlust.chattingrobot_mvp.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wanderlust.chattingrobot_mvp.model.beans.TextBean.TYPE_SEND;
import static com.wanderlust.chattingrobot_mvp.model.beans.TextBean.TYPE_RECEIVE;

public class ChatActivity extends AppCompatActivity implements IChatView, View.OnClickListener {

    @BindView(R.id.m_toolbar)  Toolbar      toolbar;
    @BindView(R.id.send)       Button       send;
    @BindView(R.id.home)       ImageView    home;
    @BindView(R.id.delete)     ImageView    delete;
    @BindView(R.id.chat_view)  RecyclerView chatView;
    @BindView(R.id.input_text) EditText     input;

    private IChatPresenter mPresenter;

    private ChatAdapter adapter;  //recyclerView适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        mPresenter = new ChatPresenterImpl(this);

        adapter = new ChatAdapter(mPresenter.getList());
        initRecyclerView();
        mPresenter.loadAll();

        optimizeUI();

        home.setOnClickListener(this);
        send.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:   finish();        break;
            case R.id.delete: onDeleteClick(); break;
            case R.id.send:   onSendClick();   break;
            default: break;
        }
    }

    /** 对RecyclerView初始化 */
    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(ChatActivity.this);
        chatView.addItemDecoration(new ItemSpaceDecoration(
                Utils.dpToPx(ChatActivity.this, 8))); //设置边距
        chatView.setLayoutManager(manager);
        chatView.setAdapter(adapter);
    }

    /** 发送按钮点击事件 */
    private void onSendClick() {
        String text = input.getText().toString();
        if (!text.trim().isEmpty()) {
            mPresenter.addToList(text, TYPE_SEND);
            mPresenter.save(text, TYPE_SEND);
            input.setText("");
            if (!mPresenter.getList().isEmpty()) {
                chatView.smoothScrollToPosition(mPresenter.getList().size() - 1);
            }
            mPresenter.requestSendMessage(text);
        }
    }

    /** 删除按钮点击事件 */
    private void onDeleteClick() {
        AlertDialog dialog = new AlertDialog.Builder(ChatActivity.this)
                .setMessage("确定要删除所有消息记录吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.clearList();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /** 优化UI细节 */
    @SuppressLint("ClickableViewAccessibility")
    private void optimizeUI() {
        if (!mPresenter.getList().isEmpty()) {
            chatView.smoothScrollToPosition(mPresenter.getList().size() - 1);
        }
        //优化1：使触摸聊天界面的任意位置都能隐藏键盘
        chatView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    InputMethodManager imm = (InputMethodManager)
                            ChatActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });

        //优化2：利用Handler的延迟消息使recyclerView始终自动滚动到列表最后一项
        input.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("HandlerLeak") @Override
            public boolean onTouch(View v, MotionEvent event) {
                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == 0) {
                            if (!mPresenter.getList().isEmpty()) {
                                chatView.smoothScrollToPosition(mPresenter.getList().size() - 1);
                            }
                        }
                    }
                }.sendEmptyMessageDelayed(0, 100);
                return false;
            }
        });

        //优化3：设置EditText按回车发送消息
        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    send.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showReceivedMessage(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPresenter.addToList(text, TYPE_RECEIVE);
                mPresenter.save(text, TYPE_RECEIVE);
                chatView.smoothScrollToPosition(mPresenter.getList().size() - 1);
            }
        });
    }

    @Override
    public void initList(List<TextBean> list) {
        mPresenter.setList(list);
    }

    @Override
    public void refreshList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showToast(String text) {
        Looper.prepare();
        Toast.makeText(ChatActivity.this, text, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void intent(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        ChatActivity.this.startActivity(intent);
    }

}
