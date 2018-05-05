package org.snapscript.compile.staticanalysis;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.List;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.StoreContext;
import org.snapscript.compile.StringCompiler;
import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.transform.GenericReference;
import org.snapscript.core.constraint.transform.GenericTransform;
import org.snapscript.core.constraint.transform.GenericTransformer;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class GenericTransformerTest extends TestCase {   
   
   private static final String SOURCE_1 =
   "class One<A>{\n"+
   "   func():A{\n"+
   "   }\n"+
   "}\n"+
   "class Two<A, B> extends One<B>{\n"+
   "   blah():A{\n"+
   "   }\n"+
   "}\n"+
   "class Three extends Two<String, Integer>{\n"+
   "}\n"+
   "class Four<A: Boolean> extends Two<String, A>{\n"+
   "}\n";   

   private static final String SOURCE_2 = 
   "class Foo<A>{\n"+
   "   func():A{}\n"+
   "}\n"+
   "var f: Foo<List<String>> = new Foo<List<String>>();\n"+
   "f.func().get(0).substring(1);\n";         
   
   public void testGenericTransformation() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Model model = new EmptyModel();
      
      System.err.println(SOURCE_1);
      
      compiler.compile(SOURCE_1).execute(model, true);
      
      TypeLoader loader = context.getLoader();
      Type type3 = loader.resolveType(DEFAULT_PACKAGE, "Three");
      Type type4 = loader.resolveType(DEFAULT_PACKAGE, "Four");        
      Type type1 = loader.resolveType(DEFAULT_PACKAGE, "One");
      Type typeInteger = loader.loadType(Integer.class);
      Type typeBoolean = loader.loadType(Boolean.class);

      GenericTransformer transformer = context.getTransformer();
      GenericTransform resolution3 = transformer.transform(type3, type1);
      Constraint constraint3 = Constraint.getConstraint(type3); // original     
      GenericReference result3 = resolution3.getReference(constraint3);
      Scope scope3 = type3.getScope();
      Constraint transformed3 = result3.getType();
      
      System.err.println( transformed3);
      assertEquals(transformed3.getType(scope3), type1);
      assertEquals(result3.getConstraint("A").getType(scope3), typeInteger);
      assertNull(result3.getConstraint("B"));         
      
      
      GenericTransform resolution4 = transformer.transform(type4, type1);
      Constraint constraint4 = Constraint.getConstraint(type4); // original     
      GenericReference result4 = resolution4.getReference(constraint4);
      Scope scope4 = type4.getScope();
      Constraint transformed4 = result4.getType();
      
      System.err.println( transformed4);
      assertEquals(transformed4.getType(scope4), type1);
      assertEquals(result4.getConstraint("A").getType(scope4), typeBoolean);  
      assertNull(result4.getConstraint("B"));   
   }
   
//   public void testNestedGenericTransformation() throws Exception {
//      Store store = new ClassPathStore();
//      Context context = new StoreContext(store, null);
//      Compiler compiler = new StringCompiler(context);
//      Model model = new EmptyModel();
//      
//      System.err.println(SOURCE_2);
//      
//      compiler.compile(SOURCE_2).execute(model, true);
//      
//      TypeLoader loader = context.getLoader();
//      Type typeFoo = loader.resolveType(DEFAULT_PACKAGE, "Foo");
//      Type typeList = loader.loadType(List.class);
//      Type typeString = loader.loadType(String.class);
//
//      GenericTransformer transformer = context.getTransformer();
//      GenericTransform resolution = transformer.transform(typeFoo, typeFoo);
//      Constraint constraint = Constraint.getConstraint(typeFoo); // original     
//      GenericReference result = resolution.getReference(constraint);
//      Scope scope = typeFoo.getScope();
//      Constraint transformed = result.getType();
//      
//      System.err.println(transformed);      
//   }
   

}
