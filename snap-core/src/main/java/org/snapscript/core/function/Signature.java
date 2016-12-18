package org.snapscript.core.function;

import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.State;
import org.snapscript.core.Type;

public class Signature {
   
   private final List<Parameter> parameters;
   private final SignatureMatcher matcher;
   private final Type definition;
   private final boolean variable;

   public Signature(List<Parameter> parameters, Module module, State stack){
      this(parameters, module, stack, false);
   }
   
   public Signature(List<Parameter> parameters, Module module, State stack, boolean variable){
      this.matcher = new SignatureMatcher(this, module);
      this.definition = new FunctionType(this, module, stack);
      this.parameters = parameters;
      this.variable = variable;
   }
   
   public Type getDefinition() {
      return definition;
   }
   
   public ArgumentConverter getConverter() {
      return matcher.getConverter();
   }
   
   public List<Parameter> getParameters(){
      return parameters;
   }
   
   public boolean isVariable() {
      return variable;
   }
}