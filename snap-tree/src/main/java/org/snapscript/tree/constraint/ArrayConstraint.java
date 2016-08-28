package org.snapscript.tree.constraint;

import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.reference.TypeReference;

public class ArrayConstraint implements Evaluation {

   private final TypeReference reference;
   private final StringToken[] bounds;
   
   public ArrayConstraint(TypeReference reference, StringToken... bounds) {
      this.reference = reference;
      this.bounds = bounds;
   }
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Value value = reference.evaluate(scope, null);
      Type entry = value.getValue();
      Type array = create(scope, entry);
      
      return ValueType.getTransient(array);
   }
   
   private Type create(Scope scope, Type entry) throws Exception {
      Module module = entry.getModule();
      Context context = module.getContext();
      TypeLoader loader = context.getLoader();
      String prefix = module.getName();
      String name = entry.getName();
      
      return loader.resolveType(prefix, name, bounds.length);
   }
}
