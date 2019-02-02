package org.snapscript.core.type.extend;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Origin;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class FunctionExtractor {
   
   private final ParameterBuilder builder;
   private final TypeLoader loader;
   private final Origin origin;
   
   public FunctionExtractor(TypeLoader loader, Origin origin){
      this.builder = new ParameterBuilder();
      this.loader = loader;
      this.origin = origin;
   }

   public List<Function> extract(Module module, Class extend, Class extension) throws Exception {
      Type source = loader.loadType(extension);
      List<Function> functions = source.getFunctions();
      Scope scope = module.getScope();
      
      if(!functions.isEmpty()) {
         List<Function> adapters = new ArrayList<Function>();
         
         for(Function function : functions) {
            Signature signature = function.getSignature();
            List<Parameter> parameters = signature.getParameters();
            
            if(!parameters.isEmpty()) {
               Parameter parameter = parameters.get(0);
               Constraint constraint = parameter.getConstraint();
               Type type = constraint.getType(scope);
               Class real = type.getType();
            
               if(real == extend) {
                  Function adapter = extract(module, extend, extension, function);
                  
                  if(adapter != null) {
                     adapters.add(adapter);
                  }
               }
            }
         }
         return adapters;
      }
      return Collections.emptyList();
   }

   private Function extract(Module module, Class extend, Class extension, Function function) {
      Invocation invocation = function.getInvocation();
      Signature signature = function.getSignature();
      List<Constraint> generics = signature.getGenerics();
      List<Parameter> parameters = signature.getParameters();
      Member member = signature.getSource();
      boolean variable = signature.isVariable();
      int length = parameters.size();
   
      if(length > 0) {
         List<Parameter> copy = new ArrayList<Parameter>();
         Signature reduced = new FunctionSignature(copy, generics, module, member, origin, true, variable);
         Invocation adapter = new ExportInvocation(invocation, extend, extension);
         
         for(int i = 1; i < length; i++) {
            Parameter parameter = parameters.get(i);
            Constraint constraint = parameter.getConstraint();
            Parameter duplicate = builder.create(constraint, i - 1);
            
            copy.add(duplicate);
         }
         String name = function.getName();
         Type source = function.getSource();
         Constraint returns = function.getConstraint();
         int modifiers = function.getModifiers();
         
         return new InvocationFunction(reduced, adapter, source, returns, name, modifiers); 
      }
      return null;
   }

   private static class ExportInvocation implements Invocation<Object>{

      private final ExportInstance instance;
      private final Invocation invocation;
      private final Class extend;
      
      public ExportInvocation(Invocation invocation, Class extend, Class extension) {
         this.instance = new ExportInstance(extension);
         this.invocation = invocation;
         this.extend = extend;
      }
      
      @Override
      public Object invoke(Scope scope, Object left, Object... list) throws Exception {
         Object[] arguments = new Object[list.length + 1];
         
         for(int i = 0; i < list.length; i++) {
            arguments[i + 1] = list[i];
         }
         Object target = instance.getInstance();

         if(extend == Scope.class) {
            arguments[0] = scope;
         } else {
            arguments[0] = left;
         }
         return invocation.invoke(scope, target, arguments);
      }
   }

   private static class ExportInstance {

      private Class extension;
      private Object instance;

      public ExportInstance(Class extension) {
         this.extension = extension;
      }

      public Object getInstance() throws Exception {
         if(instance == null) {
            int modifiers = extension.getModifiers();

            if(Modifier.isAbstract(modifiers)) {
               throw new IllegalStateException("Extension " + extension + " is abstract");
            }
            if(Modifier.isInterface(modifiers)) {
               throw new IllegalStateException("Extension " + extension + " is an interface");
            }
            instance = extension.newInstance();
         }
         return instance;
      }
   }
}