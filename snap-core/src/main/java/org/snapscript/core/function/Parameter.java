package org.snapscript.core.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class Parameter {
   
   private final List<Annotation> annotations;
   private final String name;
   private final Type type;
   private final boolean variable;
   
   public Parameter(String name, Type type){
      this(name, type, false);
   }
   
   public Parameter(String name, Type type, boolean variable){
      this.annotations = new ArrayList<Annotation>();
      this.variable = variable;
      this.name = name;
      this.type = type;
   }
   
   public boolean isVariable() {
      return variable;
   }
   
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   public String getName() {
      return name;
   }
   
   public Type getType() {
      return type;
   }
}  