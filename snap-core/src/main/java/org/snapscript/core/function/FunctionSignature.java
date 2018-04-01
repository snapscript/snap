package org.snapscript.core.function;

import java.lang.reflect.Member;
import java.util.List;

import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public class FunctionSignature implements Signature {
   
   private final List<Parameter> parameters;
   private final SignatureDescription description;
   private final SignatureMatcher matcher;
   private final Type definition;
   private final Member source;
   private final boolean absolute;
   private final boolean variable;
   
   public FunctionSignature(List<Parameter> parameters, Module module, Member source){
      this(parameters, module, source, true);
   }
   
   public FunctionSignature(List<Parameter> parameters, Module module, Member source, boolean absolute){
      this(parameters, module, source, absolute, false);
   }
   
   public FunctionSignature(List<Parameter> parameters, Module module, Member source, boolean absolute, boolean variable){
      this.description = new SignatureDescription(this);
      this.matcher = new SignatureMatcher(this, module);
      this.definition = new FunctionType(this, module);
      this.parameters = parameters;
      this.absolute = absolute;
      this.variable = variable;
      this.source = source;
   }
   
   @Override
   public ArgumentConverter getConverter() {
      return matcher.getConverter();
   }
   
   @Override
   public List<Parameter> getParameters(){
      return parameters;
   }
   
   @Override
   public Type getDefinition() {
      return definition;
   }
   
   @Override
   public Member getSource() {
      return source;
   }
   
   @Override
   public boolean isVariable() {
      return variable;
   }
   
   @Override
   public boolean isAbsolute() {
      return absolute; // array parameters are not absolute
   }
   
   @Override
   public boolean isInvalid() {
      return false;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}