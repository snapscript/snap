package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.Identity;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.construct.CreateObject;

public class ThisConstructor extends TypePart {
   
   private final ArgumentList arguments;
   
   public ThisConstructor(StringToken token) {
      this(token, null);
   }
   
   public ThisConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public ThisConstructor(StringToken token, ArgumentList arguments) {
      this.arguments = arguments;
   }
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {  
      Execution execution = new StaticBody.StaticExecution(factory, type);
      Evaluation reference = new Identity(type, type);
      CreateObject evaluation = new CreateObject(reference, arguments);
      
      return new ThisFactory(execution, evaluation);
   }   
}