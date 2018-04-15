package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;

import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.function.Signature;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.tree.function.ParameterList;

public class ConstructorAssembler {

   private final ConstructorSelector delegate; // this() or super()
   private final ParameterList parameters;
   private final Statement statement;

   public ConstructorAssembler(ParameterList parameters, TypePart part, Statement statement){  
      this.delegate = new ConstructorSelector(part);
      this.parameters = parameters;
      this.statement = statement;
   } 
   
   public ConstructorBuilder assemble(TypeBody body, Type type, Scope scope) throws Exception {
      TypeState internal = delegate.define(body, type, scope);
      Signature signature = parameters.create(scope, TYPE_CLASS);
      
      return new ConstructorBuilder(internal, signature, statement);
   }
}