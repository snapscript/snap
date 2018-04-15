package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.StaticAccessor;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.property.Property;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceTypePart;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.DeclarationConstraint;
import org.snapscript.tree.literal.TextLiteral;

public class TraitConstant implements Compilation {
   
   private final TypePart part;
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Evaluation value) {
      this(annotations, list, identifier, null, value);
   }
   
   public TraitConstant(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.part = new CompileResult(annotations, list, identifier, constraint, value);
   }
   
   @Override
   public TypePart compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getAllocate(module, path, line);
      
      return new TraceTypePart(interceptor, handler, part, trace);
   }
   
   private static class CompileResult extends TypePart {

      private final TraitConstantDeclaration declaration;
      private final AnnotationList annotations;
      private final ModifierChecker checker;
      private final TextLiteral identifier;
      private final Constraint constraint;
      
      public CompileResult(AnnotationList annotations, ModifierList list, TextLiteral identifier, Constraint constraint, Evaluation value) {
         this.declaration = new TraitConstantDeclaration(identifier, constraint, value);
         this.constraint = new DeclarationConstraint(constraint);
         this.checker = new ModifierChecker(list);
         this.annotations = annotations;
         this.identifier = identifier;
      }
   
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
         TypeState declare = declaration.declare(body, type);
         List<Property> properties = type.getProperties();
         Value value = identifier.evaluate(scope, null);
         String name = value.getString();
         
         if(!checker.isConstant()) {
            throw new InternalStateException("Variable '" + name + "' for '" + type + "' must be constant");
         }
         Accessor accessor = new StaticAccessor(body, type, name);
         Property property = new AccessorProperty(name, type, constraint, accessor, ModifierType.STATIC.mask | ModifierType.CONSTANT.mask);
         
         annotations.apply(scope, property);
         properties.add(property);
   
         return declare;
      }
   }
}