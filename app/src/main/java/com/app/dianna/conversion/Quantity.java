package com.app.dianna.conversion;

import java.text.DecimalFormat;

public class Quantity
{       // Each object will have both a value and a unit of measure
    final double value;
    final Unit unit;
            /*Enum types use a constant key to represent a value
                They allow use to easily define how to convert all the other types
                of measurements to convert from teaspoon to anything. Then to make
                any conversion we convert from the starting type to teaspoon and then
                to the final required type.*/

    public static enum Unit
    {
        tsp(1.0d), tbs(0.3333d), cup(0.02083d), oz(0.1667d),
        pint(0.01042d), quart(0.005208d), gallon(0.001302d),
        pound(0.01087d), ml(4.929d), liter(0.004929d),
        mg(4929d), kg(0.004929d);
                /* We define that tsp will be the base unit of measure that we will
                   convert to and then convert from    */
        final static Unit baseUnit = tsp;
        final double byBaseUnit;      // Will hold the number of tsp the original unit converts to
                // Receives the number of tsps the starting unit equals
        private Unit(double inTsp)
        {
            this.byBaseUnit = inTsp;
        }
                // Converts any other unit value to the number of tsps
        public double toBaseUnit(double value)
        {
            return value / byBaseUnit;
        }
                /* We convert to another unit by using the teaspoon conversion percent
                    defined in the enum */
        public double fromBaseUnit(double value)
        {
            return value * byBaseUnit;
        }
    }
                // The constructor that receives the value and unit of measure
    public Quantity(double value, Unit unit)
    {
        super();
        this.value = value;
        this.unit = unit;
    }
                // Converts from tsp to the desired unit type
    public Quantity to(Unit newUnit)
    {
        Unit oldUnit = this.unit;
        return new Quantity(newUnit.fromBaseUnit(
                oldUnit.toBaseUnit(value)), newUnit);
    }
                // Prints out to screen the unit amount and unit type

    @Override
        public String toString()
    {
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(value) + " " + unit.name();
    }
}
