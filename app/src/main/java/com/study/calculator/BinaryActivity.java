package com.study.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class BinaryActivity extends AppCompatActivity {

    int two,eight,ten;
    String strTwo,strEight,strTen,strSixteen,strThirtyTwo;
    boolean isTwoTouch=true,isEightTouch=false,isTenTouch=false,isSixteenTouch=false,isThirdtyTwoTouch=false;

    EditText etTwo;
    EditText etEight;
    EditText etTen;
    EditText etSixteen;
    EditText etThirtyTwo;

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        Intent intent=new Intent();
        switch (id){
            case R.id.item_cal:
                intent.setClass(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item_unit:
                intent.setClass(this,UnitActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item_binary:
                intent.setClass(this,BinaryActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item_help:
                intent.setClass(this,HelpActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.item_exit:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);

        etTwo=findViewById(R.id.et_two);
        etEight=findViewById(R.id.et_eight);
        etTen=findViewById(R.id.et_ten);
        etSixteen=findViewById(R.id.et_sixteen);
        etThirtyTwo=findViewById(R.id.et_thirtytwo);

        etTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTwoTouch=true;
                isEightTouch=false;
                isTenTouch=false;
                isSixteenTouch=false;
                isThirdtyTwoTouch=false;
                return false;
            }
        });

        etEight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTwoTouch=false;
                isEightTouch=true;
                isTenTouch=false;
                isSixteenTouch=false;
                isThirdtyTwoTouch=false;
                return false;
            }
        });

        etTen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTwoTouch=false;
                isEightTouch=false;
                isTenTouch=true;
                isSixteenTouch=false;
                isThirdtyTwoTouch=false;
                return false;
            }
        });

        etSixteen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTwoTouch=false;
                isEightTouch=false;
                isTenTouch=false;
                isSixteenTouch=true;
                isThirdtyTwoTouch=false;
                return false;
            }
        });

        etThirtyTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTwoTouch=false;
                isEightTouch=false;
                isTenTouch=false;
                isSixteenTouch=false;
                isThirdtyTwoTouch=true;
                return false;
            }
        });

        etTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(isTwoTouch){
                    strTwo=editable.toString();
                    if(strTwo.length()==0) strTwo="0";
                    try {
                        ten=Integer.valueOf(strTwo,2);
                        strEight=Integer.toOctalString(ten);
                        strSixteen=Integer.toHexString(ten);
                        strThirtyTwo=Integer.toString(ten,32);
                        etEight.setText(strEight);
                        etTen.setText(ten+"");
                        etSixteen.setText(strSixteen);
                        etThirtyTwo.setText(strThirtyTwo);
                    } catch (Exception e) {
                        Toast toast=Toast.makeText(BinaryActivity.this, "不是二进制，转换失败！", Toast.LENGTH_LONG);
                        changeToastTime(toast,1000);
                    }
                }
            }
        });

        etEight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(isEightTouch){
                    strEight=editable.toString();
                    if(strEight.length()==0) strEight="0";
                    try {
                        ten=Integer.valueOf(strEight,8);
                        strTwo=Integer.toBinaryString(ten);
                        strSixteen=Integer.toHexString(ten);
                        strThirtyTwo=Integer.toString(ten,32);
                        etTwo.setText(strTwo);
                        etTen.setText(ten+"");
                        etSixteen.setText(strSixteen);
                        etThirtyTwo.setText(strThirtyTwo);
                    } catch (Exception e) {
                        Toast toast=Toast.makeText(BinaryActivity.this, "不是八进制，转换失败！", Toast.LENGTH_LONG);
                        changeToastTime(toast,1000);
                    }
                }
            }
        });

        etTen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(isTenTouch){
                    strTen=editable.toString();
                    if(strTen.length()==0) strTen="0";
                    try {
                        ten=Integer.valueOf(strTen);
                        strTwo=Integer.toBinaryString(ten);
                        strEight=Integer.toOctalString(ten);
                        strSixteen=Integer.toHexString(ten);
                        strThirtyTwo=Integer.toString(ten,32);
                        etTwo.setText(strTwo);
                        etEight.setText(strEight);
                        etSixteen.setText(strSixteen);
                        etThirtyTwo.setText(strThirtyTwo);
                    } catch (Exception e) {
                        Toast toast=Toast.makeText(BinaryActivity.this, "不是十进制，转换失败！", Toast.LENGTH_LONG);
                        changeToastTime(toast,1000);
                    }
                }
            }
        });

//        将十六进制转化转化为各个进制
        etSixteen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
//                如果isSixteenTouch为真那么将输入的editable转化为字符串
                if(isSixteenTouch){
                    strSixteen=editable.toString();
//                    如果strSixteen长度为0,那么strSixteen也为0
                    if(strSixteen.length()==0) strSixteen="0";
                    try {
//                        将strSixteen用valueOf转换为整型的包装类
                        ten=Integer.valueOf(strSixteen,16);
//                        将十进制转换为二进制
                        strTwo=Integer.toBinaryString(ten);
//                        将十进制转换为八进制
                        strEight=Integer.toOctalString(ten);
//                        将十进制转换为三十二进制
                        strThirtyTwo=Integer.toString(ten,32);
//                        将etTwo设定为strTwo
                        etTwo.setText(strTwo);
//                        将etTwo设定为strEight
                        etEight.setText(strEight);
//                        将etTwo设定为ten加上空字符串
                        etTen.setText(ten+"");
//                        将etTwo设定为strThirtyTwo
                        etThirtyTwo.setText(strThirtyTwo);
                    } catch (Exception e) {    //抛出异常，打印提示信息“不是十六进制，转换失败”
                        Toast toast=Toast.makeText(BinaryActivity.this, "不是十六进制，转换失败！", Toast.LENGTH_LONG);
                        changeToastTime(toast,1000);
                    }
                }
            }
        });

        etThirtyTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(isThirdtyTwoTouch){
                    strThirtyTwo=editable.toString();
                    if(strThirtyTwo.length()==0) strThirtyTwo="0";
                    try {
                        ten=Integer.valueOf(strThirtyTwo,32);
                        strTwo=Integer.toBinaryString(ten);
                        strEight=Integer.toOctalString(ten);
                        strSixteen=Integer.toHexString(ten);
                        etTwo.setText(strTwo);
                        etEight.setText(strEight);
                        etTen.setText(ten+"");
                        etSixteen.setText(strSixteen);
                    } catch (Exception e) {
                        Toast toast=Toast.makeText(BinaryActivity.this, "不是三十二进制，转换失败！", Toast.LENGTH_LONG);
                        changeToastTime(toast,1000);
                    }
                }
            }
        });

    }


    private void changeToastTime(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);//每隔三秒调用一次show方法;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

}


