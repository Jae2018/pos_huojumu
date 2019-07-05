package com.huojumu.adapter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huojumu.R;
import com.huojumu.listeners.onPointerMoveListener;
import com.huojumu.model.Production;
import com.huojumu.utils.GlideApp;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : Jie
 * Date: 2018/10/8
 * Description
 */
public class HomeProductAdapter extends BaseQuickAdapter<Production, BaseViewHolder> {

    private boolean moved, showed;
    private float pointer1OldCoorX, pointer1NewCoorX;
    private float pointer1OldCoorY, pointer1NewCoorY;
    private int iNumber1, iNumber2, iNumber3, iNumber4;
    private onPointerMoveListener moveListener;
    private Timer timer;
    private int time = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler();
    private boolean isNotUp;

    public HomeProductAdapter(@Nullable List<Production> data, onPointerMoveListener moveListener) {
        super(R.layout.item_home_product_all, data);
        this.moveListener = moveListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Production item) {
        ImageView iv = helper.getView(R.id.iv_product_url);
        GlideApp.with(mContext).load(item.getImgs().get(0).getPath()).fallback(R.drawable.placeholder).into(iv);
        iNumber1 = -1;
        iNumber2 = -1;
        iNumber3 = -1;
        iNumber4 = -1;
        String type1 = "";
        String type2 = "";
        String type3 = "";
        String type4 = "";

        for (int i = 0; i < item.getScales().size(); i++) {
            switch (item.getScales().get(i).getTcposition()) {
                case "3":
                    iNumber1 = i;
                    type1 = item.getScales().get(i).getScaName();
                    break;
                case "6":
                    iNumber2 = i;
                    type2 = item.getScales().get(i).getScaName();
                    break;
                case "9":
                    type3 = item.getScales().get(i).getScaName();
                    iNumber3 = i;
                    break;
                case "12":
                    type4 = item.getScales().get(i).getScaName();
                    iNumber4 = i;
                    break;
            }
        }
        if (iNumber1 == -1) {
            type1 = "";
            helper.setVisible(R.id.right_tv, false);
        }
        if (iNumber2 == -1) {
            type2 = "";
            helper.setVisible(R.id.bottom_tv, false);
        }
        if (iNumber3 == -1) {
            type3 = "";
            helper.setVisible(R.id.left_tv, false);
        }
        if (iNumber4 == -1) {
            type4 = "";
            helper.setVisible(R.id.top_tv, false);
        }

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isNotUp) {
                    showed = true;
                    showArror(helper, true);
                }
            }
        };

        helper.setText(R.id.tv_product_name, item.getProName())
                .setText(R.id.tv_product_cut, String.valueOf(item.getMinPrice()))
                .setText(R.id.right_tv, type1)
                .setText(R.id.bottom_tv, type2)
                .setText(R.id.left_tv, type3)
                .setText(R.id.top_tv, type4)
                .itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                switch (e.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        if (e.getActionIndex() == 0) {
                            pointer1OldCoorX = e.getX();
                            pointer1OldCoorY = e.getY();
                            moved = false;
                            isNotUp = false;
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    time++;
                                    if (time == 2) {
                                        isNotUp = true;
                                        if (timer != null) {
                                            timer.cancel();
                                            timer.purge();
                                            timer = null;
                                        }
                                        time = 0;
                                    }
                                }
                            }, 0, 149);

                            handler.postDelayed(runnable, 300);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isNotUp = true;
                        moved = true;
                        //手指个数满足条件,判断每个手指的滑动方向
                        pointer1NewCoorX = e.getX();
                        pointer1NewCoorY = e.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //
                        isNotUp = false;
                        if (showed && moved) {
                            float deltaX = Math.abs(pointer1NewCoorX - pointer1OldCoorX);
                            float deltaY = Math.abs(pointer1NewCoorY - pointer1OldCoorY);

                            if (deltaX > deltaY) {
                                //横向滑动
                                if ((pointer1NewCoorX - pointer1OldCoorX) > 70) {
                                    setSelected(helper, true, false, false, false);
                                    //向右
                                    try {
                                        moveListener.onPointerMoved(item, 2, item.getScales().get(iNumber1));
                                    } catch (Exception e1) {
                                        moveListener.onPointerMoved(item, 2, null);
                                    }
                                } else if ((pointer1NewCoorX - pointer1OldCoorX) < -70) {
                                    setSelected(helper, false, false, true, false);
                                    //向左
                                    try {
                                        moveListener.onPointerMoved(item, 0, item.getScales().get(iNumber3));
                                    } catch (Exception e1) {
                                        moveListener.onPointerMoved(item, 0, null);
                                    }
                                }
                            } else if (deltaY > deltaX) {
                                //纵向滑动
                                if ((pointer1NewCoorY - pointer1OldCoorY) > 70) {
                                    setSelected(helper, false, true, false, false);
                                    //向下
                                    try {
                                        moveListener.onPointerMoved(item, 3, item.getScales().get(iNumber2));
                                    } catch (Exception e1) {
                                        moveListener.onPointerMoved(item, 3, null);
                                    }
                                } else if ((pointer1NewCoorY - pointer1OldCoorY) < -70) {
                                    setSelected(helper, false, false, false, true);
                                    //向上
                                    try {
                                        moveListener.onPointerMoved(item, 1, item.getScales().get(iNumber4));
                                    } catch (Exception e1) {
                                        moveListener.onPointerMoved(item, 1, null);
                                    }
                                }
                            }
                        } else {
                            handler.removeCallbacks(runnable);
                            v.performClick();
                        }
                        showArror(helper, false);
                        showed = false;
                        if (timer != null) {
                            timer.cancel();
                            timer.purge();
                            timer = null;
                        }
                        break;
                }

                return true;
            }
        });

    }

    private void showArror(BaseViewHolder helper, boolean b) {
        helper.setVisible(R.id.relative_types, b);
    }

    private void setSelected(BaseViewHolder helper, boolean b1, boolean b2, boolean b3, boolean b4) {
        helper.getView(R.id.right_tv).setSelected(b1);
        helper.getView(R.id.bottom_tv).setSelected(b2);
        helper.getView(R.id.left_tv).setSelected(b3);
        helper.getView(R.id.top_tv).setSelected(b4);
    }

}
