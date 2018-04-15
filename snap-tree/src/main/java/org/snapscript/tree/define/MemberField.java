package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.Accessor;
import org.snapscript.core.function.AccessorProperty;
import org.snapscript.core.function.ScopeAccessor;
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
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;

public class MemberField implements Compilation {
   
   private final TypePart part;
   
   public MemberField(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
      this.part = new CompileResult(annotations, modifiers, declarations);
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
   
      private final MemberFieldDeclaration[] declarations;
      private final TypeStateCollector collector;
      private final MemberFieldAssembler assembler;
      private final AnnotationList annotations;
      private final ModifierChecker checker;
   
      public CompileResult(AnnotationList annotations, ModifierList modifiers, MemberFieldDeclaration... declarations) {
         this.assembler = new MemberFieldAssembler(modifiers);
         this.checker = new ModifierChecker(modifiers);
         this.collector = new TypeStateCollector();
         this.declarations = declarations;
         this.annotations = annotations;
      }
   
      @Override
      public TypeState define(TypeBody body, Type type, Scope scope) throws Exception {
         List<Property> properties = type.getProperties();
         int mask = checker.getModifiers();
         
         for(MemberFieldDeclaration declaration : declarations) {
            MemberFieldData data = declaration.create(scope, mask);
            String name = data.getName();
            Constraint constraint = data.getConstraint();
            TypeState declare = assembler.assemble(data);
            
            if (checker.isStatic()) {
               Accessor accessor = new StaticAccessor(body, type, name);
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
}