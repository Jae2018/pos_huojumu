package com.huojumu.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.listeners.onPointerMoveListener;
import com.huojumu.model.Production;
import com.huojumu.utils.GlideApp;

import java.util.List;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description:
 */
public class HomeProductAdapter extends BaseQuickAdapter<Production, BaseViewHolder> {

    private boolean moved;
    private float pointer1OldCoorX, pointer1NewCoorX;
    private float pointer1OldCoorY, pointer1NewCoorY;

    private onPointerMoveListener moveListener;

    public HomeProductAdapter(@Nullable List<Production> data, onPointerMoveListener moveListener) {
        super(R.layout.item_home_product_all, data);
        this.moveListener = moveListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Production item) {
        ImageView iv = helper.getView(R.id.iv_product_url);
        GlideApp.with(mContext).load(item.getImgs().get(0).getPath()).fallback(R.drawable.placeholder).into(iv);

        ViewConfiguration configuration = ViewConfiguration.get(mContext);
        final int mTouchSlop = configuration.getScaledPagingTouchSlop();

        helper.setText(R.id.tv_product_name, item.getProName())
                .setText(R.id.tv_product_cut, String.valueOf(item.getMinPrice()))
                .itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {

//                 onTouch 1: 1__65__41__105
//  onTouch 2: 129__65__169__105
// onTouch 3: 65__1__105__41
// onTouch 4: 65__129__105__169

                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        pointer1OldCoorX = e.getX();
                        pointer1OldCoorY = e.getY();
                        moved = false;
                        showArror(helper, true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moved = true;
                        //手指个数满足条件,判断每个手指的滑动方向
                        pointer1NewCoorX = e.getX();
                        pointer1NewCoorY = e.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        showArror(helper, false);
                        if (!moved) {
                            v.performClick();
                        } else {
                            float deltaX = Math.abs(pointer1NewCoorX - pointer1OldCoorX);
                            float deltaY = Math.abs(pointer1NewCoorY - pointer1OldCoorY);

                            if (deltaX > deltaY && deltaX > mTouchSlop) {
                                Log.e(TAG, "onTouch: 横向");
                                //横向滑动
                                if ((pointer1NewCoorX - pointer1OldCoorX) > 100) {
                                    //向右
                                    moveListener.onPointerMoved(item, 2);
                                    Log.e(TAG, "onTouch: 2");

                                } else if ((pointer1NewCoorX - pointer1OldCoorX) < -100) {
                                    //向左
                                    moveListener.onPointerMoved(item, 0);
                                    Log.e(TAG, "onTouch: 0");

                                }
                            } else if (deltaY > deltaX) {
                                Log.e(TAG, "onTouch: 纵向");
                                //纵向滑动
                                if ((pointer1NewCoorY - pointer1OldCoorY) > 100) {
                                    //向下
                                    Log.e(TAG, "onTouch: 3");
                                    moveListener.onPointerMoved(item, 3);

                                } else if ((pointer1NewCoorY - pointer1OldCoorY) < -100) {
                                    //向上
                                    Log.e(TAG, "onTouch: 1");
                                    moveListener.onPointerMoved(item, 1);

                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void showArror(BaseViewHolder helper, boolean b) {
        helper.setVisible(R.id.left_tv, b);
        helper.setVisible(R.id.right_tv, b);
        helper.setVisible(R.id.top_tv, b);
        helper.setVisible(R.id.bottom_tv, b);
    }

}
