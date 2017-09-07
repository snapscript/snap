package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationBuilder;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.FunctionCompiler;
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
   
   public FunctionCompiler create(TypeFactory factory, Type type, int modifiers, boolean compile) {
      InvocationBuilder external = new StatementInvocationBuilder(signature, statement, statement, null);
      Invocation body = new StatementInvocation(external);
      TypeAllocator instance = new ThisAllocator(factory, body, type);
      InvocationBuilder internal = new TypeInvocationBuilder(delegate, signature, type);
      TypeAllocator base = new TypeDelegateAllocator(instance, internal); 
      Invocation constructor = new NewInvocation(factory, base, type, compile);
      Function function = new InvocationFunction(signature, constructor, type, type, TYPE_CONSTRUCTOR, modifiers | STATIC.mask, 1);
      
      return new FunctionCompiler(external, function);
   }
}