class MultiplyExpression extends BinaryExpression implements Expression {
   public MultiplyExpression(final Expression lft, final Expression rht) {super(lft, rht, "*");}

//   public double evaluate(final Bindings bindings) {
//      return super.getLft().evaluate(bindings) * super.getRht().evaluate(bindings);
//   }
}

