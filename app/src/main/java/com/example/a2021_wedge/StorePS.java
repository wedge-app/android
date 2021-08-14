package com.example.a2021_wedge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

public class StorePS extends AppCompatActivity {

    final int STEP_NONE = 0;
    final int STEP_TYPE = 1;
    final int STEP_THEMA = 2;
    final int STEP_NAME = 3;
    final int STEP_TEL = 4;
    final int STEP_ADDRESS1 = 5;
    final int STEP_ADDRESS2 = 6;
    final int STEP_MENU = 7;
    final int STEP_LAT = 8;
    final int STEP_LNG = 9;
    final int STEP_TIP = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_ps);

        AssetManager am = getAssets();
        InputStream is = null;

        try {
            int type = -1;
            String thema = null;
            String name = null;
            String tel = null;
            String address1 = null;
            String address2 = null;
            String menu = null;
            double lat = -1;
            double lng = -1;
            String tip = null;

            // XML 파일 스트림 열기
            is = am.open("yongsan.xml");
            System.out.println("열기 성공");

            // XML 파서 초기화
            XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            // XML 파서에 파일 스트림 지정.
            parser.setInput(is, "UTF-8");

            int eventType = parser.getEventType();
            int step = STEP_NONE;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    // XML 데이터 시작
                } else if (eventType == XmlPullParser.START_TAG) {
                    System.out.println("열기 성공2");
                    String startTag = parser.getName();
                    if (startTag.equals("type")) {
                        step = STEP_TYPE;
                    } else if (startTag.equals("thema")) {
                        step = STEP_THEMA;
                    } else if (startTag.equals("name")) {
                        step = STEP_NAME;
                    } else if (startTag.equals("tel")) {
                        step = STEP_TEL;
                    } else if (startTag.equals("address1")) {
                        step = STEP_ADDRESS1;
                    } else if (startTag.equals("address2")) {
                        step = STEP_ADDRESS2;
                    } else if (startTag.equals("lat")) {
                        step = STEP_LAT;
                    } else if (startTag.equals("lng")) {
                        step = STEP_LNG;
                    } else if (startTag.equals("tip")) {
                        step = STEP_TIP;
                    } else {
                        step = STEP_NONE;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = parser.getName();
                    System.out.println("열기 성공3");
                    if ((endTag.equals("type") && step != STEP_TYPE) ||
                            (endTag.equals("thema") && step != STEP_THEMA) ||
                            (endTag.equals("name") && step != STEP_NAME) ||
                            (endTag.equals("tel") && step != STEP_TEL) ||
                            (endTag.equals("address1") && step != STEP_ADDRESS1) ||
                            (endTag.equals("address2") && step != STEP_ADDRESS2) ||
                            (endTag.equals("menu") && step != STEP_MENU) ||
                            (endTag.equals("lat") && step != STEP_LAT) ||
                            (endTag.equals("lng") && step != STEP_LNG)) {

                    }
                    step = STEP_NONE;
                } else if (eventType == XmlPullParser.TEXT) {
                    System.out.println("열기 성공4");
                    String text = parser.getText();
                    if (step == STEP_TYPE) {
                        try {
                            type = Integer.parseInt(text);
                        } catch (Exception e) {
                            type = 0;
                        }
                    } else if (step == STEP_THEMA) {
                        thema = text;
                    } else if (step == STEP_NAME) {
                        name = text;
                    } else if (step == STEP_TEL) {
                        tel = text;
                    } else if (step == STEP_ADDRESS1) {
                        address1 = text;
                    } else if (step == STEP_ADDRESS2) {
                        address2 = text;
                    } else if (step == STEP_MENU) {
                        menu = text;
                    } else if (step == STEP_LAT) {
                        try {
                            lat = Double.parseDouble(text);
                        } catch (Exception e) {
                            type = 0;
                        }
                    } else if (step == STEP_LNG) {
                        try {
                            lng = Double.parseDouble(text);
                        } catch (Exception e) {
                            type = 0;
                        }
                    } else if (step == STEP_TIP) {
                        if(text == null) tip = "";
                        tip = text;
                    }
                }

                eventType = parser.next();
            }

            if (type == -1 || name == null || tel == null) {
                // ERROR : XML is invalid.
            } else {
                System.out.println("열기 성공5");
                EditText editTextType = (EditText) findViewById(R.id.editTextType);
                editTextType.setText(Integer.toString(type));

                EditText editTextThema = (EditText) findViewById(R.id.editTextThema);
                editTextThema.setText(thema);

                EditText editTextName = (EditText) findViewById(R.id.editTextName);
                editTextName.setText(name);

                EditText editTextTel = (EditText) findViewById(R.id.editTextTel);
                editTextTel.setText(tel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}