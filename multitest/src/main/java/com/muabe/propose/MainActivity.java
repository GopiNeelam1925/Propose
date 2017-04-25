package com.muabe.propose;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.muabe.propose.chat.GraphBoard;
import com.muabe.propose.chat.SimpleStyle;
import com.muabe.propose.chat.skin.BarGraphSkin;
import com.muabe.propose.chat.skin.HLineSkin;
import com.muabe.propose.chat.skin.LineGraphSkin;
import com.muabe.propose.chat.skin.TextArraySkin;
import com.muabe.propose.chat.skin.VLineSkin;
import com.muabe.propose.touch.coords.WindowCoordinates;

public class MainActivity extends Activity {
    View progress;
    TextView text, select1, select2;

    GraphBoard graphBoard;
    public static float den;

    int count=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowCoordinates.bindWindow(this);


        text = (TextView)findViewById(R.id.text);
        select1 = (TextView)findViewById(R.id.select1);
        select2 = (TextView)findViewById(R.id.select2);

        den = getResources().getDisplayMetrics().density;

        text.setClickable(true);


        final TouchDetector detector = new TouchDetector(this);
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(v, event);
            }
        });



/***************************************************************************************************************************************************************************************************/

        progress = findViewById(R.id.progress);
        progress.setVisibility(View.GONE);
        graphBoard = (GraphBoard)findViewById(R.id.board);
        graphBoard.init(7, 1000);
        graphBoard.setPaddingCount(3);
        graphBoard.setOnScrollListener(new GraphBoard.OnScrollListener() {
            @Override
            public void onStart() {
                Log.i("d","시작");
            }

            @Override
            public void onEnd() {
                Log.i("d","끝");
                if(progress.getVisibility() == View.GONE) {
                    progress.setVisibility(View.VISIBLE);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            String[] string_array = {count + "/1", count + "/2", count + "/3", count + "/4", count + "/5", count + "/6", count + "/7", count + "/8", count + "/9", count + "/10"
                                    , count + "/11", count + "/12", count + "/13", count + "/14", count + "/15", count + "/16", count + "/17", count + "/18", count + "/19", count + "/20"
                                    , count + "/21", count + "/22", count + "/23", count + "/24", count + "/25", count + "/26", count + "/27", count + "/28", count + "/29", count + "/30"};

                            Integer[] data = new Integer[string_array.length];
                            for (int i = 0; i < data.length; i++) {
                                data[i] = i % 10 * 100;
                            }

                            graphBoard.getSkin("bar").addItems(data);
                            graphBoard.getSkin("text").addItems(string_array);
                            ((VLineSkin) graphBoard.getSkin("vback")).setLineLength(graphBoard.getSkin("bar").getItemLength());
                            graphBoard.invalidate();
                            count++;
                            progress.setVisibility(View.GONE);
                        }
                    }.execute();
                }
            }
        });
        drawBar(graphBoard, getResources().getDisplayMetrics().density);
        graphBoard.setOnSelectListener(new GraphBoard.OnSelectListener() {
            @Override
            public void onSelected(int index) {
                select2.setText(index+":"+(String)(graphBoard.getSkin("text").getItems()[index]));
            }

            @Override
            public void onSelecting(int index) {
                select1.setText(index+":"+(String)(graphBoard.getSkin("text").getItems()[index]));
            }
        });
    }


    public void drawBar(GraphBoard board, float density){
        int count = 100;
        String[] array = { "3/1\n(일)", "3/2\n(일)", "3/3\n(일)", "3/4\n ", "3/5", "3/6", "3/7","3/8","3/9","3/10"
                ,"3/11","3/12","3/13","3/14","3/15","3/16","3/17","3/18","3/19","3/20"
                ,"3/21","3/22","3/23","3/24","3/25","3/26","3/27","3/28","3/29","3/30"};

        Integer[] data = new Integer[array.length];
        for(int i=0;i<data.length;i++){
            data[i] = i%6*200;
        }

        board.setMaxTopMargin(8);
        // 세로줄 정의
        SimpleStyle style = new SimpleStyle(Color.parseColor("#e5e5e5"), 1/density, 0, 0, SimpleStyle.Align.CENTER);
        SimpleStyle selectStyle;
        VLineSkin vback = new VLineSkin(data.length, style);
        vback.setSelectStyle(new SimpleStyle(Color.parseColor("#ff0000"), 1/density, 0, 0, SimpleStyle.Align.CENTER));
        board.addSkin("vback",vback); // 세로줄 추가


        // 하단 텍스트 정의
        style = TextArraySkin.getSimpleStyle(Color.parseColor("#999999"),  12,70);
        TextArraySkin text = new TextArraySkin(array, style);
        text.setLineMargin(10);
        text.setSelectStyle(TextArraySkin.getSimpleStyle(Color.parseColor("#ffffff"), 14, 70));
        board.addSkin("text",text); // 하단 텍스트 추가
        board.invalidate();


        // 막대그래프 정의
        style = BarGraphSkin.getSimpleStyle(Color.parseColor("#8ecd00"), 20);
        selectStyle = BarGraphSkin.getSimpleStyle(Color.parseColor("#ffffff"), 20);
        BarGraphSkin bar = new BarGraphSkin(data, style);
        bar.setSelectStyle(selectStyle);
        board.addSkin("bar",bar); // 막대그래프 추가


        // 선그래프 정의
        SimpleStyle circleStyle = LineGraphSkin.getCircleSimpleStyle(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"), 0,4f);
        SimpleStyle lineStyle = LineGraphSkin.getLineSimpleStyle(Color.parseColor("#ffffff"), 1f);
        LineGraphSkin lineSkin = new LineGraphSkin(data, circleStyle, lineStyle);
        lineSkin.setSelectStyle(LineGraphSkin.getCircleSimpleStyle(Color.parseColor("#77ffffff"), Color.parseColor("#ff0000"), 2f,6f));
        board.addSkin("line",lineSkin); // 라인그래프 추가


        // 가로줄 정의
        Integer[] lines = {800};
        style = HLineSkin.getSimpleStyle(Color.parseColor("#33e3dd"), 1/density);
        HLineSkin hback = new HLineSkin(lines, style);
        board.addSkin("hback", hback); // 가로줄 추가

    }


}
