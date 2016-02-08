package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleDemo extends Activity implements View.OnClickListener {
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    LinearLayout mLayout;
    TextView tv;
    RadioGroup mRadiogroup;
    RadioButton rb1,rb2,rb3,rb4;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPager=(ViewPager)findViewById(R.id.viewpager_layout);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.button1);
        btn7=(Button)findViewById(R.id.button2);
        btn8=(Button)findViewById(R.id.button3);
        mLayout=(LinearLayout)findViewById(R.id.lay1);
        tv=(TextView)findViewById(R.id.tv);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        mRadiogroup=(RadioGroup)findViewById(R.id.radioGroup1);
        rb1=(RadioButton)findViewById(R.id.radio0);
        rb2=(RadioButton)findViewById(R.id.radio1);
        rb3=(RadioButton)findViewById(R.id.radio2);
        rb4=(RadioButton)findViewById(R.id.radio3);
       // ViewPagerAdapter adapter = new ViewPagerAdapter(this, mResources);

        mPagerAdapter = new MyPagerAdapter();
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem(0);
    }





    @Override
    public void onClick(View v) {
switch(v.getId())
{
    case R.id.btn1:
        tv.setText("");
         tv.setText("Item1");
        break;
    case R.id.btn2:
        tv.setText("");

        tv.setText("Item2");
        break;
    case R.id.btn3:
        tv.setText("");

        tv.setText("Item3");
        break;
    case R.id.btn4:
        tv.setText("");

        tv.setText("Item4");
    break;
    case R.id.btn5:
        tv.setText("");

        tv.setText("Item5");
        break;
    case R.id.button1:
      mLayout.setBackgroundColor(Color.RED);
        break;
    case R.id.button2:
        mLayout.setBackgroundColor(Color.BLUE);
        break;
    case R.id.button3:
        mLayout.setBackgroundColor(Color.GREEN);
        break;


}
    }
    private class MyPagerAdapter extends PagerAdapter{

        int NumberOfPages = 4;

        int[] res = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,


        };
        int[] backgroundcolor = {
                0xFF101010,
                0xFF202020,
                0xFF303030,
                0xFF404040,
                0xFF505050};

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {


           /* TextView textView = new TextView(SimpleDemo.this);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            .setText(String.valueOf(position));*/

            ImageView imageView = new ImageView(SimpleDemo.this);
            imageView.setImageResource(res[position]);
            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(SimpleDemo.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            layout.setBackgroundColor(backgroundcolor[position]);
            layout.setLayoutParams(layoutParams);
          // layout.addView(textView);
            layout.addView(imageView);


            final int page = position;

            if(page==0)
            {
                rb1.setChecked(true);
                rb1.setFocusable(true);

            }
            if(page==1)
            {
                rb2.setChecked(true);
                rb1.setFocusable(true);
            }
            if(page==2)
            {
                rb3.setChecked(true);
                rb1.setFocusable(true);
            }
             if(page==3)

            {
                rb4.setChecked(true);
                rb1.setFocusable(true);
            }
            final int pageno=page+1;
            layout.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Toast.makeText(SimpleDemo.this,
                            "Image " + pageno + " clicked",
                            Toast.LENGTH_SHORT).show();
                }});

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout)object);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(SimpleDemo.this,Home.class);
        startActivity(intent);
        finish();
    }
}
