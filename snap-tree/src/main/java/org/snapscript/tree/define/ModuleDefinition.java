package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_THIS;
import static org.snapscript.core.type.Phase.COMPILE;
import static org.snapscript.core.type.Phase.CREATE;
import static org.snapscript.core.type.Phase.DEFINE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.common.Progress;
import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.type.Phase;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.annotation.AnnotationList;

public class ModuleDefinition implements Compilation { 
   
   private final Statement definition;

   public ModuleDefinition(AnnotationList annotations, ModuleName module, ModulePart... parts) {
      this.definition = new CompileResult(annotations, module, parts);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getDefine(module, path, line);
      
      return new TraceStatement(interceptor, handler, definition, trace);
   }
   
   private static class CompileResult extends Statement {   
   
      private final AtomicReference<Module> reference;
      private final ModuleBuilder builder;
      private final Statement body;
      
      public CompileResult(AnnotationList annotations, ModuleName module, ModulePart... parts) {
         this.builder = new ModuleBuilder(annotations, module);
         this.reference = new AtomicReference<Module>();
         this.body = new ModuleBody(parts);
      }
      
      @Override
      public void create(Scope scope) throws Exception {
         Module module = builder.create(scope);
         Progress<Phase> progress = module.getProgress();
         Scope inner = module.getScope();
         
         try {
            reference.set(module);
            body.create(inner);
         } finally {
            progress.done(CREATE);
         }
      }
   
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = reference.get();
         Progress<Phase> progress = module.getProgress();
         Value value = Value.getTransient(module, module);
         Scope inner = module.getScope();
         State state = inner.getState();
         
         try {
            state.add(TYPE_THIS, value);
            body.define(inner); // must be module scope
         } finally {
            progress.done(DEFINE);
         }         
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         Module module = reference.get();
         Progress<Phase> progress = module.getProgress();
         Scope inner = module.getScope();
         Scope local = inner.getStack();
         
         try {
            return body.compile(local, null); // must be module scope
         } finally {
            progress.done(COMPILE);
         }
      }
   }
}