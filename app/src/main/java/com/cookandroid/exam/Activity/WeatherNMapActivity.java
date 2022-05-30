package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cookandroid.exam.DTO.Dust.PmInfo;
import com.cookandroid.exam.DTO.Weather.Item;
import com.cookandroid.exam.DTO.Weather.Result;
import com.cookandroid.exam.Interface.DustService;
import com.cookandroid.exam.Interface.WeatherService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.DustRetrofitClient;
import com.cookandroid.exam.Retrofit.WeatherRetrofitClient;
import com.cookandroid.exam.Util.ScheduleData;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class WeatherNMapActivity extends Activity {

    private static final String TAG = "DetailPageActivity";

    private TextView tvDay, tvEventName, tvEventTime, tvTmp, tvRain, tvWind, tvDust, tvRec;
    private ImageView weatherImg, dressImg1, dressImg2;
    private ImageButton backButton;

    private String MyKey = "3QkdRZx/R+OBMH+5QoKu7iRbyDkdjaO0nMixw6RktnNL74/9rWajdRkCmtRfYxxYrWv8OABBzFaEY5h6WqwJFA==";
    private String DustKey = "+WSXT9nqJHijeDm0+DfSdKPLPcMLKnfHaJ6XU7N2hq0VkI3x+NM7Yc4Bgbkbok08RCFQEIgd0W/LpiVOg2j3Ow==";

    private WeatherService weatherService;
    private List<Item> weatherItemList = new ArrayList<>();

    private DustService dustService;
    private List<com.cookandroid.exam.DTO.Dust.Item> dustItemList = new ArrayList<>();

    private String rainPercent = "-";
    private String temperature = "-";
    private String windSpeed = "-";
    private String weatherCode;

    private String dustValue, dustGrade;

    private ArrayList<ScheduleData> list = new ArrayList<>();
    ScheduleData data;
    private int pos, dressTem;
    private String base_date = "20220530";
    private String base_time = "0200";
    private String dust_daytime = "2022-05-30 02:00";
    private String strMonth = "May";

    String posTime;
    int timeH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            list = intent.getParcelableArrayListExtra("scheduleList");
            pos = intent.getIntExtra("pos", -1);
            pos = pos + 1;
        }
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //레이아웃 xml 지정
        setContentView(R.layout.activity_detail_page);

        if (list != null) {
            for (ScheduleData scheduleData : list) {
                posTime = scheduleData.getStartHms();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime localTime= LocalTime.parse(posTime, formatter);
                    timeH = Integer.valueOf(localTime.getHour());
                    if (timeH == pos) {
                        data = scheduleData;
                        base_time = String.format("%02d", pos);
                    }
                }
            }
        }

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat curDayFormat = new SimpleDateFormat("dd");
        String strCurMonth = curMonthFormat.format(date);
        String strCurDay = curDayFormat.format(date);

        //상단 월
        switch (strCurMonth) {
            case "01":
                strMonth = "Jan";
                break;
            case "02":
                strMonth = "Feb";
                break;
            case "03":
                strMonth = "Mar";
                break;
            case "04":
                strMonth = "Apr";
                break;
            case "05":
                strMonth = "May";
                break;
            case "06":
                strMonth = "Jun";
                break;
            case "07":
                strMonth = "Jul";
                break;
            case "08":
                strMonth = "Aug";
                break;
            case "09":
                strMonth = "Sep";
                break;
            case "10":
                strMonth = "Oct";
                break;
            case "11":
                strMonth = "Nov";
                break;
            case "12":
                strMonth = "Dec";
                break;
        }

        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        int dayofWeekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        String weekday = "SUN";

        //상단 요일
        switch (dayofWeekNumber) {
            case 1:
                weekday = "Sunday";
                break;
            case 2:
                weekday = "Monday";
                break;
            case 3:
                weekday = "Tuesday";
                break;
            case 4:
                weekday = "Wednesday";
                break;
            case 5:
                weekday = "Thursday";
                break;
            case 6:
                weekday = "Friday";
                break;
            case 7:
                weekday = "Saturday";
                break;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        base_date = format.format(date);
        System.out.println("WeatherNMap 171 : " + data.getStartYmd());
        dust_daytime = data.getStartYmd() + " " + base_time + ":00";
        base_time = base_time.concat("00");

        //UI 객체 설정
        backButton = (ImageButton) findViewById(R.id.detail_back);
        tvDay = (TextView) findViewById(R.id.detail_day);
        tvEventName = (TextView) findViewById(R.id.event_name);
        tvEventTime = (TextView) findViewById(R.id.event_time);
        tvTmp = (TextView) findViewById(R.id.weather_temperature);
        tvRain = (TextView) findViewById(R.id.weather_rainpercent);
        tvWind = (TextView) findViewById(R.id.weather_windspeed);
        tvDust = (TextView) findViewById(R.id.weather_dust);
        tvRec = (TextView) findViewById(R.id.dress_recommendation);
        weatherImg = (ImageView) findViewById(R.id.weather_image);
        dressImg1 = (ImageView) findViewById(R.id.dress_1);
        dressImg2 = (ImageView) findViewById(R.id.dress_2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvDay.setText(weekday + ", " + strMonth + " " + strCurDay);
        tvEventName.setText(data.getTitle());
        tvEventTime.setText(data.getStartHms() + " - " + data.getEndHms());

        weatherService = WeatherRetrofitClient.getClient().create(WeatherService.class);

        weatherService.getWeather(MyKey,
                "1",
                "10",
                "JSON",
                base_date,
                base_time,
                "60",
                "126").enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, retrofit2.Response<Result> response) {
                Result weatherResponse = response.body();
                String resultCode = weatherResponse.getResponse().getHeader().getResultCode();
                if (resultCode.equals("00")) {
                    weatherItemList = weatherResponse.getResponse().getBody().getItems().getItem();
                    for (Item item : weatherItemList) {
                        String str = item.getCategory();

                        switch (str) {
                            case "POP": {
                                //rainPercent = Integer.parseInt(item.getObsrValue());
                                rainPercent = item.getObsrValue();
                                break;
                            }
                            case "PTY": {
                                if (item.getObsrValue().equals("0")) {
                                    rainPercent = "0";
                                    weatherCode = item.getObsrValue();
                                } else if (item.getObsrValue().equals("1")) rainPercent = "100";
                                break;
                            }
                            case "WSD": {
                                //windSpeed = Double.parseDouble(item.getObsrValue());
                                windSpeed = item.getObsrValue();
                                break;
                            }
                            case "T1H": {
                                Double temDouble = Double.parseDouble(item.getObsrValue());
                                int tem = (int) Math.round(temDouble);
                                System.out.println(item.getCategory());
                                System.out.println("T1H = " + tem);
                                temperature = String.valueOf(tem);
                                break;
                            }
                            case "TMP": {
                                Double temDouble = Double.parseDouble(item.getObsrValue());
                                int tem = (int) Math.round(temDouble);
                                System.out.println(item.getCategory());
                                System.out.println("TMP = " + tem);
                                temperature = String.valueOf(tem);
                                break;
                            }
                        }
                    }
                    System.out.println(temperature);
                    System.out.println(rainPercent);
                    System.out.println(windSpeed);

                    switch (weatherCode) {
                        case "0":
                            weatherImg.setImageResource(R.drawable.weather_sunny);
                    }

                    tvTmp.setText(temperature);
                    tvRain.setText(rainPercent);
                    tvWind.setText(windSpeed);

                    dressTem = Integer.parseInt(temperature);

                    if (dressTem >= 27) {
                        tvRec.setText("나시티, 반바지, 민소매 원피스");
                        dressImg1.setImageResource(R.drawable.dress_sleeveless);
                        dressImg2.setImageResource(R.drawable.dress_shorts);
                    }
                    else if (dressTem >= 23 && dressTem <= 26) {
                        tvRec.setText("반팔, 얇은 셔츠, 얇은 긴팔, 반바지, 면바지");
                        dressImg1.setImageResource(R.drawable.dress_short_sleeve);
                        dressImg2.setImageResource(R.drawable.dress_shirt);
                    }
                    else if (dressTem >= 20) {
                        tvRec.setText("긴팔티, 가디건, 후드티, 면바지, 슬랙스, 스키니");
                        dressImg1.setImageResource(R.drawable.dress_jean);
                        dressImg2.setImageResource(R.drawable.dress_long_sleeve);
                    }
                    else if (dressTem >= 17) {
                        tvRec.setText("니트, 가디건, 후드티, 맨투맨, 청바지, 슬랙스");
                        dressImg1.setImageResource(R.drawable.dress_hoodie);
                        dressImg2.setImageResource(R.drawable.dress_sleeve);
                    }
                    else if (dressTem >= 12) {
                        tvRec.setText("자켓, 셔츠, 가디건, 간절기 야상");
                        dressImg1.setImageResource(R.drawable.dress_jacket);
                        dressImg2.setImageResource(R.drawable.dress_long_shirt);
                    }
                    else if (dressTem >= 10) {
                        tvRec.setText("트렌치코트, 간절기 야상, 여러겹 껴입기");
                        dressImg1.setImageResource(R.drawable.dress_trench);
                        dressImg2.setImageResource(R.drawable.dress_knit);
                    }
                    else if (dressTem >= 6) {
                        tvRec.setText("코트, 가죽자켓");
                        dressImg1.setImageResource(R.drawable.dress_coat);
                        dressImg2.setImageResource(R.drawable.dress_sweater_winter);
                    }
                    else {
                        tvRec.setText("겨울 옷(야상, 패딩, 목도리 등)");
                        dressImg1.setImageResource(R.drawable.dress_puffer_coat);
                        dressImg2.setImageResource(R.drawable.dress_scarf);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });


        dustService = DustRetrofitClient.getClient().create(DustService.class);

        dustService.getDust("용산구", "json", "daily", 1, 10, DustKey).enqueue(new Callback<PmInfo>() {
            @Override
            public void onResponse(Call<PmInfo> call, retrofit2.Response<PmInfo> response) {
                PmInfo dustResponse = response.body();
                dustItemList = dustResponse.getResponse().getBody().getItems();

                dustValue = "0";

                for (com.cookandroid.exam.DTO.Dust.Item item : dustItemList) {
                    if (item.getDataTime().equals(dust_daytime)) {
                        dustValue = item.getPm10Grade();
                        System.out.println(dustValue);
                    }
                }

                switch (dustValue) {
                    case "0":
                        dustGrade = "-";
                    case "1":
                        dustGrade = "좋음";
                    case "2":
                        dustGrade = "보통";
                    case "3":
                        dustGrade = "나쁨";
                    case "4":
                        dustGrade = "매우나쁨";
                }

                tvDust.setText(dustGrade);
            }

            @Override
            public void onFailure(Call<PmInfo> call, Throwable t) {
                Log.d("retrofit", t.getMessage());
            }
        });

    }


}
