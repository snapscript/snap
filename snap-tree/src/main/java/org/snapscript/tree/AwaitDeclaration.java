package org.snapscript.tree;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.ScopeIndex;
import org.snapscript.core.scope.index.ScopeTable;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.literal.TextLiteral;

public class AwaitDeclaration extends Evaluation {

   private final AtomicReference<Address> location;
   private final DeclarationAllocator allocator;
   private final NameReference reference;
   private final ModifierList list;

   public AwaitDeclaration(Modifier modifier, TextLiteral identifier) {
      this(modifier, identifier, null);
   }

   public AwaitDeclaration(Modifier modifier, TextLiteral identifier, Constraint constraint) {
      this.allocator = new AwaitReferenceAllocator(constraint);
      this.reference = new NameReference(identifier);
      this.location = new AtomicReference<Address>();
      this.list = new ModifierList(modifier);
   }

   @Override
   public void define(Scope scope) throws Exception {
      String name = reference.getName(scope);
      ScopeIndex index = scope.getIndex();
      Address address = index.index(name);

      location.set(address);
   }

   @Override
   public Constraint compile(Scope scope, Constraint left) throws Exception {
      int modifiers = list.getModifiers();
      String name = reference.getName(scope);
      Local local = allocator.compile(scope, name, modifiers);
      ScopeTable table = scope.getTable();
      Address address = location.get();

      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return NONE;
   }

   @Override
   public Value evaluate(Scope scope, Value left) throws Exception {
      int modifiers = list.getModifiers();
      String name = reference.getName(scope);
      Local local = allocator.allocate(scope, name, modifiers);
      ScopeTable table = scope.getTable();
      Address address = location.get();

      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }
      return local;
   }
}
