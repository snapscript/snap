package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.define.SuperExtractor;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.literal.TextLiteral;

public class SuperConstructor implements TypePart {
   
   private final SuperExtractor extractor;
   private final ArgumentList arguments;
   
   public SuperConstructor() {
      this(null, null);
   }
   
   public SuperConstructor(StringToken token) {
      this(token, null);
   }
   
   public SuperConstructor(ArgumentList arguments) {
      this(null, arguments);
   }
   
   public SuperConstructor(StringToken token, ArgumentList arguments) {
      this.extractor = new SuperExtractor();
      this.arguments = arguments;
   }
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      return null;
   }

   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      Type base = extractor.extractor(type);
      
      if(base == null) {
         throw new InternalStateException("No super type for '" + type + "'");
      }     
      return assemble(factory, base);
   }
   
   protected TypeFactory assemble(TypeFactory statements, Type type) throws Exception {
      StringToken name = new StringToken(TYPE_CONSTRUCTOR);
      Evaluation literal = new TextLiteral(name);
      Evaluation evaluation = new SuperInvocation(literal, arguments, type);
      
      return new SuperFactory(evaluation, type);
   }
}