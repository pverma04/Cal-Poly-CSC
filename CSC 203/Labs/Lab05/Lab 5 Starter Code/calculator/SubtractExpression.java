class SubtractExpression extends BinaryExpression implements Expression {

   public SubtractExpression(final Expression lft, final Expression rht) {super(lft, rht, "-");}

//   public double evaluate(final Bindings bindings) {
//      return super.getLft().evaluate(bindings) - super.getRht().evaluate(bindings);
//   }
}

