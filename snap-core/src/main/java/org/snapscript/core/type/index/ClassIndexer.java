package org.snapscript.core.type.index;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.ModifierType.ARRAY;
import static org.snapscript.core.ModifierType.CLASS;
import static org.snapscript.core.ModifierType.ENUM;
import static org.snapscript.core.ModifierType.PROXY;
import static org.snapscript.core.ModifierType.TRAIT;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationExtractor;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.PrimitivePromoter;
import org.snapscript.core.error.InternalArgumentException;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportScanner;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.extend.ClassExtender;

public class ClassIndexer {

   private final ClassHierarchyIndexer hierarchy;
   private final AnnotationExtractor extractor;
   private final GenericIndexer generics;
   private final MethodIndexer functions;
   private final PropertyIndexer properties;
   private final PrimitivePromoter promoter;
   private final ModuleRegistry registry;
   private final ImportScanner scanner;
   private final TypeIndexer indexer;

   public ClassIndexer(TypeIndexer indexer, ModuleRegistry registry, ImportScanner scanner, ClassExtender extender, PlatformProvider provider) {
      this.properties = new PropertyIndexer(indexer, extender);
      this.functions = new MethodIndexer(extender, provider);
      this.generics = new GenericIndexer(indexer);
      this.hierarchy = new ClassHierarchyIndexer();
      this.extractor = new AnnotationExtractor();      
      this.promoter = new PrimitivePromoter();
      this.scanner = scanner;
      this.registry = registry;
      this.indexer = indexer;
   }
   
   public List<Constraint> indexTypes(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return hierarchy.index(actual);
   }
   
   public List<Annotation> indexAnnotations(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return extractor.extract(actual);
   }
   
   public List<Constraint> indexConstraints(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return generics.index(actual);
   }
   
   public List<Property> indexProperties(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return properties.index(actual);
   }
   
   public List<Function> indexFunctions(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      return functions.index(type);
   }
   
   public Module indexModule(ClassType type) throws Exception {
      Class source = type.getType();
      Class actual = promoter.promote(source);
      
      if(actual == null) {
         throw new InternalArgumentException("Could not determine type for " + source);
      }
      while(actual.isArray()) {
         actual = actual.getComponentType();
      }
      Package module = actual.getPackage();
      
      if(module != null) {
         String name = scanner.importName(module);
         
         if(name != null) {
            return registry.addModule(name);
         }
      }
      return registry.addModule(DEFAULT_PACKAGE);
   }
   
   public Type indexOuter(ClassType type) throws Exception {
      Class source = type.getType();
      Class outer = source.getEnclosingClass();
      
      if(outer != null) {
         Class actual = promoter.promote(outer);
         
         if(actual == null) {
            throw new InternalArgumentException("Could not determine type for " + source);
         }
         return indexer.loadType(actual);
      }
      return null;
   }
   
   public Type indexEntry(ClassType type) throws Exception {
      Class source = type.getType();
      Class entry = source.getComponentType();
      
      if(entry != null) {
         Class actual = promoter.promote(entry);
         
         if(actual == null) {
            throw new InternalArgumentException("Could not determine type for " + source);
         }
         return indexer.loadType(actual);
      }
      return null;
   }
   
   public int indexModifiers(ClassType type) throws Exception {
      Class source = type.getType();
      int modifiers = source.getModifiers();
      
      if(source.isEnum()) {
         return ENUM.mask;
      }
      if(source.isInterface()) {
         return TRAIT.mask | ABSTRACT.mask;
      } 
      if(source.isArray()) {
         return ARRAY.mask;
      } 
      if(Proxy.isProxyClass(source)) {
         return PROXY.mask;
      }
      if(Modifier.isAbstract(modifiers)) {
         return CLASS.mask | ABSTRACT.mask;
      }
      return CLASS.mask;
   }
}