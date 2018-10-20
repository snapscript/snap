package org.snapscript.tree.construct;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.error.Reason.CONSTRUCTION;
import static org.snapscript.core.variable.Value.NULL;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.CompileConstraint;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.AliasResolver;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.link.ImplicitImportLoader;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;

public class CreateObject extends Evaluation {   
   
   private final ConstructArgumentList arguments;
   private final ImplicitImportLoader loader;
   private final FunctionResolver resolver;
   private final ErrorHandler handler;
   private final AliasResolver alias;
   private final Constraint constraint;
   private final int violation; // what modifiers are illegal

   public CreateObject(FunctionResolver resolver, ErrorHandler handler, Constraint constraint, ArgumentList arguments, int violation) {
      this.arguments = new ConstructArgumentList(constraint, arguments);
      this.constraint = new CompileConstraint(constraint);
      this.loader = new ImplicitImportLoader();
      this.alias = new AliasResolver();
      this.violation = violation;
      this.resolver = resolver;
      this.handler = handler;
   }      

   @Override
   public void define(Scope scope) throws Exception {
      List<String> names = constraint.getImports(scope);
      int count = names.size();
      
      if(count > 0) {
         loader.loadImports(scope, names);
      }
      arguments.define(scope);
   }
   
   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      Type type = constraint.getType(scope);
      Type actual = alias.resolve(type);
      int modifiers = actual.getModifiers();
      
      if((violation & modifiers) != 0) {
         handler.handleCompileError(CONSTRUCTION, scope, actual);
      }
      return arguments.compile(scope, actual);
   }   
   
   @Override
   public Value evaluate(Scope scope, Value left) throws Exception { 
      Type type = constraint.getType(scope);
      Type actual = alias.resolve(type);
      Object[] list = arguments.create(scope, actual);
      FunctionCall call = resolver.resolveStatic(scope, actual, TYPE_CONSTRUCTOR, list);
      
      if(call == null){
         handler.handleRuntimeError(CONSTRUCTION, scope, actual, TYPE_CONSTRUCTOR, list);
      }
      Object result = call.invoke(scope, null, list);
      
      if(result != null) {
         return Value.getTransient(result);
      }
      return NULL;
   }
}