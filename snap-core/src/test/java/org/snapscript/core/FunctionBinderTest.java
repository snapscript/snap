package org.snapscript.core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bind.FunctionResolver;
import org.snapscript.core.bind2.FunctionResolver2;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.NoPackage;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.TraceInterceptor;

public class FunctionBinderTest extends TestCase {
   
   private static final int ITERATIONS = 1000000;
   
   public void testBinder() throws Exception {
      Map<String, Object> map = new HashMap<String, Object>();
      Context context = new TestContext();
      Model model = new EmptyModel();
      Path path = new Path(Reserved.DEFAULT_RESOURCE);
      Module module = new ContextModule(context, null, path, Reserved.DEFAULT_PACKAGE);
      Scope scope = new ModelScope(model, module);
      
      context.getBinder().bind(scope, map, "put", "x", 11).call();
      context.getBinder().bind(scope, map, "put", "y", 21).call();
      context.getBinder().bind(scope, map, "put", "z", 441).call();
      
      assertEquals(map.get("x"), 11);
      assertEquals(map.get("y"), 21);
      assertEquals(map.get("z"), 441);
      
      context.getBinder().bind(scope, map, "put", "x", 22).call();
      context.getBinder().bind(scope, map, "remove", "y").call();
      context.getBinder().bind(scope, map, "put", "z", "x").call();
      
      assertEquals(map.get("x"), 22);
      assertEquals(map.get("y"), null);
      assertEquals(map.get("z"), "x");
      
      assertEquals(context.getBinder().bind(scope, map, "put", "x", 44).call().getValue(), 22);
      assertEquals(context.getBinder().bind(scope, map, "put", "y", true).call().getValue(), null);
      assertEquals(context.getBinder().bind(scope, map, "put", "z", "x").call().getValue(), "x");
      
      long start = System.currentTimeMillis();
      
      for(int i = 0; i < ITERATIONS; i++) {
         context.getBinder().bind(scope, map, "put", "x", 44);
      }
      long finish = System.currentTimeMillis();
      double milliseconds = finish - start;
      double seconds = milliseconds / 1000.0;
      
      System.err.println("Time taken: " + seconds + " seconds, Invocations per second: " +(ITERATIONS/seconds));
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

   private static class TestLinker implements PackageLinker {
      
      @Override
      public Package link(Path name, String source) throws Exception {
         return new NoPackage();
      }
      
      @Override
      public Package link(Path name, String source, String grammar) throws Exception {
         return new NoPackage();
      }
   }
   
   private static class TestContext implements Context {
      
      private final ConstraintMatcher matcher;
      private final ResourceManager manager;
      private final ModuleRegistry registry;
      private final TypeLoader loader;
      private final TypeExtractor extractor;
      private final ThreadStack stack;
      private final ProxyWrapper wrapper;
      private final FunctionBinder binder;
      private final FunctionResolver resolver;
      private final PackageLinker linker;
      private final Store store;
      
      private final FunctionResolver2 resolver2;
      
      public TestContext(){
         this.linker = new TestLinker();
         this.store = new ClassPathStore();
         this.stack = new ThreadStack();
         this.manager = new StoreManager(store);
         this.registry = new ModuleRegistry(this, null);
         this.loader = new TypeLoader(linker, registry, manager);
         this.extractor = new TypeExtractor(loader);
         this.resolver = new FunctionResolver(extractor);
         this.resolver2 = new FunctionResolver2(extractor);
         this.binder = new FunctionBinder(extractor, stack, resolver, resolver2);
         this.wrapper = new ProxyWrapper(this);
         this.matcher = new ConstraintMatcher(loader, wrapper);
      }

      @Override
      public ThreadStack getStack() {
         return stack;
      }

      @Override
      public ErrorHandler getHandler() {
         return null;
      }

      @Override
      public TypeExtractor getExtractor() {
         return extractor;
      }

      @Override
      public ResourceManager getManager() {
         return manager;
      }

      @Override
      public ModuleRegistry getRegistry() {
         return registry;
      }

      @Override
      public ConstraintMatcher getMatcher() {
         return matcher;
      }

      @Override
      public ProgramValidator getValidator() {
         return null;
      }

      @Override
      public TraceInterceptor getInterceptor() {
         return null;
      }

      @Override
      public ExpressionEvaluator getEvaluator() {
         return null;
      }
      
      @Override
      public PlatformProvider getProvider() {
         return null;
      }

      @Override
      public FunctionBinder getBinder() {
         return binder;
      }

      @Override
      public PackageLinker getLinker() {
         return linker;
      }

      @Override
      public ProxyWrapper getWrapper() {
         return wrapper;
      }

      @Override
      public TypeLoader getLoader() {
         return loader;
      }
   }
}
