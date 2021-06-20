package com.alarminum.alarminumapp.repository;

import com.alarminum.alarminumapp.utils.AppExecutors;
import com.alarminum.alarminumapp.utils.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TimeTableRepository {
    boolean error;
    int index=0;
    String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
    String UserAgent = " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";
    String username = "*************"; // 에타 ID
    String password = "*************"; // 에타 비밀번호
    JSONObject jsonTimeTable;
    String JSON;
    HashMap<String,String> data1 = new HashMap<>();
    HashMap<String,String> data2 = new HashMap<>();
    HashMap<String,String> data3 = new HashMap<>();
    Map<String, String> cookies = new HashMap<String,String>();
    Element temp;
    Document Parser1;
    Document Parser2;
    String semester ="";
    String[][] subjectInfo = new String[15][5]; // [0]:name [1]:hour [2]:minute [3]:day1 [4]:day2

    public TimeTableRepository() {
    }

    public void makeTimeTableRequest(String username, String password, final RepositoryCallback<JSONObject> callback ) {
        AppExecutors.getInstance().getNetworkIO().execute(() -> {
            try{
                Result<JSONObject> result =  makeSyncTimeTableRequest(username, password);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<JSONObject> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    private Result<JSONObject> makeSyncTimeTableRequest(String username, String password) {
        error = false;
        for(int i=0; i< 15; ++i) {
            for(int j=0; j<5; ++j) {
                subjectInfo[i][j] = "-1";
            }
        }
        try {
            data1.put("userid", username);
            data1.put("password", password);
            data1.put("redirect", "/");

            Connection.Response loginForm = Jsoup.connect("https://everytime.kr/user/login")
                    .userAgent(UserAgent)
                    .timeout(10000)
                    .header("Origin", "https://everytime.kr")
                    .header("Referer", "https://everytime.kr/")
                    .header("Accept", Accept)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .data(data1)
                    .method(Connection.Method.GET)
                    .execute();
            //첫 로그인 후 얻은 쿠키
            Map<String, String> cookies = loginForm.cookies();
            data2.put("year", "2021");
            data2.put("semester", "1");

            Connection.Response response = Jsoup.connect("https://api.everytime.kr/find/timetable/table/list/semester")
                    .userAgent(UserAgent)
                    .timeout(10000)
                    .cookies(cookies)
                    .data(data2)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Connection", "keep-alive")
                    .header("Content-Length", "20")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .method(Connection.Method.POST)
                    .execute();


            Parser1 = response.parse();
            Element e1 = Parser1.selectFirst("table");
            semester = e1.attr("id");

            data3.put("id", semester);
            Connection.Response timetable = Jsoup.connect("https://api.everytime.kr/find/timetable/table")
                    .userAgent(UserAgent)
                    .timeout(10000)
                    .cookies(cookies)
                    .data(data3)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .header("Connection", "keep-alive")
                    .header("Content-Length", "11")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .method(Connection.Method.POST)
                    .execute();

            boolean isTwoday;
            Parser2 = timetable.parse();
            Elements elements = Parser2.select("subject");
            for (Element e2 : elements) {
                isTwoday = true;
                temp = e2.selectFirst("name");
                subjectInfo[index][0] = temp.attr("value");
                temp = e2.selectFirst("time");
                String temp2 = temp.attr("value");

                String hour = temp2.substring(2, 4);
                String minute = temp2.substring(5, 7);

                subjectInfo[index][1] = hour;
                subjectInfo[index][2] = minute;

                if (temp2.indexOf("(") == -1) {
                    isTwoday = false;
                }

                temp = e2.selectFirst("data");
                subjectInfo[index][3] = temp.attr("day");

                if (isTwoday) {
                    int nextday = Integer.parseInt(temp.attr("day"));
                    String lastInfo = Integer.toString(nextday + 2);
                    subjectInfo[index][4] = lastInfo;
                }
                index++;
            }


            JSONObject jsonObject = new JSONObject(); // 최종 선언문
            JSONArray SubjectArray = new JSONArray();
            for (int i = 0; i < index; ++i) {
                JSONObject dayinfo = new JSONObject();
                JSONArray dayArray = new JSONArray();
                JSONObject SubjectInfo = new JSONObject();
                SubjectInfo.put("name", subjectInfo[i][0]);
                SubjectInfo.put("hour", subjectInfo[i][1]);
                SubjectInfo.put("minute", subjectInfo[i][2]);
                dayinfo.put("day1", subjectInfo[i][3]);
                dayArray.put(dayinfo);
                dayinfo = new JSONObject();
                dayinfo.put("day2", subjectInfo[i][4]);
                dayArray.put(dayinfo);
                SubjectInfo.put("day", dayArray);
                SubjectArray.put(SubjectInfo);
            }
            jsonObject.put("subject", SubjectArray);
            return new Result.Success<JSONObject>(jsonObject);
        }catch(RuntimeException| IOException| JSONException e){
            return new Result.Error<JSONObject>(e);
        }
    }

}
