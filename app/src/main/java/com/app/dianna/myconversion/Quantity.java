package com.app.dianna.myconversion;

import android.util.Log;

import java.text.DecimalFormat;

public class Quantity
{       // Each object will have both a value and a unit of measure
    private static final String TAG = Quantity.class.getSimpleName();
    final double value;
    final Unit unit;
            /*Enum types use a constant key to represent a value
                They allow us to easily define how to convert all the other types
                of measurements to convert from teaspoon to anything. Then to make
                any myconversion we convert from the starting type to teaspoon and then
                to the final required type.*/

    public static enum Unit
    {
        TSP(1.0d), TBS(0.3333d), CUP(0.0208d), OZ(0.1666d),
        PINT(0.0104d), QUART(0.0052d), GALLON(0.0013d),
        POUND(0.0125d), ML(4.9289d), LITER(0.0049d),
        MG(5687.5d), KG(0.0057d);
                /* We define that TSP will be the base unit of measure that we will
                   convert to and then convert from    */
        final static Unit baseUnit = TSP;
        /* Will hold the number of tsps converted from the original unit ie if you're converting
        from 1 TBS to TSP the byBaseUnit will be .3333 so you know what to use to divide or multiply
        This is just setting up the amount from the current unit's myconversion? otherConvertedToTsp*/
        final double byBaseUnit;
                    // Receives the number of tsps the starting unit equals
        private Unit(double inTsp)
        {
            this.byBaseUnit = inTsp;
            Log.d(" Unit ", "inTsp= " + inTsp + " byBaseUnit = " + String.valueOf(byBaseUnit));
        }

        // This section starts the myconversion either from an "other" unit to tsps
                    // Converts any other unit value to the number of tsps
        public double toBaseUnit(double value)
        {
            Log.d("toBaseUnit-return ", "value= " + value + " / " + String.valueOf(byBaseUnit));
            return value / byBaseUnit;
        }
                /* We convert to another unit by using the teaspoon myconversion percent
                    defined in the enum */
        public double fromBaseUnit(double value)
        {
            Log.d("fromBaseUnit-return ", "value= " + value + " * " + String.valueOf(byBaseUnit));
            return value * byBaseUnit;
        }
    }
                // The constructor that receives the value and unit of measure from the enum above
    public Quantity(double value, Unit unit)
    {
        super();
        Log.d("Quantity-constructor start ", "value & unit= " + value + " & " + String.valueOf
                (unit));
        this.value = value;
        this.unit = unit;
        Log.d("Quantity-constructor end ", "value & unit= " + value + " & " + String.valueOf
                (unit));
    }
            // method name="to" returns "Quantity" object
                // Converts from TSP to the desired unit type
    public Quantity to(Unit newUnit)
    {
        Unit oldUnit = this.unit;
        Log.d("to method-QTY Obj returned ", "value= " + value);
        return new Quantity(newUnit.fromBaseUnit(
                oldUnit.toBaseUnit(value)), newUnit);
    }

                // Prints out to screen the unit amount and unit type
    @Override
        public String toString()
    {
        Log.d("QTY toString", "value= " + value + " unit= " + unit);
        try
        {
            DecimalFormat df = new DecimalFormat("#.0000");
            return df.format(value) + " " + unit.name().toLowerCase();
        } catch (Exception e)
        {
            e.printStackTrace();
            Log.d(TAG, "toString() ", e);
        }
        return "toString exception, check stack trace";
    }
}
