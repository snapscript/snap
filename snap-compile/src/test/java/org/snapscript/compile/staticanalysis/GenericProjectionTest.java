package org.snapscript.compile.staticanalysis;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.compile.Compiler;
import org.snapscript.compile.StoreContext;
import org.snapscript.compile.StringCompiler;
import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class GenericProjectionTest extends TestCase {
   
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
   "}\n";

   
   public void testProjection() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Compiler compiler = new StringCompiler(context);
      Model model = new EmptyModel();
      
      System.err.println(SOURCE_1);
      
      compiler.compile(SOURCE_1).execute(model, true);
      
      TypeLoader loader = context.getLoader();
      Type type3 = loader.resolveType(DEFAULT_PACKAGE, "Three");    
      Type type1 = loader.resolveType(DEFAULT_PACKAGE, "One");  
      Constraint constraint = Constraint.getConstraint(type3);
      List<Constraint> path = findPath(constraint, type1);
      
      findRealType(constraint, type1, "A");
      assertEquals(path.size(), 3);
      
   }
   
   public Type findRealType(Constraint constraint, Type require, String name) {
      List<Constraint> path = findPath(constraint, require);
      Scope scope = require.getScope();
      Map<String, Type> types = new HashMap<String, Type>();
      
      for(Constraint entry : path) {
         Type type = entry.getType(scope);
         List<Constraint> generics = entry.getGenerics(scope);
         List<Constraint> declared = type.getConstraints();
         int declaredCount = declared.size();
         int genericCount = generics.size();
         
         if(declaredCount > 0) {
            if(declaredCount == genericCount) {
               for(int i = 0; i < genericCount; i++) {
                  Constraint declaration = declared.get(i);
                  Constraint generic = generics.get(i);
                  String parameter = declaration.getName(scope);
                  Type actual = generic.getType(scope);
                  
                  types.put(parameter, actual);
               }
            }
         } 
      }
      return null;
   }

   private static List<Constraint> findPath(Constraint constraint, Type require) {
      List<Constraint> path = new ArrayList<Constraint>();

      findPath(constraint, require, path);
      path.add(constraint);
      Collections.reverse(path);
      
      return path;
   }
   
   private static boolean findPath(Constraint constraint, Type require, List<Constraint> path) {
      Scope scope = require.getScope();
      Type type = constraint.getType(scope);
      
      if(type != require) {
         List<Constraint> types = type.getTypes();
         
         for(Constraint base : types) {
            if(findPath(base, require, path)) {
               path.add(base);
               return true;
            }
         }
         return false;
      }
      return true;
   }
}
