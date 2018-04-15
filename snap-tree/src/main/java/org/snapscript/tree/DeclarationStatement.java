package org.snapscript.tree;

import static org.snapscript.core.result.Result.NORMAL;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;

public class DeclarationStatement implements Compilation {
   
   private final Statement declaration;   
   
   public DeclarationStatement(Modifier modifier, Declaration... declarations) {
      this.declaration = new CompileResult(modifier, declarations);     
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, declaration, trace);
   }
   
   private static class CompileResult extends Statement {

      private final Declaration[] declarations;
      private final Modifier modifier;
      
      public CompileResult(Modifier modifier, Declaration... declarations) {
         this.declarations = declarations;
         this.modifier = modifier;
      }  
      
      @Override
      public boolean define(Scope scope) throws Exception {
         ModifierType type = modifier.getType();
         
         for(Declaration declaration : declarations) {
            declaration.define(scope, type.mask); 
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         ModifierType type = modifier.getType();
         
         for(Declaration declaration : declarations) {
            declaration.compile(scope, type.mask); 
         }
         return new CompileExecution(type, declarations);
      }
   }
   
   private static class CompileExecution extends Execution {

      private final Declaration[] declarations;
      private final ModifierType modifier;
      
      public CompileExecution(ModifierType modifier, Declaration... declarations) {
         this.declarations = declarations;
         this.modifier = modifier;
      }  
      
      @Override
      public Result execute(Scope scope) throws Exception {
         for(Declaration declaration : declarations) {
            declaration.declare(scope, modifier.mask); 
         }
         return NORMAL;
      }
   }
}