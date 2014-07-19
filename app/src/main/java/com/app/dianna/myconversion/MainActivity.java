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
import android.widget.Toast;

/*  Code Analysis - refactoring todo's
/Users/dianna/Google Drive/dev/AndroidStudioProjects/Conversion/app/src/main/java/com/app/dianna/myconversion/MainActivity.java
        Warning:(19, 1) [UNUSED_IMPORT] Unused import statement
Warning:(23, 33) [UnusedDeclaration] Private field 'TAG' is never used
        Warning:(107, 13) 'if' statement replaceable with 'switch' statement
        Warning:(286, 56) [UnusedDeclaration] Parameter 'currentUnit' is never used
        Warning:(346, 9) 'if' statement can be replaced with 'return id == R.id.action_settings || super.onOptionsItemSelected(item);'
        Warning:(376, 17) Method 'excessText()' is never used
        /Users/dianna/Google Drive/dev/AndroidStudioProjects/Conversion/app/src/main/java/com/app/dianna/myconversion/Quantity.java
        Warning:(26, 27) Field 'baseUnit' is never used*/

public class MainActivity extends ActionBarActivity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    private Spinner unitTypeSp;
    String savedItemSelectedInSpinner;
    private EditText amountET;
    double savedDoubleToConvert;
    static String valueInTeaspoons;
    String DEBUG = "DEBUG ";
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
                Log.d(DEBUG, "onItemSelected-start");
                // Get the item selected in the Spinner
                String itemSelectedInSpinner = parent.getItemAtPosition(pos).toString();
                savedItemSelectedInSpinner = itemSelectedInSpinner;
                /*Verify if I'm converting from teaspoon so that I use the right
                conversion algorithm*/
                checkIfConvertingFromTsp(itemSelectedInSpinner);
                Log.d(DEBUG, "onItemSelected-finish");
            }
            public void onNothingSelected(AdapterView<?> arg0)
            {   // TODO maybe add something here later
            }
        });
        Log.d(DEBUG, "addListenerSpinner-finish");
    }

    public void addListenerToEditText()
    {
        // Get a reference to the editText view to retrieve the amount of the unit type
        amountET = (EditText) findViewById(R.id.amountET);
        /*InputMethodManager imm =(InputMethodManager) getSystemService(Context
            .INPUT_METHOD_SERVICE);
        imm.showSoftInput(amountET,InputMethodManager.SHOW_IMPLICIT);*/
        amountET.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                Log.d(DEBUG, "onEditorAction-start");
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {       // Close soft keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService
                            (Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(amountET.getWindowToken(), 0);
                        //Amount has changed, so convert the new amounts
                    checkIfConvertingFromTsp(savedItemSelectedInSpinner);
                    Log.d(DEBUG, "onEditorAction-finish");
                    return true;
                }
                return false;
            }
        });
    }

    public void checkIfConvertingFromTsp(String currentUnit)
    {
        Log.d(DEBUG, "checkIfConverting-start");
        if(currentUnit.equals("teaspoon")){
            updateUnitTypesUsingTsp(Quantity.Unit.tsp);
        } else {
            if(currentUnit.equals("tablespoon")){
                updateUnitTypesUsingOther(Quantity.Unit.tbs);
            } else if(currentUnit.equals("cup")){
                updateUnitTypesUsingOther(Quantity.Unit.cup);
            } else if(currentUnit.equals("ounce")){
                updateUnitTypesUsingOther(Quantity.Unit.oz);
            } else if(currentUnit.equals("pint")){
                updateUnitTypesUsingOther(Quantity.Unit.pint);
            } else if(currentUnit.equals("quart")){
                updateUnitTypesUsingOther(Quantity.Unit.quart);
            } else if(currentUnit.equals("gallon")){
                updateUnitTypesUsingOther(Quantity.Unit.gallon);
            } else if(currentUnit.equals("pound")){
                updateUnitTypesUsingOther(Quantity.Unit.pound);
            } else if(currentUnit.equals("milliliter")){
                updateUnitTypesUsingOther(Quantity.Unit.ml);
            } else if(currentUnit.equals("liter")){
                updateUnitTypesUsingOther(Quantity.Unit.liter);
            } else if(currentUnit.equals("milligram")){
                updateUnitTypesUsingOther(Quantity.Unit.mg);
            } else {
                updateUnitTypesUsingOther(Quantity.Unit.kg);
            }
        }
        // Switchback changes... passing String valueInTsps to updateUTUTsp,
        /*switch (currentUnit)
        {
            case "teaspoon":
                valueInTeaspoons = amountET.getText().toString();
                *//* updateUnitTypesUsingTsp(Quantity.Unit.tsp); *//*
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
        updateUnitTypesUsingTsp(valueInTeaspoons);
*/
        Log.d(DEBUG, "checkIfConverting-finish");
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert,
                                            Quantity.Unit unitConvertingTo, TextView theTextView)
    {
        Log.d(DEBUG, "updateUnitTextField-3 params-start");
        Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.tsp);
        String tempUnit = unitQuantity.to(unitConvertingTo).toString();
        theTextView.setText(tempUnit);

        Log.d(DEBUG, "updateUnitTextField-3 params-finish");
    }

    public void updateUnitTextFieldUsingTsp(double doubleToConvert, Quantity.Unit currentUnit,
                                            Quantity.Unit preferredUnit, TextView targetTextView)
    {
        Log.d(DEBUG, "updateUnitTextField-4 params-start");
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);

        // Algorithm used quantityInTbs.to(Unit.tsp).to(Unit.ounce)
        String tempTextViewText = currentQuantitySelected.to(Quantity.Unit.tsp).
                to(preferredUnit).toString();
        targetTextView.setText(tempTextViewText);
        Log.d(DEBUG, "updateUnitTextField-4 params-finish");
    }
    // Switchback changes... returning String valueInTsps from updateUTUOther,

    public void updateUnitTypesUsingOther(Quantity.Unit currentUnit)
    {       // Convert the value in the EditText box to a double
        Log.d(DEBUG, "updateUnitTypesUsingOther-start");
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());
        savedDoubleToConvert = doubleToConvert;

        // Create a Quantity using the teaspoon unit
        Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);
        // Log.d(DEBUG, "vIT= " + valueInTeaspoons);

        // Create the String for the teaspoon TextView
        valueInTeaspoons = currentQuantitySelected.to(Quantity.Unit.tsp).toString();
        // valueInTeaspoons = String.valueOf(doubleToConvert);
        Log.d(DEBUG, "cQS= " + String.valueOf(currentQuantitySelected));
        Log.d(DEBUG, "vIT= " + valueInTeaspoons);

        // // Set the text for the teaspoon TextView
        tspTV.setText(valueInTeaspoons);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.tbs, tbsTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.cup, cupTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.oz, ozTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.pint, pintTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.quart, quartTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.gallon, gallonTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.pound, poundTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.ml, mlTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.liter, literTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.mg, mgTV);

        updateUnitTextFieldUsingTsp(doubleToConvert, currentUnit,
                Quantity.Unit.kg, kgTV);

        // Set the currently selected unit to the number in the EditText
        if(currentUnit.name().equals(currentQuantitySelected.unit.name()))
        {

            // Create the TextView text by taking the value in EditText and adding
            // on the currently selected unit in the spinner
            String currentUnitTextViewText = doubleToConvert + " " +
                    currentQuantitySelected.unit.name();

            // Create the TextView name to change by getting the currently
            // selected quantities unit name and tacking on _text_view
            String currentTextViewName = currentQuantitySelected.unit.name() +
                    "TV";

            // Get the resource id needed for the textView to use in findViewById
            int currentId = getResources().getIdentifier(currentTextViewName, "id",
                    MainActivity.this.getPackageName());

            // Create an instance of the TextView we want to change
            TextView currentTextView = (TextView) findViewById(currentId);

            // Put the right data in the TextView
            currentTextView.setText(currentUnitTextViewText);

            Log.d(DEBUG, "updateUnitTypesUsingOther-finish");
            // return valueInTeaspoons;
        }
    }

    // Switchback changes... receiving String valueInTsps to updateUTUTsp,
    private void updateUnitTypesUsingTsp(Quantity.Unit currentUnit)
    {
        Log.d(DEBUG, "updateUnitTypesUsingTsp-start");
        // Convert the value in the EditText box to a double
        double doubleToConvert = Double.parseDouble(amountET.getText().toString());
        savedDoubleToConvert = doubleToConvert;
        // Combine value to unit
        String teaspoonValueAndUnit = doubleToConvert + " tsp";

        // String teaspoonValueAndUnit = valueInTeaspoons + " tsp";
        Log.d(DEBUG, "vIT= " + valueInTeaspoons);
        Log.d(DEBUG, "tVAU= " + teaspoonValueAndUnit);
                // Change the value for the teaspoon TextView
        tspTV.setText(teaspoonValueAndUnit);

                // double doubleToConvert = Double.parseDouble(valueInTeaspoons);
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

        Log.d(DEBUG, "updateUnitTypesUsingTsp-finish");
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

/*    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(value) + " " + unit.name();
    }*/

    public void excessText()
    {
    /*

    */
    }
}
