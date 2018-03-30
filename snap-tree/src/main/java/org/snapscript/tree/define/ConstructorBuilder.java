package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.constraint.ConstantConstraint;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.StatementInvocationBuilder;
import org.snapscript.tree.function.StatementInvocation;

public class ConstructorBuilder {
   
   private final TypeFactory delegate;
   private final Statement statement;
   private final Signature signature;

   public ConstructorBuilder(TypeFactory delegate, Signature signature, Statement statement) {
      this.signature = signature;
      this.statement = statement;
      this.delegate = delegate;
   }
   
   public Function create(TypeFactory factory, Type type, int modifiers) {
      return create(factory, type, modifiers);
   }
   
   public FunctionHandle create(TypeFactory factory, Type type, int modifiers, boolean compile) {
      Constraint none = new ConstantConstraint(null);
      InvocationBuilder external = new StatementInvocationBuilder(signature, statement, none);
      Invocation body = new StatementInvocation(external);
      TypeAllocator instance = new ThisAllocator(factory, body, type);
      InvocationBuilder internal = new TypeInvocationBuilder(delegate, signature, type);
      TypeAllocator base = new TypeDelegateAllocator(instance, internal); 
      Invocation constructor = new NewInvocation(factory, base, type, compile);
      Constraint constraint = new ConstantConstraint(type);
      Function function = new InvocationFunction(signature, constructor, type, constraint, TYPE_CONSTRUCTOR, modifiers | STATIC.mask, 1);
      
      return new FunctionHandle(external, internal, function);
   }
}