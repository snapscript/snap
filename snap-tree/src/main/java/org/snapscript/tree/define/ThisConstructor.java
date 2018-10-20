package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.ENUM;
import static org.snapscript.core.ModifierType.MODULE;
import static org.snapscript.core.ModifierType.TRAIT;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.StaticConstraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.construct.CreateObject;

public class ThisConstructor implements Compilation {

   private final ArgumentList arguments;
   
   public ThisConstructor(StringToken token) {
      this(token, null);
   }
   
   public ThisConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public ThisConstructor(StringToken token, ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   @Override
   public TypePart compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      FunctionResolver resolver = context.getResolver();
      
      return new CompileResult(resolver, handler, arguments);
   }
   
   private static class CompileResult extends TypePart {
   
      private final FunctionResolver resolver;
      private final ArgumentList arguments;
      private final ErrorHandler handler;
      
      public CompileResult(FunctionResolver resolver, ErrorHandler handler, ArgumentList arguments) {
         this.arguments = arguments;
         this.resolver = resolver;
         this.handler = handler;
      }
      
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {  
         Execution execution = new StaticBody(body, type);
         Constraint constraint = new StaticConstraint(type);
         CreateObject evaluation = new CreateObject(resolver, handler, constraint, arguments, TRAIT.mask | ENUM.mask | MODULE.mask);
         
         return new ThisState(execution, evaluation);
      }   
   }
}