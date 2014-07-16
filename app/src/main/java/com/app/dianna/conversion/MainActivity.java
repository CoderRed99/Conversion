package com.app.dianna.conversion;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{
    private Spinner unitTypeSp;
    private EditText amountET;
    TextView tspTV, cupTV, tbsTV, ozTV,
            kgTV, quartTV, gallonTV, poundTV,
            mlTV, literTV, mgTV, pintTV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                /* Fills the spinner with the unit options
                    and adds the listener to the spinner */
        addItemsToUnitTypeSpinner();
        addListenerToUnitTypeSpinner();
        // Get a reference to the editText view to retrieve the amount of the unit type
        amountET = (EditText) findViewById(R.id.amountET);

        initializeViews();
    }

    public void addListenerToUnitTypeSpinner()
    {
        unitTypeSp = (Spinner) findViewById(R.id.unitTypeSp);

        unitTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long l)
            {
                String itemSelectedInSpinner = parent.getItemAtPosition(pos).toString();

                checkIfConvertingFromTsp(itemSelectedInSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                // TODO maybe add something here later
            }
        });
    }

    public void checkIfConvertingFromTsp(String currentUnit)
    {

        switch (currentUnit)
        {
            case "teaspoon":
                updateUnitTypesUsingTsp(Quantity.Unit.tsp);
                break;
            case "tablespoon":
                updateUnitTypesUsingOther(Quantity.Unit.tbs);
                break;
            case "cup":
                updateUnitTypesUsingOther(Quantity.Unit.cup);
                break;
            case "ounce":
                updateUnitTypesUsingOther(Quantity.Unit.oz);
                break;
            case "pint":
                updateUnitTypesUsingOther(Quantity.Unit.pint);
                break;
            case "quart":
                updateUnitTypesUsingOther(Quantity.Unit.quart);
                break;
            case "gallon":
                updateUnitTypesUsingOther(Quantity.Unit.gallon);
                break;
            case "pound":
                updateUnitTypesUsingOther(Quantity.Unit.pound);
                break;
            case "milliliter":
                updateUnitTypesUsingOther(Quantity.Unit.ml);
                break;
            case "liter":
                updateUnitTypesUsingOther(Quantity.Unit.liter);
                break;
            case "milligram":
                updateUnitTypesUsingOther(Quantity.Unit.mg);
                break;
            case "kilogram":
                updateUnitTypesUsingOther(Quantity.Unit.kg);
                break;
            default:
                throw new IllegalArgumentException("Invalid unit of measure: " + currentUnit);
        }
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert,
                                            Quantity.Unit unitConvertingTo, TextView theTextView)
    {
        Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.tsp);
        String tempUnit = unitQuantity.to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit currentUnit,
                                            Quantity.Unit preferredUnit, TextView targetTextView)
    {
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);
        String tempTextViewText = currentQuantitySelected.to(Quantity.Unit.tsp).to(preferredUnit)
                .toString();
        targetTextView.setText(tempTextViewText);
    }

    private void updateUnitTypesUsingOther(Quantity.Unit currentUnit)
    {       // Convert the value in the EditText box to a double
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());

        // Create a Quantity using the teaspoon unit
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);

        // Create the String for the teaspoon TextView
        String valueInTeaspoons = currentQuantitySelected.to(Quantity.Unit.tsp).toString();
        tspTV.setText(valueInTeaspoons);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit, Quantity.Unit.tbs, tbsTV);

        if (currentUnit.name().equals(currentQuantitySelected.unit.name()))
        {
            String currentUnitTextViewText = doubleToConvert + " " +
                    currentQuantitySelected.unit.name();
            String currentTextViewName = currentQuantitySelected.unit.name() +
                    "TV";

            int currentId = getResources().getIdentifier(currentTextViewName, "id",
                    MainActivity.this.getPackageName());

            TextView currentTextView = (TextView) findViewById(currentId);

            currentTextView.setText(currentUnitTextViewText);

        }
    }

    private void updateUnitTypesUsingTsp(Quantity.Unit currentUnit)
    {
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());

        String teaspoonValueAndUnit = doubleToConvert + " tsp";
        tspTV.setText(teaspoonValueAndUnit);

        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.tbs, tbsTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.cup, cupTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.oz, ozTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.pint, pintTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.quart, quartTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.gallon, gallonTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.pound, poundTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.ml, mlTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.liter, literTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.mg, mgTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.kg, kgTV);
    }

    public void addItemsToUnitTypeSpinner()
    {
        unitTypeSp = (Spinner) findViewById(R.id.unitTypeSp);
        ArrayAdapter<CharSequence> unitTypeSpAdapter =
                ArrayAdapter.createFromResource(this, R.array.conversion_types,
                        android.R.layout.simple_spinner_item);

        unitTypeSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        unitTypeSp.setAdapter(unitTypeSpAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeViews()
    {
        tspTV = (TextView) findViewById(R.id.tspTV);
        tbsTV = (TextView) findViewById(R.id.tbsTV);
        cupTV = (TextView) findViewById(R.id.cupTV);
        ozTV = (TextView) findViewById(R.id.ozTV);
        pintTV = (TextView) findViewById(R.id.pintTV);
        quartTV = (TextView) findViewById(R.id.quartTV);
        gallonTV = (TextView) findViewById(R.id.gallonTV);
        poundTV = (TextView) findViewById(R.id.poundTV);
        mlTV = (TextView) findViewById(R.id.mlTV);
        literTV = (TextView) findViewById(R.id.literTV);
        mgTV = (TextView) findViewById(R.id.mgTV);
        kgTV = (TextView) findViewById(R.id.kgTV);
    }

    public void excessText()
    {
    /*

    */
    }
}
