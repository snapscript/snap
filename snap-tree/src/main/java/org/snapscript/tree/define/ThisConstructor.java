package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.Identity;
import org.snapscript.core.TypeFactory;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.construct.CreateObject;

public class ThisConstructor implements TypePart {
   
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
      return null;
   }
   
   @Override
   public TypeFactory validate(TypeFactory factory, Type type) throws Exception {
      return null;
   }
   
   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {  
      Statement statement = new StaticBody(factory, type);
      Evaluation reference = new Identity(type);
      CreateObject evaluation = new CreateObject(reference, arguments);
      
      return new ThisFactory(statement, evaluation);
   }
}