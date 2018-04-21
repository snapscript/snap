package org.snapscript.core.type.index;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.type.Type;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.annotation.AnnotationExtractor;
import org.snapscript.core.function.Function;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.type.extend.ClassExtender;

public class MethodIndexer {
   
   private final AnnotationExtractor extractor;
   private final FunctionGenerator generator;
   private final ConstructorIndexer indexer;
   private final ModifierConverter converter;
   private final ClassExtender extender;
   
   public MethodIndexer(TypeIndexer indexer, ClassExtender extender, PlatformProvider provider){
      this.generator = new FunctionGenerator(indexer, provider);
      this.indexer = new ConstructorIndexer(indexer, provider);
      this.extractor = new AnnotationExtractor();
      this.converter = new ModifierConverter();
      this.extender = extender;
   }

   public List<Function> index(Type type) throws Exception {
      Class source = type.getType();
      List<Function> extensions = extender.extend(source);
      List<Function> constructors = indexer.index(type);
      Method[] methods = source.getDeclaredMethods();
      
      for(Function extension : extensions) {
         constructors.add(extension);
      }
      if(methods.length > 0) {
         List<Function> functions = new ArrayList<Function>();
   
         for(Method method : methods){
            int modifiers = converter.convert(method);
            
            if(ModifierType.isPublic(modifiers) || ModifierType.isProtected(modifiers)) {
               String name = method.getName();
               Function function = generator.generate(type, method, name, modifiers);
               List<Annotation> extracted = extractor.extract(method);
               List<Annotation> actual = function.getAnnotations();
               
               functions.add(function);
               actual.addAll(extracted);
            }
         }
         if(!constructors.isEmpty()) {
            functions.addAll(constructors);
         }
         return functions;
      }
      return constructors;
   }
}