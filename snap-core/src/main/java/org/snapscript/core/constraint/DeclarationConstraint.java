package org.snapscript.core.constraint;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class DeclarationConstraint extends Constraint {
   
   private final ConstraintDescription description;
   private final List<Constraint> generics;
   private final String name;
   private final Type type;
   private final int modifiers;

   public DeclarationConstraint(Type type) {
      this(type, EMPTY_LIST, 0);
   }
   
   public DeclarationConstraint(Type type, int modifiers) {
      this(type, EMPTY_LIST, modifiers);
   }
   
   public DeclarationConstraint(Type type, String name) {
      this(type, name, 0);
   }
   
   public DeclarationConstraint(Type type, String name, int modifiers) {
      this(type, EMPTY_LIST, name, modifiers);
   }
   
   public DeclarationConstraint(Type type, List<Constraint> generics) {
      this(type, generics, null, 0);
   }
   
   public DeclarationConstraint(Type type, List<Constraint> generics, int modifiers) {
      this(type, generics, null, modifiers);
   }
   
   @Bug("too many params")
   public DeclarationConstraint(Type type, List<Constraint> generics, String name, int modifiers) {
      this.description = new ConstraintDescription(this, type);
      this.modifiers = modifiers;
      this.generics = generics;
      this.type = type;
      this.name = name;
   }
   
   @Override
   public List<Constraint> getGenerics(Scope scope) {
      return generics;
   }

   @Override
   public Type getType(Scope scope) {
      return type;
   }
   
   @Override
   public String getName(Scope scope) {
      return name;
   }

   @Override
   public boolean isVariable(){
      return !ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isConstant(){
      return ModifierType.isConstant(modifiers);
   }
   
   @Override
   public boolean isClass(){
      return ModifierType.isClass(modifiers);
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}
