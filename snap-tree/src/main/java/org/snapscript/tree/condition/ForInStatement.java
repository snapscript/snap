package org.snapscript.tree.condition;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.Local;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Resume;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Table;
import org.snapscript.core.Value;
import org.snapscript.core.Yield;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.SuspendStatement;
import org.snapscript.tree.collection.Iteration;
import org.snapscript.tree.collection.IterationConverter;

public class ForInStatement implements Compilation {
   
   private final Statement loop;
   
   public ForInStatement(Evaluation identifier, Evaluation collection, Statement body) {
      this.loop = new CompileResult(identifier, collection, body);
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, loop, trace);
   }
   
   private static class CompileResult extends SuspendStatement<Iterator> {
   
      private final IterationConverter converter;
      private final NameReference reference;
      private final Evaluation collection;
      private final AtomicInteger offset;
      private final Statement body;
   
      public CompileResult(Evaluation identifier, Evaluation collection, Statement body) {
         this.reference = new NameReference(identifier);
         this.converter = new IterationConverter();
         this.offset = new AtomicInteger();
         this.collection = collection;
         this.body = body;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception { 
         String name = reference.getName(scope);
         Index index = scope.getIndex();
         int size = index.size();
         int depth = index.index(name);
         
         try {   
            collection.compile(scope);
            offset.set(depth);
            
            return body.compile(scope);
         } finally {
            index.reset(size);
         }
      }
   
      @Override
      public Result execute(Scope scope) throws Exception { 
         Value list = collection.evaluate(scope, null);
         Object object = list.getValue();
         Iteration iteration = converter.convert(scope, object);
         Iterable iterable = iteration.getIterable(scope);
         Iterator iterator = iterable.iterator();
         
         return resume(scope, iterator);
      }

      @Override
      public Result resume(Scope scope, Iterator iterator) throws Exception {
         String name = reference.getName(scope);
         Table table = scope.getTable();
         Local local = Local.getReference(name, name);
         int depth = offset.get();
         
         table.add(depth, local);
         
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
               return Result.getNormal();
            }
         }    
         return Result.getNormal();
      }

      @Override
      public Resume create(Result result, Resume resume, Iterator value) throws Exception {
         Yield yield = result.getValue();
         
      }
   }
   
}