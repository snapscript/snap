package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
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
   public Initializer define(Initializer initializer, Type type) throws Exception {
      return null;
   }
   
   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {  
      Statement statement = new StaticBody(initializer, type);
      Evaluation reference = new TypeValue(type);
      CreateObject evaluation = new CreateObject(reference, arguments);
      
      return new ThisInitializer(statement, evaluation);
   }
}