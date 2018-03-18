package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Constraint;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ScopeAccessor;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.property.Property;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;

public class MemberField implements TypePart {
   
   private final MemberFieldDeclaration[] declarations;
   private final TypeFactoryCollector collector;
   private final MemberFieldAssembler assembler;
   private final AnnotationList annotations;
   private final ModifierChecker checker;

   public MemberField(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
      this.assembler = new MemberFieldAssembler(modifiers);
      this.checker = new ModifierChecker(modifiers);
      this.collector = new TypeFactoryCollector();
      this.declarations = declarations;
      this.annotations = annotations;
   }
   
   @Override
   public TypeFactory create(TypeFactory factory, Type type) throws Exception {
      return null;
   }
   
   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      return null;
   }

   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      Scope scope = type.getScope();
      List<Property> properties = type.getProperties();
      int mask = checker.getModifiers();
      
      for(MemberFieldDeclaration declaration : declarations) {
         MemberFieldData data = declaration.create(scope);
         String name = data.getName();
         Constraint constraint = data.getConstraint();
         TypeFactory declare = assembler.assemble(data);
         
         if (checker.isStatic()) {
            Accessor accessor = new StaticAccessor(factory, scope, type, name);
            Property property = new AccessorProperty(name, type, constraint, accessor, mask);
            
            annotations.apply(scope, property);
            properties.add(property);
         } else {
            Accessor accessor = new ScopeAccessor(name);
            Property property = new AccessorProperty(name, type, constraint, accessor, mask); // is this the correct type!!??
            
            annotations.apply(scope, property);
            properties.add(property);
         }
         collector.update(declare);
      }
      return collector;
   }
}