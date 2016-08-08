package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ScopeAccessor;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class MemberField implements TypePart {

   private final MemberFieldDeclaration declaration;
   private final ConstraintExtractor extractor;
   private final AnnotationList annotations;
   private final ModifierChecker checker;
   private final TextLiteral identifier;
   private final ModifierList list;
   
   public MemberField(AnnotationList annotations, ModifierList list, TextLiteral identifier) {
      this(annotations, list, identifier, null, null);
   }

   public MemberField(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint) {
      this(annotations, list, identifier, constraint, null);
   }

   public MemberField(AnnotationList annotations, ModifierList list, TextLiteral identifier, Evaluation value) {
      this(annotations, list, identifier, null, value);
   }

   public MemberField(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new MemberFieldDeclaration(list, identifier, constraint, value);
      this.extractor = new ConstraintExtractor(constraint);
      this.checker = new ModifierChecker(list);
      this.annotations = annotations;
      this.identifier = identifier;
      this.list = list;
   }

   @Override
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      Scope scope = type.getScope();
      Initializer declare = declaration.declare(initializer);
      List<Property> properties = type.getProperties();
      Value value = identifier.evaluate(scope, null);
      Type constraint = extractor.extract(scope);
      String name = value.getString();
      int modifiers = list.getModifiers();
      
      if (checker.isStatic()) {
         Accessor accessor = new StaticAccessor(initializer, scope, type, name);
         Property property = new AccessorProperty(name, type, constraint, accessor, modifiers);
         
         annotations.apply(scope, property);
         properties.add(property);
      } else {
         Accessor accessor = new ScopeAccessor(name);
         Property property = new AccessorProperty(name, type, constraint, accessor, modifiers); // is this the correct type!!??
         
         annotations.apply(scope, property);
         properties.add(property);
      }
      return declare;
   }
}

