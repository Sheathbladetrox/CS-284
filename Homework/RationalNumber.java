// Andrew Chuah
// I pledge my honor that I have abided by the Stevens Honor System.
// Professor Antonio Nicolosi
// CS-284-D HW 1

public class RationalNumber extends java.lang.Number{

    private int numerator;
    private int denominator;

    /**
     * This is a constructor that creates the RationalNumber object with two integers, and puts it in form "p/q".
     * @param p Integer that goes to the numerator.
     * @param q Integer that goes to the denominator.
     */
    public RationalNumber(int p, int q){
        // If denominator equals 0, throw exception saying that it's illegal to put q as 0.
        if(q == 0){
            throw new IllegalArgumentException("Denominator cannot be zero!");
        }

        // If denominator is negative, make numerator store negative sign and make denominator positive.
        if(q < 0){
            p = p * -1;
            q = q * -1;
        }
        numerator = p;
        denominator = q;

        simplify();
    }

    /**
     * This function simplifies a rational number p/q to its simplest form, r/s.
     * It replaces the receiving rational number with its simplified form.
     */
    public void simplify(){
        if(numerator != 0){
            int g = gcd(Math.abs(numerator), denominator);

            numerator = numerator / g;
            denominator = denominator / g;
        }
    }

    /**
     * Helper function for simplify().
     * @param p integer
     * @param q integer
     * @return gcd
     */
    private int gcd(int p, int q){
        while(p != q){
            if(p > q)
                p = p - q;
            else
                q = q - p;
        }
        return p;
    }

    /**
     * This function adds two rational numbers, one that is given as a parameter, and one that receives the message.
     * It modifies the receiving rational number with the result of the operation.
     */
    public void add(RationalNumber n2){
        int common_denominator = denominator * n2.getDenominator();
        int numer_1 = numerator * n2.getDenominator();
        int numer_2 = n2.getNumerator() * denominator;
        numerator = numer_1 + numer_2;

        denominator = common_denominator;
        simplify();
    }

    /**
     * This function multiplies two rational numbers, one as a parameter, and one that receives the message.
     * It modifies the receiving rational number with the result of the operation.
     */
    public void multiply(RationalNumber n2){
        numerator = numerator * n2.getNumerator();
        denominator = denominator * n2.getDenominator();

        simplify();
    }

    /**
     * Returns numerator of Rational Number
     * @return numerator
     */
    public int getNumerator(){
        return numerator;
    }

    /**
     * Returns denominator of Rational Number
     * @return denominator
     */
    public int getDenominator(){
        return denominator;
    }

    /**
     * Methods overridden from the Number class.
     */
    @Override
    public int intValue(){
        return numerator/denominator;
    }

    @Override
    public double doubleValue(){
        return (double)numerator/(double)denominator;
    }

    @Override
    public long longValue(){
        return (long)numerator/(long)denominator;
    }

    @Override
    public float floatValue(){
        return (float)numerator/(float)denominator;
    }

    /**
     * Constructor for RationalNumber object.
     * Parses string and converts binary periodic number to a decimal number, and then to a rational number.
     * @param s binary periodic number as a string
     */
    public RationalNumber(String s){
        int length = s.length();

        // Replacing empty antiperiod, if any, with a revised one that indicates antiperiod = 0
        // Replacing a stray period (if there's just the characteristic), if any, with an empty string
        // Throws exception if string is an invalid string.
        if(s.equals("._"))
            throw new IllegalArgumentException("Not a valid string.");
        else{
            if(s.contains("._"))
                s = s.replace("._", ".0_");
            else if(s.indexOf(".") == length - 1){
                s = s.replace(".", "");
            }
        }

        System.out.println(s);

        double c_value = 0;
        double ap_value = 0;
        double p_value = 0;

        double anti_denom;
        double period_denom;

        // If characteristic, antiperiod, and period are present
        if(s.contains(".") && s.contains("_")){
            int dot = s.indexOf(".");
            int underline = s.indexOf("_");

            String character = s.substring(0, dot);
            String antiperiod = s.substring(dot + 1, underline);
            String period = s.substring(underline + 1, length);

            // The 3 for loops are to find the decimal value of the binary numbers.
            int expo = 0;
            for(int i = character.length() - 1; i >= 0; i--){
                int buffer_char = character.charAt(i) - '0';
                c_value += buffer_char * Math.pow(2, expo);
                expo++;
            }

            if(antiperiod.equals("0")){
                ap_value = 0;
                anti_denom = 1;
            }
            else{
                expo = 0;
                for(int i = antiperiod.length() - 1; i >= 0; i--){
                    int buffer_anti = antiperiod.charAt(i) - '0';
                    ap_value += buffer_anti * Math.pow(2, expo);
                    expo++;
                }
                anti_denom = Math.pow(2, antiperiod.length());
            }

            expo = 0;
            for(int i = period.length() - 1; i >= 0; i--){
                int buffer_period = period.charAt(i) - '0';
                p_value += buffer_period * Math.pow(2, expo);
                expo++;
            }

            period_denom = Math.pow(2, period.length()) - 1;

            // Creates new RationalNumber objects using the first constructor
            // based on the formula given to convert binary periodic number to rational
            RationalNumber c = new RationalNumber((int)c_value, 1);
            RationalNumber ap = new RationalNumber((int)ap_value, (int)anti_denom);
            RationalNumber p = new RationalNumber((int)p_value, (int)(period_denom * anti_denom));

            c.add(ap);
            c.add(p);

            numerator = c.getNumerator();
            denominator = c.getDenominator();
        }
        // Just the characteristic is present
        else if(!s.contains(".") && !s.contains("_")){
            int expo = 0;
            for(int i = s.length() - 1; i >= 0; i--){
                int buffer_char = s.charAt(i) - '0';
                c_value += buffer_char * Math.pow(2, expo);
                expo++;
            }

            RationalNumber c = new RationalNumber((int)c_value, 1);
            numerator = c.getNumerator();
            denominator = c.getDenominator();
        }
        // Characteristic and antiperiod are present
        else if(s.contains(".") && !s.contains("_")){
            int dot = s.indexOf(".");

            String character = s.substring(0, dot);
            String antiperiod = s.substring(dot + 1, length);

            int expo = 0;
            for(int i = character.length() - 1; i >= 0; i--){
                int buffer_char = character.charAt(i) - '0';
                c_value += buffer_char * Math.pow(2, expo);
                expo++;
            }

            expo = 0;
            for(int i = antiperiod.length() - 1; i >= 0; i--){
                int buffer_anti = antiperiod.charAt(i) - '0';
                ap_value += buffer_anti * Math.pow(2, expo);
                expo++;
            }
            anti_denom = Math.pow(2, antiperiod.length());

            RationalNumber c = new RationalNumber((int)c_value, 1);
            RationalNumber ap = new RationalNumber((int)ap_value, (int)anti_denom);

            c.add(ap);

            numerator = c.getNumerator();
            denominator = c.getDenominator();
        }

        if(denominator == 0)
            throw new IllegalArgumentException("Denominator cannot be zero!");

        if(denominator < 0){
            numerator = numerator * -1;
            denominator = denominator * -1;
        }

        simplify();
    }

    /**
     * Test method for RationalNumber
     * @param args
     */
    public static void main(String [] args){
        // Testing first constructor
        RationalNumber n1 = new RationalNumber(-35, 50);
        RationalNumber n2 = new RationalNumber(58, 70);
        System.out.println(n1.getNumerator() + "/" + n1.getDenominator());
        System.out.println(n2.getNumerator() + "/" + n2.getDenominator());

        n1.add(n2);
        System.out.println(n1.getNumerator() + "/" + n1.getDenominator());

        RationalNumber n3 = new RationalNumber(4, 7);
        RationalNumber n4 = new RationalNumber(-5, 10);

        n3.multiply(n4);
        System.out.println(n3.getNumerator() + "/" + n3.getDenominator());

        // Testing second constructor
        RationalNumber n5 = new RationalNumber("100._001");
        RationalNumber n6 = new RationalNumber("100.01_101");
        RationalNumber n7 = new RationalNumber("0.101");
        RationalNumber n8 = new RationalNumber("110.");

        System.out.println(n5.getNumerator() + "/" + n5.getDenominator());
        System.out.println(n6.getNumerator() + "/" + n6.getDenominator());
        System.out.println(n7.getNumerator() + "/" + n7.getDenominator());
        System.out.println(n8.getNumerator() + "/" + n8.getDenominator());

        RationalNumber n9 = new RationalNumber(1, 3);
        System.out.println(n9.doubleValue());
    }
}