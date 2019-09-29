package com.fed.learnandroid;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText sourceEditText;

    private TextView resultTextView;
    private TextView generatedTextView;
    private TextView lengthGeneratedText;

    private String[] russians;
    private String[] latins;

    private PasswordsHelp helper;

    View copyButton;
    View copyGeneratedButton;
    View generateButton;

    ImageView qualityImageView;

    private CompoundButton checkCaps;
    private CompoundButton checkSymb;
    private CompoundButton checkDigit;

    private SeekBar lengthSeekBar;

    private int lenghtGenText=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceEditText = findViewById(R.id.edit_source);
        resultTextView = findViewById(R.id.text_result);
        generatedTextView = findViewById(R.id.text_generated);
        copyButton = findViewById(R.id.button_copy_password);
        copyGeneratedButton = findViewById(R.id.button_copy_generated_password);
        copyButton.setEnabled(false);
        copyGeneratedButton.setEnabled(false);
        generateButton = findViewById(R.id.buton_generate);
        checkCaps = findViewById(R.id.check_caps);
        checkDigit = findViewById(R.id.check_digital);
        checkSymb = findViewById(R.id.check_symbols);
        lengthSeekBar = findViewById(R.id.seekbar_length_generated);
        lengthGeneratedText = findViewById(R.id.text_length_generated);
        qualityImageView = findViewById(R.id.quality_image_view);

        russians = getResources().getStringArray(R.array.russians);
        latins = getResources().getStringArray(R.array.latin);
        helper = new PasswordsHelp(russians, latins);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder result = new StringBuilder();
//
                while (lenghtGenText>result.length()){
                    switch ((int)(Math.random()*4)){
                        case 0:
                                result.append((char)(97+Math.random()*26));
                            break;
                        case 1:
                            if(checkCaps.isChecked()){
                                result.append((char)(65+Math.random()*26));
                            }
                            break;
                        case 2:
                            if(checkDigit.isChecked()){
                                result.append((char)(48+Math.random()*10));
                            }
                            break;
                        case 3:
                            if(checkSymb.isChecked()){
                                result.append((char)(33+(int)(Math.random()*15)));
                            }
                            break;

                    }

                }

                generatedTextView.setText(result);

                copyGeneratedButton.setEnabled(true);
            }
        });

        lengthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                lengthGeneratedText.setText("Ваш пароль будет состоять из "+(i+5)+" "+getResources().getQuantityString(R.plurals.symbol_count, i+5));
                lenghtGenText = i+5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if(manager!=null) {
                    manager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clickbord_title), resultTextView.getText().toString()));

                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        copyGeneratedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if(manager!=null) {
                    manager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clickbord_title), generatedTextView.getText().toString()));

                    Toast.makeText(MainActivity.this, R.string.toast, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resultTextView.setText(helper.convert(charSequence));
                qualityImageView.setImageLevel(charSequence.length()/12.0*10000.0>10000 ? 10000: (int)(charSequence.length()/12.0*10000.0));
                copyButton.setEnabled(charSequence.length()>0);
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }
}
