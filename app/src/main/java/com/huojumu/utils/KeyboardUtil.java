//package com.huojumu.utils;
//
//import android.app.Activity;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.text.Editable;
//import android.view.View;
//import android.widget.EditText;
//
//import com.huojumu.R;
//
//
///**
// *
// */
//public class KeyboardUtil {
//    private KeyboardView keyboardView;
//    private Keyboard k;// 数字键盘
//    private EditText ed;
//
//    public KeyboardUtil(Activity act) {
//        k = new Keyboard(act, R.xml.symbols);
//        keyboardView = act.findViewById(R.id.keyboard_view);
//        keyboardView.setKeyboard(k);
//        keyboardView.setEnabled(true);
//        keyboardView.setPreviewEnabled(false);
//        keyboardView.setVisibility(View.VISIBLE);
//        keyboardView.setOnKeyboardActionListener(listener);
//    }
//
//    public KeyboardUtil(Activity act, EditText ed) {
//        k = new Keyboard(act, R.xml.symbols);
//        this.ed = ed;
//        keyboardView = act.findViewById(R.id.keyboard_view);
//        keyboardView.setKeyboard(k);
//        keyboardView.setEnabled(true);
//        keyboardView.setPreviewEnabled(false);
//        keyboardView.setVisibility(View.VISIBLE);
//        keyboardView.setOnKeyboardActionListener(listener);
//    }
//
//    public void setEd(EditText edit) {
//        this.ed = edit;
//    }
//
//    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
//        @Override
//        public void swipeUp() {
//        }
//
//        @Override
//        public void swipeRight() {
//        }
//
//        @Override
//        public void swipeLeft() {
//        }
//
//        @Override
//        public void swipeDown() {
//        }
//
//        @Override
//        public void onText(CharSequence text) {
//        }
//
//        @Override
//        public void onRelease(int primaryCode) {
//        }
//
//        @Override
//        public void onPress(int primaryCode) {
//        }
//
//        //一些特殊操作按键的codes是固定的比如完成、回退等
//        @Override
//        public void onKey(int primaryCode, int[] keyCodes) {
//            if (ed == null)
//                return;
//            Editable editable = ed.getText();
//            int start = ed.getSelectionStart();
//            if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
//                if (editable != null && editable.length() > 0) {
//                    if (start > 0) {
//                        editable.delete(start - 1, start);
//                    }
//                }
//            } else if (primaryCode == 4896) {// 清空
//                editable.clear();
//            } else { //将要输入的数字现在编辑框中
//                editable.insert(start, Character.toString((char) primaryCode));
//            }
//        }
//    };
//
//    public void showKeyboard() {
//        int visibility = keyboardView.getVisibility();
//        if (visibility == View.GONE || visibility == View.INVISIBLE) {
//            keyboardView.setVisibility(View.VISIBLE);
//        }
//    }
//}
