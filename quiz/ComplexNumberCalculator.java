
class ComplexNumber {
    private int real;
    private int imaginary;

    public ComplexNumber(int real, int imaginary) {
        this.real=real;
        this.imaginary=imaginary;
    }

    public int getReal() {
        return real;
    }

    public int getImaginary() {
        return imaginary;
    }
    public void  setReal(int real) {
        this.real=real;
    }

    public void setImaginary(int imaginary) {
        this.imaginary=imaginary;
    }

    @Override
    public String toString()  {
        // it is for when we return the object itself in the ComplexNumberCalculator , the given output will be given.
        //This function make the desired output happen.
        if (imaginary<0) {
            return real+""+imaginary+"i";
        } else {
            return real+"+"+imaginary+"i";
        }
    }
}


public class ComplexNumberCalculator {
    private final ComplexNumber number1; // I know it was enough to make private but my compiler keeps warning me about making it final, since the values did not change...
    private final ComplexNumber number2; // ... throughout the program I made it final.


    public ComplexNumberCalculator(int real1, int img1, int real2, int img2) {
        this.number1=new ComplexNumber(real1,img1);
        this.number2=new ComplexNumber(real2,img2);
    }


    public boolean equal() {
        //a = c and b = d
        return (number1.getReal()==number2.getReal() &&
                number1.getImaginary()==number2.getImaginary());
    }


    public ComplexNumber sum() {
        //(a+c)+(b+d)i
        int real=number1.getReal()+number2.getReal();
        int imaginary=number1.getImaginary()+number2.getImaginary();
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber mul() {
        // (a + bi)(c + di) = (ac - bd) + (ad + bc)i
        int real=number1.getReal()*number2.getReal()-number1.getImaginary()*number2.getImaginary();
        int imaginary=number1.getReal()*number2.getImaginary() +number1.getImaginary()*number2.getReal();
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber sub() {
        // (a−c)+(b−d)i
        int real =number1.getReal()-number2.getReal();
        int imaginary =number1.getImaginary()-number2.getImaginary();
        return new ComplexNumber(real, imaginary);
    }

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage:java ComplexNumberCalculator  <real1> <img1> <real2> <img2> <operation>");
            return;
        }
            int real1 = Integer.parseInt(args[0]); // to make the string values integer.
            int img1 = Integer.parseInt(args[1]);
            int real2 = Integer.parseInt(args[2]);
            int img2 = Integer.parseInt(args[3]);
            String operation = args[4].toLowerCase(); // if the given operation is typed as upper case, this line makes the program not give any error about it by making all the lower case

            ComplexNumberCalculator calculate = new ComplexNumberCalculator(real1, img1, real2, img2);
            switch (operation) {
                case "add":
                    System.out.println(calculate.sum());
                    break;
                case "sub":
                    System.out.println(calculate.sub());
                    break;
                case "mul":
                    System.out.println(calculate.mul());
                    break;
                case "equal":
                    System.out.println(calculate.equal());
                    break;
                default:
                    System.out.println("Invalid operation.Use add, sub, mul or equal.");
                    break;
            }
    }
}