package com.example.jingbin.cloudreader.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewAdapter;
import com.example.jingbin.cloudreader.base.baseadapter.BaseRecyclerViewHolder;
import com.example.jingbin.cloudreader.bean.AndroidBean;
import com.example.jingbin.cloudreader.databinding.ItemEverydayOneBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayThreeBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayTitleBinding;
import com.example.jingbin.cloudreader.databinding.ItemEverydayTwoBinding;
import com.example.jingbin.cloudreader.http.rx.RxBus;
import com.example.jingbin.cloudreader.http.rx.RxCodeConstants;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.ImgLoadUtil;
import com.example.jingbin.cloudreader.utils.PerfectClickListener;
import com.example.jingbin.cloudreader.view.webview.WebViewActivity;

import java.util.List;

/**
 * Created by jingbin on 2016/12/27.
 */

public class EverydayAdapter extends BaseRecyclerViewAdapter<List<AndroidBean>> {

    private static final int TYPE_TITLE = 1; // title
    private static final int TYPE_ONE = 2;// 一张图
    private static final int TYPE_TWO = 3;// 二张图
    private static final int TYPE_THREE = 4;// 三张图

    @Override
    public int getItemViewType(int position) {

        if (!TextUtils.isEmpty(getData().get(position).get(0).getType_title())) {
            return TYPE_TITLE;
        } else if (getData().get(position).size() == 1) {
            return TYPE_ONE;
        } else if (getData().get(position).size() == 2) {
            return TYPE_TWO;
        } else if (getData().get(position).size() == 3) {
            return TYPE_THREE;
        }
        return super.getItemViewType(position);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                return new TitleHolder(parent, R.layout.item_everyday_title);
            case TYPE_ONE:
                return new OneHolder(parent, R.layout.item_everyday_one);
            case TYPE_TWO:
                return new TwoHolder(parent, R.layout.item_everyday_two);
            default:
                return new ThreeHolder(parent, R.layout.item_everyday_three);
        }
    }

    private class TitleHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayTitleBinding> {

        TitleHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, final int position) {
            int index = 0;
            String title = object.get(0).getType_title();
            binding.tvTitleType.setText(title);
            if ("Android".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_android));
                index = 0;
            } else if ("福利".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_meizi));
                index = 1;
            } else if ("IOS".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_ios));
                index = 2;
            } else if ("休息视频".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_movie));
                index = 2;
            } else if ("拓展资源".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_source));
                index = 2;
            } else if ("瞎推荐".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_xia));
                index = 2;
            } else if ("前端".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_qian));
                index = 2;
            } else if ("App".equals(title)) {
                binding.ivTitleType.setImageDrawable(CommonUtils.getDrawable(R.drawable.home_app));
                index = 2;
            }

            final int finalIndex = index;
            binding.llTitleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE, finalIndex);
                }
            });
        }
    }

    private class OneHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayOneBinding> {

        OneHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(final List<AndroidBean> object, int position) {
            if ("福利".equals(object.get(0).getType())) {
                binding.tvOnePhotoTitle.setVisibility(View.GONE);
            } else {
                binding.tvOnePhotoTitle.setVisibility(View.VISIBLE);
                setDes(object, 0, binding.tvOnePhotoTitle);
            }
            displayRandomImg(1, 0, position, binding.ivOnePhoto);
            binding.llOnePhoto.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    WebViewActivity.loadUrl(v.getContext(),object.get(0).getUrl(),"加载中...");
                }
            });
        }
    }

    private class TwoHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayTwoBinding> {

        TwoHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            displayRandomImg(4, 0, position, binding.ivTwoOneOne);
            displayRandomImg(4, 1, position, binding.ivTwoOneTwo);
            setDes(object, 0, binding.tvTwoOneOneTitle);
            setDes(object, 1, binding.tvTwoOneTwoTitle);
        }
    }

    private class ThreeHolder extends BaseRecyclerViewHolder<List<AndroidBean>, ItemEverydayThreeBinding> {

        ThreeHolder(ViewGroup parent, int title) {
            super(parent, title);
        }

        @Override
        public void onBindViewHolder(List<AndroidBean> object, int position) {
            displayRandomImg(6, 0, position, binding.ivThreeOneOne);
            displayRandomImg(6, 1, position, binding.ivThreeOneTwo);
            displayRandomImg(6, 2, position, binding.ivThreeOneThree);
            setDes(object, 0, binding.tvThreeOneOneTitle);
            setDes(object, 1, binding.tvThreeOneTwoTitle);
            setDes(object, 2, binding.tvThreeOneThreeTitle);
        }
    }

    private void setDes(List<AndroidBean> object, int position, TextView textView) {
        textView.setText(object.get(position).getDesc());
    }

    private void displayRandomImg(int imgNumber, int position, int itemPosition, ImageView imageView) {
        ImgLoadUtil.displayRandom(imgNumber, position, itemPosition, imageView);
//        Glide.with(imageView.getContext())
//                .load(R.drawable.home_six_three)
//                .placeholder(R.drawable.home_six_three)
//                .error(R.drawable.home_six_three)
//                .crossFade(1500)
//                .into(imageView);

    }
}
