package org.snapscript.tree.constraint;

import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;

import java.util.List;

public class FunctionName implements GenericName {

   private final NameReference reference;
   private final GenericList generics;

   public FunctionName(TextLiteral literal, GenericList generics) {
      this.reference = new NameReference(literal);
      this.generics = generics;
   }

   @Override
   public String getName(Scope scope) throws Exception{ // called from outer class
      return reference.getName(scope);
   }

   @Override
   public List<Constraint> getGenerics(Scope scope) throws Exception {
      List<Constraint> constraints = generics.getGenerics(scope);
      Module module = scope.getModule();
      ImportManager manager = module.getManager();

      for(Constraint constraint : constraints) {
         Type type = constraint.getType(scope);
         String alias = constraint.getName(scope);

         if(alias != null) {
            Type parent = scope.getType();
            String prefix = parent.getName();

            manager.addImport(type, prefix +'$' +alias);
         }
      }
      return constraints;
   }
}