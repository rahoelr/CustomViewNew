package com.example.customviewapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyButton myButton;
    private EditTextClear editTextClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myButton = findViewById(R.id.btn1);
        editTextClear = findViewById(R.id.et1);
        setMyButtonEnable();

        editTextClear.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    setMyButtonEnable();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }



    private void setMyButtonEnable() {
        Editable result = editTextClear.getText();
        if (result != null && !result.toString().isEmpty()) {
            myButton.setEnabled(true);
        } else {
            myButton.setEnabled(false);
        }
    }
}

class MyButton extends AppCompatButton {

    Drawable mEnabledButtonImage;
    Drawable mDisabledButtonImage;
    private int txtColor = 0;

    private void init(){
        txtColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        mEnabledButtonImage = (Drawable) ContextCompat.getDrawable(getContext(), R.drawable.bg_button);
        mDisabledButtonImage = (Drawable) ContextCompat.getDrawable(getContext(), R.drawable.bg_button_disable);
    }

    public MyButton(@NonNull Context context) {
        super(context);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackground((isEnabled()) ? mEnabledButtonImage : mDisabledButtonImage);
        setTextColor(txtColor);
        setTextSize(12f);
        setGravity(Gravity.CENTER);
        setText((isEnabled()) ? "Kirim" : "Eits Isi Yaa");
    }

}

class EditTextClear extends AppCompatEditText{
    Drawable mClearButtonImage;
    private void init(){

        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (getCompoundDrawablesRelative()[2] != null){
                    float mClearButtonStart = (getWidth() - getPaddingEnd() - mClearButtonImage.getIntrinsicWidth());
                    boolean isButtonClicked = false;

                    if (getLayoutDirection() != LAYOUT_DIRECTION_RTL) {
                        if (motionEvent.getX() > mClearButtonStart) {
                            isButtonClicked = true;
                        }
                    } else {
                        if (motionEvent.getX() < mClearButtonStart) {
                            isButtonClicked = true;
                        }
                    }

                    if (isButtonClicked){
                        if (motionEvent.getAction() == motionEvent.ACTION_DOWN){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }

                        if(motionEvent.getAction() == motionEvent.ACTION_UP){
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                        }
                    }
                }

                return false;
            }
        });
    }

    public EditTextClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);

    }
}
