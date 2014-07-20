package com.app.dianna.myconversion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Spinner unitTypeSp;
    String savedItemSelectedInSpinner;
    private EditText amountET;
    double savedDoubleToConvert;
    static String valueInTeaspoons;
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
        addListenerToEditText();

        initializeViews();
    }

    public void addListenerToUnitTypeSpinner()
    {
        unitTypeSp = (Spinner) findViewById(R.id.unitTypeSp);
        unitTypeSp.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3)
            {
                Log.d(TAG, "onItemSelected-start");
                // Get the item selected in the Spinner
                String itemSelectedInSpinner = parent.getItemAtPosition(pos).toString();
                savedItemSelectedInSpinner = itemSelectedInSpinner;
                /*Verify if I'm converting from teaspoon so that I use the right
                conversion algorithm*/
                checkIfConvertingFromTsp(itemSelectedInSpinner);
                Log.d(TAG, "onItemSelected-finish");
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {   // TODO maybe add something here later
            }
        });
        Log.d(TAG, "addListenerSpinner-finish");
    }

    public void addListenerToEditText()
    {
        // Get a reference to the editText view to retrieve the amount of the unit type
        amountET = (EditText) findViewById(R.id.amountET);
        amountET.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                Log.d(TAG, "onEditorAction-start");
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {       // Close soft keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService
                            (Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(amountET.getWindowToken(), 0);
                        //Amount has changed, so convert the new amounts
                    checkIfConvertingFromTsp(savedItemSelectedInSpinner);
                    Log.d(TAG, "onEditorAction-finish");
                    return true;
                }
                return false;
            }
        });
    }

    public void checkIfConvertingFromTsp(String currentUnit)
    {
        Log.d(TAG, "checkIfConverting-start");
        switch (currentUnit)
        {
            case "teaspoon":
                updateUnitTypesUsingTsp();
                break;
            case "tablespoon":
                updateUnitTypesUsingOther(Quantity.Unit.TBS);
                break;
            case "cup":
                updateUnitTypesUsingOther(Quantity.Unit.CUP);
                break;
            case "ounce":
                updateUnitTypesUsingOther(Quantity.Unit.OZ);
                break;
            case "pint":
                updateUnitTypesUsingOther(Quantity.Unit.PINT);
                break;
            case "quart":
                updateUnitTypesUsingOther(Quantity.Unit.QUART);
                break;
            case "gallon":
                updateUnitTypesUsingOther(Quantity.Unit.GALLON);
                break;
            case "pound":
                updateUnitTypesUsingOther(Quantity.Unit.POUND);
                break;
            case "milliliter":
                updateUnitTypesUsingOther(Quantity.Unit.ML);
                break;
            case "liter":
                updateUnitTypesUsingOther(Quantity.Unit.LITER);
                break;
            case "milligram":
                updateUnitTypesUsingOther(Quantity.Unit.MG);
                break;
            case "kilogram":
                updateUnitTypesUsingOther(Quantity.Unit.KG);
                break;
            default:
                throw new IllegalArgumentException("Invalid unit of measure: " + currentUnit);
        }
        Log.d(TAG, "checkIfConverting-finish");
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert,
                                            Quantity.Unit unitConvertingTo, TextView theTextView)
    {
        Log.d(TAG, "updateUnitTextField-3 params-start");
        Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.TSP);
        String tempUnit = unitQuantity.to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);

        Log.d(TAG, "updateUnitTextField-3 params-finish");
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit currentUnit,
                                            Quantity.Unit preferredUnit, TextView targetTextView)
    {
        Log.d(TAG, "updateUnitTextField-4 params-start");
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);

        // Algorithm used quantityInTbs.to(Unit.TSP).to(Unit.ounce)
        String tempTextViewText = currentQuantitySelected.to(Quantity.Unit.TSP).
                to(preferredUnit).toString();
        targetTextView.setText(tempTextViewText);
        Log.d(TAG, "updateUnitTextField-4 params-finish");
    }
    // Switchback changes... returning String valueInTsps from updateUTUOther,

    public void updateUnitTypesUsingOther(Quantity.Unit currentUnit)
    {       // Convert the value in the EditText box to a double
        Log.d(TAG, "updateUnitTypesUsingOther-start");
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());
        savedDoubleToConvert = doubleToConvert;

        // Create a Quantity using the teaspoon unit
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);
        // Log.d(TAG, "vIT= " + valueInTeaspoons);

        // Create the String for the teaspoon TextView
        valueInTeaspoons = currentQuantitySelected.to(Quantity.Unit.TSP).toString();
        // valueInTeaspoons = String.valueOf(doubleToConvert);
        Log.d(TAG, "cQS= " + String.valueOf(currentQuantitySelected));
        Log.d(TAG, "vIT= " + valueInTeaspoons);

        // // Set the text for the teaspoon TextView
        tspTV.setText(valueInTeaspoons);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.TBS, tbsTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.CUP, cupTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.OZ, ozTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.PINT, pintTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.QUART, quartTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.GALLON, gallonTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.POUND, poundTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.ML, mlTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.LITER, literTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.MG, mgTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.KG, kgTV);

        // Set the currently selected unit to the number in the EditText
        if(currentUnit.name().equals(currentQuantitySelected.unit.name()))
        {
            // Create the TextView text by taking the value in EditText and adding
            // on the currently selected unit in the spinner
            String currentUnitTextViewText = doubleToConvert + " " +
                    currentQuantitySelected.unit.name().toLowerCase();

            // Create the TextView name to change by getting the currently
            // selected quantities unit name and tacking on _text_view
            String currentTextViewName = currentQuantitySelected.unit.name().toLowerCase() +
                    "TV";

            // Get the resource id needed for the textView to use in findViewById
            int currentId = getResources().getIdentifier(currentTextViewName, "id",
                    MainActivity.this.getPackageName());

            // Create an instance of the TextView we want to change
            TextView currentTextView = (TextView) findViewById(currentId);

            // Put the right data in the TextView
            currentTextView.setText(currentUnitTextViewText);

            Log.d(TAG, "updateUnitTypesUsingOther-finish");
            // return valueInTeaspoons;
        }
    }

    private void updateUnitTypesUsingTsp()
    {
        Log.d(TAG, "updateUnitTypesUsingTsp-start");
        // Convert the value in the EditText box to a double
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());
        savedDoubleToConvert = doubleToConvert;
        // Combine value to unit
        String teaspoonValueAndUnit = doubleToConvert + " tsp";

                // Change the value for the teaspoon TextView
        tspTV.setText(teaspoonValueAndUnit);

        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.TBS, tbsTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.CUP, cupTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.OZ, ozTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.PINT, pintTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.QUART, quartTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.GALLON, gallonTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.POUND, poundTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.ML, mlTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.LITER, literTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.MG, mgTV);
        updateUnitTextFieldUsingTsp(doubleToConvert, Quantity.Unit.KG, kgTV);

        Log.d(TAG, "updateUnitTypesUsingTsp-finish");
    }

    public void addItemsToUnitTypeSpinner()
    {           // Get a reference to the spinner
        unitTypeSp = (Spinner) findViewById(R.id.unitTypeSp);

            // Create an ArrayAdapter using the string array and a default spinner layout
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
}
