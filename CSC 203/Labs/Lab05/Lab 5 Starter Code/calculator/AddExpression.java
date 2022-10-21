class AddExpression extends BinaryExpression implements Expression {
   public AddExpression(final Expression lft, final Expression rht) { super(lft, rht, "+"); }

//   public double evaluate(final Bindings bindings) {
//      return super.getLft().evaluate(bindings) + super.getRht().evaluate(bindings);
//   }
}
