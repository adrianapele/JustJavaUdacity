package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream)
    {
        int pricePerCoffee = 5;
        int pricePerChocolate = 2;
        int pricePerWhippedCream = 1;

        if (hasChocolate)
        {
            pricePerCoffee += pricePerChocolate;
        }

        if (hasWhippedCream)
        {
            pricePerCoffee += pricePerWhippedCream;
        }

        return quantity * pricePerCoffee;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name)
    {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolatekBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasChocolate = chocolatekBox.isChecked();

        EditText nameText = (EditText) findViewById(R.id.name_editor_view);
        String textReceived = nameText.getText().toString();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, textReceived);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + textReceived);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public void increment(View btn)
    {
        if (quantity == 100)
        {
            Toast.makeText(this, "You can not have more than 100 coffee cups", Toast.LENGTH_SHORT).show();
            return;
        }
       quantity = quantity + 1;
       display(quantity);
    }

    public void decrement(View btn)
    {
        if (quantity == 1)
        {
            Toast.makeText(this, "You can not have less than 1 coffee cup", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private void displayMessage(String message)
    {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
