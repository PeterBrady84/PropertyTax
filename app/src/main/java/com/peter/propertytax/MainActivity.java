package com.peter.propertytax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final double TAX_RATE = 0.18;    // Tax rate of 0.18%
    private static List<PropertyValueBand> bands;
    private EditText propertyValue;
    private TextView propertyValidation, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise data - to do: read from web service
        bands = new ArrayList<PropertyValueBand>() {
            {
                add(new PropertyValueBand(0, 100000));
                add(new PropertyValueBand(100001, 150000));
                add(new PropertyValueBand(150001, 200000));
                add(new PropertyValueBand(200001, 250000));
                add(new PropertyValueBand(250001, 300000));
                add(new PropertyValueBand(300001, 350000));
                add(new PropertyValueBand(350001, 400000));
                add(new PropertyValueBand(400001, 450000));
                add(new PropertyValueBand(450001, 500000));
            }
        };

        // initialise edit text
        propertyValue = (EditText) findViewById(R.id.property_value);
        Button calculate = (Button) findViewById(R.id.calculateButton);
        price = (TextView) findViewById(R.id.price);

        // validation for property value input
        propertyValidation = (TextView) findViewById(R.id.input_property_validation);
        propertyValidation.setVisibility(View.GONE);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (propertyValue.getText().toString().equals("")) {
                    propertyValidation.setVisibility(View.VISIBLE);
                    propertyValidation.setText(R.string.empty_value);
                    price.setText("");
                } else if (Double.parseDouble(propertyValue.getText().toString()) > 500000 ||
                        Double.parseDouble(propertyValue.getText().toString()) <= 0) {
                    propertyValidation.setVisibility(View.VISIBLE);
                    propertyValidation.setText(R.string.invalid_value);
                    price.setText("");
                } else {
                    propertyValidation.setVisibility(View.GONE);
                    double propertyTax = calculatePropertyTax(Double.parseDouble(propertyValue
                            .getText().toString()));
                    DecimalFormat df = new DecimalFormat("#,###.##");
                    price.setText(getString(R.string.property_tax_price, df.format(propertyTax)));
                }
            }
        });
    }

    public double calculatePropertyTax(double propertyValue) {
        // get band
        PropertyValueBand band = calculateBand(propertyValue);
        // multiply rate by mid point of band
        return (TAX_RATE / 100) * band.midPoint();
    }

    public static PropertyValueBand calculateBand(double propertyValue) {
        PropertyValueBand band = null;
        for (PropertyValueBand b : bands) {
            if (propertyValue >= b.getMin() && propertyValue <= b.getMax()) {
                band = b;
            }
        }
        if (band == null) {
            throw new IllegalArgumentException("Invalid Property Value");
        }
        return band;
    }
}
