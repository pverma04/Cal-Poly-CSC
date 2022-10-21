public class BinaryExpression implements Expression {
    private final Expression lft;
    private final Expression rht;
    private String operator;
    public BinaryExpression(final Expression lft, final Expression rht, String operator) {
        this.lft = lft;
        this.rht = rht;
        this.operator = operator;
    }
    public Expression getLft() { return this.lft; }
    public Expression getRht() { return this.rht; }
    public String toString() { return "(" + lft + " " + operator + " " + rht + ")"; }
    @Override
    public double evaluate(Bindings bindings) {

//        switch (this.operator){
//            case "+":
//                return lft.evaluate(bindings) + rht.evaluate(bindings);
//            case "-":
//                return lft.evaluate(bindings) - rht.evaluate(bindings);
//            case "*":
//                return lft.evaluate(bindings) * rht.evaluate(bindings);
//            case "/":
//                return lft.evaluate(bindings) / rht.evaluate(bindings);
//        }
        return 0;
    }
}
