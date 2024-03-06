package com.thejgdt.yaalil;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class NaasFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NaasFragment() {
        // Required empty public constructor
    }

    public static NaasFragment newInstance(String param1, String param2) {
        NaasFragment fragment = new NaasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_naas, container, false);

        ScrollView NaasView = rootView.findViewById(R.id.mainView);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.naas);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray ayahsArray = jsonObject.getJSONArray("ayahs");

            LinearLayout linearLayout = new LinearLayout(requireContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            for (int i = 0; i < ayahsArray.length(); i++) {
                JSONObject ayahObject = ayahsArray.getJSONObject(i);
                String arabicText = ayahObject.getString("arab");
                String translation = ayahObject.getString("translation");

                arabicText = convertHtmlEntities(arabicText);
                translation = convertHtmlEntities(translation);

                TextView arabicTextView = new TextView(requireContext());
                arabicTextView.setText(arabicText);
                arabicTextView.setTextSize(24);
                arabicTextView.setTextColor(Color.BLACK);
                arabicTextView.setPadding(0, 20, 0, 10);

                TextView translationTextView = new TextView(requireContext());
                translationTextView.setText(translation);
                translationTextView.setTextSize(14);
                translationTextView.setTextColor(Color.BLACK);
                translationTextView.setPadding(0, 10, 0, 20);

                if (i % 2 == 0) {
                    arabicTextView.setBackgroundColor(Color.parseColor("#DADADA"));
                    translationTextView.setBackgroundColor(Color.parseColor("#DADADA"));
                } else {
                    arabicTextView.setBackgroundColor(Color.parseColor("#00000000"));
                    translationTextView.setBackgroundColor(Color.parseColor("#00000000"));
                }

                linearLayout.addView(arabicTextView);
                linearLayout.addView(translationTextView);

                LinearLayout.LayoutParams arabicLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                arabicTextView.setLayoutParams(arabicLayoutParams);

                LinearLayout.LayoutParams translationLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                translationTextView.setLayoutParams(translationLayoutParams);
            }

            NaasView.addView(linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private String convertHtmlEntities(String textWithEntities) {
        return Html.fromHtml(textWithEntities, Html.FROM_HTML_MODE_LEGACY).toString();
    }
}