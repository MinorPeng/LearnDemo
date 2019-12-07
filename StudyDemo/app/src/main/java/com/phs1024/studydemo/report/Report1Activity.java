package com.phs1024.studydemo.report;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.phs1024.studydemo.R;

import java.util.ArrayList;

/**
 * @author PHS1024
 * @date 2019/9/24 21:09:25
 */
public class Report1Activity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Picture> mPictures;
    private ImageView mIvPicture;
    private TextView mTvIntroduce;
    private int mChoseId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report1);
        initData();
        initView();
    }

    private void initView() {
        mIvPicture = findViewById(R.id.iv_picture);
        mTvIntroduce = findViewById(R.id.tv_introduce);
        Button btnPre = findViewById(R.id.btn_pre);
        Button btnNext = findViewById(R.id.btn_next);

        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        changePicture(mChoseId);
    }

    private void initData() {
        mPictures = new ArrayList<>();
        mPictures.add(new Picture(R.drawable.scenery1, "古运河旁; 昨日的荣耀;和今天的崛起;都成为晚霞里;" +
                "一个风景;不必争这一时的荣宠;因为;每天;都有日出日落;每天;都有新生和泯灭;只要存在;就有欢乐和消磨;" +
                "就有故事和传说;一根根丝线连着古今;延伸进晚霞里;传承着风景的美妙;脚下;一条运河;默默流淌着;" +
                " 一路走来;造就了数不清的风景;一路走去;还有多少;未来的美丽画卷;正慢慢展开着;由你来解说"));
        mPictures.add(new Picture(R.drawable.scenery2, "北京野鸭湖风景吟\n" +
                "（摄影:邢璐/配文：疏影如虹）\n" +
                "京城五月，云影天光。野鸭湖水，碧波荡漾。\n" +
                "风吹绿柳，草茂树长。北京湿地，鸟语花香。\n" +
                "英满芳甸，无限晴光。游人如织，心怡欢畅。\n" +
                "人与自然，留连寻芳。"));
        mPictures.add(new Picture(R.drawable.scenery3, "“背影比风景更美！”\n" +
                "“期待你每天和我们分享点点滴滴！”\n“你和风景一样美丽，我看风景也看你。"));
        mPictures.add(new Picture(R.drawable.scenery4, "·东连“红尘中一、二等富贵风流之地”阊门，西接“吴中第一名胜”虎丘，长约七里。\n" +
                "·是唐代大诗人白居易任苏州刺史时募工凿河堆堤而成，至今已有一千一百余年历史。\n" +
                "·曾是明清时期中国商贸、文化最为发达的街区之一，被誉为“神州第一古街”、“老苏州的缩影，吴文化的窗口”。\n" +
                "·有民歌唱到：“上有天堂，下有苏杭。杭州有西湖，苏州有山塘。两处好地方，无限好风光。”\n" +
                "·夜晚的山塘街很有意境，适合拍照。你也可以找家茶馆喝茶、听评弹，感受老苏州的传统文化。"));
        mPictures.add(new Picture(R.drawable.scenery5, "·虎丘距今已有2500多年，是到访苏州必去之地，最有名的就是虎丘塔和虎丘剑池。\n" +
                "·依托着秀美的景色，悠久的历史文化景观，享有“吴中第一名胜”的美誉。\n" +
                "·虎丘塔被称为“东方比萨斜塔”，而“虎丘剑池”四个大字据说出自颜真卿的手笔。\n" +
                "·每年虎丘都会举办几次花会庙会活动，将会展出大量中外名花植物，布景静美。"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pre:
                clickPre();
                break;
            case R.id.btn_next:
                clickNext();
                break;
            default:
                break;
        }
    }

    private void clickNext() {
        if ((++mChoseId) == mPictures.size()) {
            mChoseId = 0;
        }
        changePicture(mChoseId);
    }

    private void clickPre() {
        if ((--mChoseId) == -1) {
            mChoseId = mPictures.size() - 1;
        }
        changePicture(mChoseId);
    }

    private void changePicture(int position) {
        mIvPicture.setImageResource(mPictures.get(position).getResId());
        mTvIntroduce.setText(mPictures.get(position).getIntroduce());
    }

    class Picture {
        private int mResId;
        private String mIntroduce;

        public Picture(int resId, String introduce) {
            this.mResId = resId;
            this.mIntroduce = introduce;
        }

        public int getResId() {
            return mResId;
        }

        public String getIntroduce() {
            return mIntroduce;
        }
    }
}
