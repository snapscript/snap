package org.snapscript.tree.condition;

import static org.snapscript.core.ModifierType.VARIABLE;
import static org.snapscript.core.result.Result.NORMAL;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.core.variable.Value;
import org.snapscript.core.yield.Resume;
import org.snapscript.core.yield.Yield;
import org.snapscript.tree.Declaration;
import org.snapscript.tree.SuspendStatement;
import org.snapscript.tree.collection.Iteration;
import org.snapscript.tree.collection.IterationConverter;

public class ForInStatement implements Compilation {
   
   private final Statement loop;
   
   public ForInStatement(Declaration declaration, Evaluation collection, Statement body) {
      this.loop = new CompileResult(declaration, collection, body);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends Statement {
   
      private final Declaration declaration;
      private final Evaluation collection;
      private final AtomicInteger offset;
      private final Statement body;
   
      public CompileResult(Declaration declaration, Evaluation collection, Statement body) {
         this.offset = new AtomicInteger();
         this.declaration = declaration;
         this.collection = collection;
         this.body = body;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception { 
         Index index = scope.getIndex();
         int size = index.size();
         
         try {   
            int depth = declaration.define(scope, VARIABLE.mask);
            
            collection.define(scope);            
            body.define(scope);
            offset.set(depth);
         } finally {
            index.reset(size);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception { 
         Index index = scope.getIndex();
         int size = index.size();
         
         try {  
            Value variable = declaration.compile(scope, VARIABLE.mask);
            Execution execution = body.compile(scope, returns);
            int depth = offset.get();
            
            collection.compile(scope, null);
            
            return new CompileExecution(declaration, collection, execution, depth);
         } finally {
            index.reset(size);
         }
      }
   }
   
   private static class CompileExecution extends SuspendStatement<Iterator> {
      
      private final IterationConverter converter;
      private final Declaration declaration;
      private final Evaluation collection;
      private final Execution body;
      private final int depth;
   
      public CompileExecution(Declaration declaration, Evaluation collection, Execution body, int depth) {
         this.converter = new IterationConverter();
         this.declaration = declaration;
         this.collection = collection;      
         this.depth = depth;
         this.body = body;
      }
   
      @Override
      public Result execute(Scope scope) throws Exception { 
         Value list = collection.evaluate(scope, null);
         Value value = declaration.declare(scope, VARIABLE.mask);
         Object object = list.getValue();
         Iteration iteration = converter.convert(scope, object);
         Iterable iterable = iteration.getIterable(scope);
         Iterator iterator = iterable.iterator();
         
         return resume(scope, iterator);
      }

      @Override
      public Result resume(Scope scope, Iterator iterator) throws Exception {
         Table table = scope.getTable();
         Local local = table.get(depth);
         
         while (iterator.hasNext()) {
            Object entry = iterator.next();

            local.setValue(entry);
            
            Result result = body.execute(scope);   
   
            if(result.isYield()) {
               return suspend(scope, result, this, iterator);
            }
            if (result.isReturn()) {
               return result;
            }
            if (result.isBreak()) {
               return NORMAL;
            }
         }    
         return NORMAL;
      }

      @Override
      public Resume suspend(Result result, Resume resume, Iterator value) throws Exception {
         Yield yield = result.getValue();
         Resume child = yield.getResume();
         
         return new ForInResume(child, resume, value);
      }
   }
   
}