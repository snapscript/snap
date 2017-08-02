package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CLASS;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.function.ParameterList;

public class ConstructorAssembler {

   private final ConstructorSelector delegate; // this() or super()
   private final ParameterList parameters;
   private final Statement body;

   public ConstructorAssembler(ParameterList parameters, TypePart part, Statement body){  
      this.delegate = new ConstructorSelector(part);
      this.parameters = parameters;
      this.body = body;
   } 
   
   public ConstructorBuilder assemble(TypeFactory factory, Type type) throws Exception {
      Scope scope = type.getScope();
      TypeFactory internal = delegate.compile(factory, type);
      Signature signature = parameters.create(scope, TYPE_CLASS);
      
      return new ConstructorBuilder(internal, signature, body);
   }
}