package org.snapscript.core;

import junit.framework.TestCase;

public class FunctionBinderTest extends TestCase {
   
   public void testBinder() throws Exception {
//      Map<String, Object> map = new HashMap<String, Object>();
//      PackageLinker linker = new PackageLinker() {
//         
//         @Override
//         public Package link(String name, String source) throws Exception {
//            return null;
//         }
//         @Override
//         public Package link(String name, String source, String grammar) throws Exception {
//            return null;
//         }
//      };
//      Store store = new ClassPathStore();
//      ResourceManager manager = new StoreManager(store);
//      PackageManager resolver = new PackageManager(linker, manager);
//      ModuleBuilder builder = new ModuleBuilder(null);
//      TypeLoader loader = new TypeLoader(resolver, builder);
//      ConstraintMatcher matcher = new ConstraintMatcher(loader);
//      FunctionBinder binder = new FunctionBinder(matcher, loader);
//      Type type = loader.loadType(Map.class);
//      
//      binder.bind(null, map, "put", "x", 11).call();
//      binder.bind(null, map, "put", "y", 21).call();
//      binder.bind(null, map, "put", "z", 441).call();
//      
//      assertEquals(map.get("x"), 11);
//      assertEquals(map.get("y"), 21);
//      assertEquals(map.get("z"), 441);
//      
//      binder.bind(null, map, "put", "x", 22).call();
//      binder.bind(null, map, "remove", "y").call();
//      binder.bind(null, map, "put", "z", "x").call();
//      
//      assertEquals(map.get("x"), 22);
//      assertEquals(map.get("y"), null);
//      assertEquals(map.get("z"), "x");
//      
//      assertEquals(binder.bind(null, map, "put", "x", 44).call().getValue(), 22);
//      assertEquals(binder.bind(null, map, "put", "y", true).call().getValue(), null);
//      assertEquals(binder.bind(null, map, "put", "z", "x").call().getValue(), "x");
   }
   
   public void testBinderPerformance() throws Exception {
//
//      PackageLinker linker = new PackageLinker() {
//         
//         @Override
//         public Package link(String name, String source) throws Exception {
//            return null;
//         }
//         @Override
//         public Package link(String name, String source, String grammar) throws Exception {
//            return null;
//         }
//      };
//      Store store = new ClassPathStore();
//      ResourceManager manager = new StoreManager(store);
//      PackageManager resolver = new PackageManager(linker, manager);
//      ModuleBuilder builder = new ModuleBuilder(null);
//      TypeLoader loader = new TypeLoader(resolver, builder);
//      ConstraintMatcher matcher = new ConstraintMatcher(loader);
//      FunctionBinder binder = new FunctionBinder(matcher, loader);
//      Type type = loader.loadType(Map.class);
//      long start = System.currentTimeMillis();
//      
//      for(int i = 0; i< 1000000; i++) {
//         Map<String, Object> map = new HashMap<String, Object>();
//         
//         binder.bind(null, map, "put", "x", 11).call();
//         binder.bind(null, map, "put", "y", 21).call();
//         binder.bind(null, map, "put", "z", 441).call();
//         
//         assertEquals(map.get("x"), 11);
//         assertEquals(map.get("y"), 21);
//         assertEquals(map.get("z"), 441);
//      }
//      long finish = System.currentTimeMillis();
//      long duration = finish - start;
//      
//      System.err.println("Duration " + duration);
   }

}
