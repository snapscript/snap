package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.SuperExtractor;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.literal.TextLiteral;

public class SuperConstructor extends TypePart {
   
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
   public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
      Type base = extractor.extractor(type);
      
      if(base == null) {
         throw new InternalStateException("No super type for '" + type + "'");
      }     
      return assemble(body, base, scope);
   }

   protected TypeState assemble(TypeBody body, Type type, Scope scope) throws Exception {
      StringToken name = new StringToken(TYPE_CONSTRUCTOR);
      Evaluation literal = new TextLiteral(name);
      Evaluation evaluation = new SuperInvocation(literal, arguments, type);
      
      return new SuperState(evaluation, type);
   }
}