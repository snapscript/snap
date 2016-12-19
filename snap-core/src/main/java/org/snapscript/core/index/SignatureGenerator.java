package org.snapscript.core.index;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationConverter;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.thread.ThreadStack;
import org.snapscript.core.thread.ThreadState;

public class SignatureGenerator {
   
   private final AnnotationConverter converter;
   private final ParameterBuilder builder;
   private final TypeIndexer indexer;
   private final ThreadStack stack;
   
   public SignatureGenerator(TypeIndexer indexer, ThreadStack stack) {
      this.converter = new AnnotationConverter();
      this.builder = new ParameterBuilder();
      this.indexer = indexer;
      this.stack = stack;
   }

   public Signature generate(Type type, Method method) {
      Class[] types = method.getParameterTypes();
      Object[][] annotations = method.getParameterAnnotations();
      Module module = type.getModule();
      ThreadState state = stack.state();
      boolean variable = method.isVarArgs();
      
      try {
         List<Parameter> parameters = new ArrayList<Parameter>();
   
         for(int i = 0; i < types.length; i++){
            boolean last = i + 1 == types.length;
            Type match = indexer.loadType(types[i]);
            Parameter parameter = builder.create(match, i, variable && last);
            Object[] list = annotations[i];
            
            if(list.length > 0) {
               List<Annotation> actual = parameter.getAnnotations();
               
               for(int j = 0; j < list.length; j++) {
                  Object value = list[j];
                  Object result = converter.convert(value);
                  Annotation annotation = (Annotation)result;
                  
                  actual.add(annotation);
               }
            }
            parameters.add(parameter);
         }
         return new Signature(parameters, module, state, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
   
   public Signature generate(Type type, Constructor constructor) {
      Class[] types = constructor.getParameterTypes();
      Object[][] annotations = constructor.getParameterAnnotations();
      Module module = type.getModule();
      ThreadState state = stack.state();
      boolean variable = constructor.isVarArgs();
      
      try {
         List<Parameter> parameters = new ArrayList<Parameter>();
   
         for(int i = 0; i < types.length; i++){
            boolean last = i + 1 == types.length;
            Type match = indexer.loadType(types[i]);
            Parameter parameter = builder.create(match, i, variable && last);
            Object[] list = annotations[i];
            
            if(list.length > 0) {
               List<Annotation> actual = parameter.getAnnotations();
               
               for(int j = 0; j < list.length; j++) {
                  Object value = list[j];
                  Object result = converter.convert(value);
                  Annotation annotation = (Annotation)result;
                  
                  actual.add(annotation);
               }
            }
            parameters.add(parameter);
         }
         return new Signature(parameters, module, state, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constructor for " + constructor, e);
      }
   }
}
