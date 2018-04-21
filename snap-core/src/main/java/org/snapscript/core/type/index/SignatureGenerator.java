package org.snapscript.core.type.index;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationConverter;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.ParameterBuilder;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class SignatureGenerator {
   
   private final GenericConstraintExtractor extractor;
   private final AnnotationConverter converter;
   private final ParameterBuilder builder;
   
   public SignatureGenerator(TypeIndexer indexer) {
      this.extractor = new GenericConstraintExtractor(indexer);
      this.converter = new AnnotationConverter();
      this.builder = new ParameterBuilder();
   }

   public Signature generate(Type type, Method method) {
      Constraint[] constraints = extractor.extractParameters(method);
      Object[][] annotations = method.getParameterAnnotations();
      Module module = type.getModule();
      boolean variable = method.isVarArgs();      
      
      try {
         List<Parameter> parameters = new ArrayList<Parameter>();
   
         for(int i = 0; i < constraints.length; i++){
            boolean last = i + 1 == constraints.length;
            Constraint constraint = constraints[i];
            Parameter parameter = builder.create(constraint, i, variable && last);
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
         return new FunctionSignature(parameters, module, method, true, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create function for " + method, e);
      }
   }
   
   public Signature generate(Type type, Constructor constructor) {
      Constraint[] constraints = extractor.extractParameters(constructor);
      Object[][] annotations = constructor.getParameterAnnotations();
      Module module = type.getModule();
      boolean variable = constructor.isVarArgs();
      
      try {
         List<Parameter> parameters = new ArrayList<Parameter>();
   
         for(int i = 0; i < constraints.length; i++){
            boolean last = i + 1 == constraints.length;
            Constraint constraint = constraints[i];
            Parameter parameter = builder.create(constraint, i, variable && last);
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
         return new FunctionSignature(parameters, module, constructor, true, variable);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constructor for " + constructor, e);
      }
   }
}