
package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstant implements TypePart {

   private final TraitConstantDeclaration declaration;
   private final ConstraintExtractor extractor;
   private final AnnotationList annotations;
   private final ModifierChecker checker;
   private final TextLiteral identifier;
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Evaluation value) {
      this(annotations, list, identifier, null, value);
   }
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.declaration = new TraitConstantDeclaration(identifier, constraint, value);
      this.extractor = new ConstraintExtractor(constraint);
      this.checker = new ModifierChecker(list);
      this.annotations = annotations;
      this.identifier = identifier;
   }
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      return null;
   }

   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      Scope scope = type.getScope();
      TypeFactory declare = declaration.declare(factory, type);
      List<Property> properties = type.getProperties();
      Value value = identifier.evaluate(scope, null);
      Type constraint = extractor.extract(scope);
      String name = value.getString();
      
      if(!checker.isConstant()) {
         throw new InternalStateException("Variable '" + name + "' for '" + type + "' must be constant");
      }
      Accessor accessor = new StaticAccessor(factory, scope, type, name);
      Property property = new AccessorProperty(name, type, constraint, accessor, ModifierType.STATIC.mask | ModifierType.CONSTANT.mask);
      
      annotations.apply(scope, property);
      properties.add(property);

      return declare;
   }
}