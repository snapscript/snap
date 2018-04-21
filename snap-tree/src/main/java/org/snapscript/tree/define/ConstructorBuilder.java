package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Statement;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.StatementInvocationBuilder;
import org.snapscript.tree.function.StatementInvocation;

public class ConstructorBuilder {
   
   private final TypeState delegate;
   private final Statement statement;
   private final Signature signature;

   public ConstructorBuilder(TypeState delegate, Signature signature, Statement statement) {
      this.signature = signature;
      this.statement = statement;
      this.delegate = delegate;
   }
   
   public Function create(TypeBody body, Type type, int modifiers) {
      return create(body, type, modifiers);
   }
   
   public FunctionBody create(TypeBody body, Type type, int modifiers, boolean compile) {
      Constraint none = new DeclarationConstraint(null);
      InvocationBuilder external = new StatementInvocationBuilder(signature, statement, none);
      Invocation invocation = new StatementInvocation(external);
      TypeAllocator instance = new ThisAllocator(body, invocation, type);
      InvocationBuilder internal = new TypeInvocationBuilder(delegate, signature, type);
      TypeAllocator base = new TypeDelegateAllocator(instance, internal); 
      Invocation constructor = new NewInvocation(body, base, type, compile);
      Constraint constraint = new DeclarationConstraint(type);
      Function function = new InvocationFunction(signature, constructor, type, constraint, TYPE_CONSTRUCTOR, modifiers | STATIC.mask, 1);
      
      return new FunctionBody(external, internal, function);
   }
}