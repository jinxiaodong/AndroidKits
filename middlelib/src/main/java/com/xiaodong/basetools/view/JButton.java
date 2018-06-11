package com.xiaodong.basetools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.xiaodong.basetools.R;

/**
 * Created by xiaodong.jin on 2018/5/23.
 * description：
 *  按钮高度统一：
 *      1.单按钮：高度44dp
 *      2.多按钮：高度40dp
 *      3.表单内按钮：33dp
 */

public class JButton extends android.support.v7.widget.AppCompatButton {

    /**
     * mode:按钮的样式
     * 0：默认首选样式，1：次选样式 2：弱操作样式
     */
    private int style = 0;

    public JButton(Context context) {
        this(context, null);
    }

    public JButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JButton);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        style = a.getInteger(R.styleable.JButton_mode, 0);
        //最后记得将TypedArray对象回收
        a.recycle();

        /*
        *主操作背景：R.drawable.ly_btn_blue_bg
        *次要操作背景：R.drawable.ly_btn_line_bg
        *弱操作背景：R.drawable.ly_btn_line_gray_bg
        * 禁用状态背景：R.drawable.ly_btn_forbidden_bg
        * */
        switch (style) {
            case 0:
                setBackgroundResource(R.drawable.ly_btn_blue_bg);
                break;
            case 1:
                setBackgroundResource(R.drawable.ly_btn_line_bg);
                break;
            case 2:
                setBackgroundResource(R.drawable.ly_btn_line_gray_bg);
                break;
            default:
                setBackgroundResource(R.drawable.ly_btn_blue_bg);
        }
        setGravity(Gravity.CENTER);
    }

    public void setStyle(int style){
        this.style = style;
    }
}
