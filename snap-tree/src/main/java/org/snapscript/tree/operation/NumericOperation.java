package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.core.convert.AliasResolver;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.parse.Token;
import org.snapscript.tree.math.NumericChecker;

public abstract class NumericOperation extends Evaluation {

   protected final AliasResolver resolver;
   protected final Evaluation evaluation;
   protected final Token operator;
   
   protected NumericOperation(Evaluation evaluation, Token operator) {
      this.resolver = new AliasResolver();
      this.evaluation = evaluation;
      this.operator = operator;
   }
   
   @Override
   public void define(Scope scope) throws Exception {
      evaluation.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Constraint constraint = evaluation.compile(scope, left);
      Type type = constraint.getType(scope);
      
      if(constraint.isConstant()) {
         throw new InternalStateException("Illegal " + operator+ " of constant");
      }
      if(type != null) {
         Type real = resolver.resolve(type);

         if(!NumericChecker.isNumeric(real)) {
            throw new InternalStateException("Illegal " + operator +" of type '" + type + "'");
         }
      }
      return constraint;      
   }
}