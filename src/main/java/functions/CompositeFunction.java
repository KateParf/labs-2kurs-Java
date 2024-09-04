package functions;

public class CompositeFunction implements MathFunction{
    private MathFunction firstFunction;
    private MathFunction secondFunction;

    CompositeFunction(MathFunction f, MathFunction g){
        this.firstFunction = f;
        this.secondFunction = g;
    }
    public double apply(double x) {
        return secondFunction.apply(firstFunction.apply(x));
    }
}
