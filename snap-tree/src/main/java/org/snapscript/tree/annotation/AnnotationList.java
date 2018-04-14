package org.snapscript.tree.annotation;

import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;

public class AnnotationList {
   
   private final AnnotationDeclaration[] list;

   public AnnotationList(AnnotationDeclaration... list) {
      this.list = list;
   }

   public void apply(Scope scope, Module module) throws Exception {
      List<Annotation> annotations = module.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Type type) throws Exception {
      List<Annotation> annotations = type.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Property property) throws Exception {
      List<Annotation> annotations = property.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Function function) throws Exception {
      List<Annotation> annotations = function.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
   
   public void apply(Scope scope, Parameter parameter) throws Exception {
      List<Annotation> annotations = parameter.getAnnotations();
      
      for(AnnotationDeclaration entry : list) {
         Value value = entry.evaluate(scope, null);
         Annotation annotation = value.getValue();
         
         annotations.add(annotation);
      }
   }
}