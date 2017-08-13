package org.snapscript.core.function;

import java.lang.reflect.Member;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Type;

public class Signature {
   
   private final List<Parameter> parameters;
   private final SignatureDescription description;
   private final SignatureMatcher matcher;
   private final Type definition;
   private final Member source;
   private final boolean variable;

   public Signature(List<Parameter> parameters, Module module, Member source){
      this(parameters, module, source, false);
   }
   
   public Signature(List<Parameter> parameters, Module module, Member source, boolean variable){
      this.description = new SignatureDescription(this);
      this.matcher = new SignatureMatcher(this, module);
      this.definition = new FunctionType(this, module);
      this.parameters = parameters;
      this.variable = variable;
      this.source = source;
   }
   
   public ArgumentConverter getConverter() {
      return matcher.getConverter();
   }
   
   public List<Parameter> getParameters(){
      return parameters;
   }
   
   public Type getDefinition() {
      return definition;
   }
   
   public Member getSource() {
      return source;
   }
   
   public boolean isVariable() {
      return variable;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}