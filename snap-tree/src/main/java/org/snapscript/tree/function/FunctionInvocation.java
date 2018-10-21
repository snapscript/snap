package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Bug;
import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationCache;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.function.dispatch.FunctionDispatcher;
import org.snapscript.core.link.ImplicitImportLoader;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.LocalScopeFinder;
import org.snapscript.core.scope.index.LocalValueFinder;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Constant;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.GenericList;
import org.snapscript.tree.constraint.GenericParameterExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class FunctionInvocation implements Compilation {

   private final Evaluation[] evaluations;
   private final NameReference identifier;
   private final ArgumentList arguments;
   private final GenericList generics;
   
   public FunctionInvocation(TextLiteral identifier, GenericList generics, ArgumentList arguments, Evaluation... evaluations) {
      this.identifier = new NameReference(identifier);
      this.evaluations = evaluations;
      this.arguments = arguments;
      this.generics = generics;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();     
      Trace trace = Trace.getInvoke(module, path, line);
      Evaluation invocation = create(module, path, line);
      
      return new TraceEvaluation(interceptor, invocation, trace);
   }
   
   private Evaluation create(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      String name = identifier.getName(scope);
      TypeExtractor extractor = context.getExtractor();
      FunctionBinder binder = context.getBinder();   
      FunctionMatcher matcher = binder.bind(name);
      
      return new CompileResult(matcher, extractor, generics, arguments, evaluations, name);     
   }
   
   private static class CompileResult extends Evaluation {   

      private final GenericParameterExtractor extractor;
      private final Evaluation[] evaluations; // func()[1][x]
      private final ImplicitImportLoader loader;
      private final LocalValueFinder finder;
      private final FunctionMatcher matcher;
      private final ArgumentList arguments;
      private final AtomicBoolean closure;
      private final AtomicInteger offset; 
      private final InvocationCache cache;   
      private final String name;
      
      public CompileResult(FunctionMatcher matcher, TypeExtractor extractor, GenericList generics, ArgumentList arguments, Evaluation[] evaluations, String name) {
         this.extractor = new GenericParameterExtractor(generics);
         this.cache = new InvocationCache(matcher, extractor);
         this.loader = new ImplicitImportLoader();
         this.finder = new LocalValueFinder(name);
         this.offset = new AtomicInteger(-1);
         this.closure = new AtomicBoolean();
         this.evaluations = evaluations;
         this.arguments = arguments;
         this.matcher = matcher;
         this.name = name;
      }
      
      @Override
      public void define(Scope scope) throws Exception {
         Index index = scope.getIndex();
         int depth = index.get(name);

         if(depth == -1) {
            loader.loadImports(scope, name);
         } else {
            offset.set(depth);
         }
         arguments.define(scope);
         
         for(Evaluation evaluation : evaluations) {
            evaluation.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         int depth = offset.get();
         Value value = finder.findFunction(scope, depth);
 
         if(value != null) { 
            Constraint constraint = value.getConstraint();
            Type type = constraint.getType(scope);

            if(type == null) {
               arguments.compile(scope); 
               return NONE;
            }
            return compile(scope, name, constraint);            
         }
         return compile(scope, name);         
      }
      
      private Constraint compile(Scope scope, String name) throws Exception {
         Type[] array = arguments.compile(scope); 
         Scope composite = extractor.extract(scope);
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(composite, NONE, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(composite, result);
         }
         return result; 
      }
      
      private Constraint compile(Scope scope, String name, Constraint local) throws Exception {
         Type[] array = arguments.compile(scope); 
         Scope composite = extractor.extract(scope);
         FunctionDispatcher dispatcher = matcher.match(scope);
         Constraint result = dispatcher.compile(composite, local, array);
         
         for(Evaluation evaluation : evaluations) {
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            result = evaluation.compile(composite, result);
         }
         return result; 
      }

      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         int depth = offset.get();
         Value value = finder.findFunction(scope, depth);
            
         if(value != null) { 
            Object object = value.getValue();
            Value constant = Constant.getConstant(value);
            
            if(Function.class.isInstance(object)) {
               return evaluate(scope, name, constant);
            }
         }
         return evaluate(scope, name);
      }

      private Value evaluate(Scope scope, String name) throws Exception {
         Object[] array = arguments.create(scope); 
         Invocation connection = cache.fetch(scope, array);
         Object object = connection.invoke(scope, NULL, array);
         Value value = Value.getTransient(object);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, value);
         }
         return value; 
      }
      
      private Value evaluate(Scope scope, String name, Value local) throws Exception {
         Object[] array = arguments.create(scope);
         Invocation connection = cache.fetch(scope, local, array);
         Object object = connection.invoke(scope, local, array);
         Value value = Value.getTransient(object);
         
         for(Evaluation evaluation : evaluations) {
            Object result = value.getValue();
            
            if(result == null) {
               throw new InternalStateException("Result of '" + name + "' is null"); 
            }
            value = evaluation.evaluate(scope, value);
         }
         return value; 
      }
   }
}