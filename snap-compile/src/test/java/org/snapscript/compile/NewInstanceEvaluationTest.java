package org.snapscript.compile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import junit.framework.TestCase;

import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;

public class NewInstanceEvaluationTest extends TestCase {
   
   private static final String SOURCE_1 =
   "import util.stream.Collectors;\n"+
   "trait Base{\n"+
    "   func(name){\n"+
   "       var func = -> Collectors.toList();\n"+
   "       func();\n"+
   "       eval('{:}');\n"+
   "       eval(name);\n"+
   "    }\n"+
   "}\n";
         
   private static final String SOURCE_2 =
   "import base.Base;\n"+
   //"import base.*;\n"+ // this works
   "class Other with Base{\n"+
   "   test(){\n"+
   "      func('get()');\n"+
   "   }\n"+
   "   get(){\n"+
   "      \"Other.get()\";\n"+
   "   }\n"+
   "   override toString(){\n"+
   "     return 'Other.toString()';\n"+
   "   }\n"+
   "}";

   private static final String SOURCE_3 =
   "import test.*;\n"+
   //"import base.*;\n"+ // this works
   "const value = eval('new Other()');\n"+
   "value.test();\n";
   
   public void testEvaluation() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/base/Base.snap", SOURCE_1);
      sources.put("/test/Other.snap", SOURCE_2);    
      Store store = new MapStore(sources);
      Executor executor = new ScheduledThreadPoolExecutor(10);
      Model model = new EmptyModel();
      Context context = new StoreContext(store, executor);
      Compiler compiler = new ResourceCompiler(context);
      compiler.compile("/test/Other.snap").execute();
      Object result = context.getEvaluator().evaluate(model, "new Other().test()", "test");
      Object instance = context.getEvaluator().evaluate(model, "new Other()", "test");
      Object proxy = context.getWrapper().toProxy(instance);
      System.err.println("result=" + result+ " instance="+proxy +" class="+proxy.getClass());
   }
   
   public void testEvaluationReturnValue() throws Exception {
      Map<String, String> sources = new HashMap<String, String>();
      sources.put("/base/Base.snap", SOURCE_1);
      sources.put("/test/Other.snap", SOURCE_2);   
      sources.put("/run.snap", SOURCE_3);    
      Store store = new MapStore(sources);
      Executor executor = new ScheduledThreadPoolExecutor(10);
      Context context = new StoreContext(store, executor);
      Compiler compiler = new ResourceCompiler(context);
      compiler.compile("/run.snap").execute();
   }
   
   private static class MapStore implements Store {
      
      private final Map<String, String> sources;
      
      public MapStore(Map<String, String> sources){
         this.sources = sources;
      }

      @Override
      public InputStream getInputStream(String name) {
         String source = sources.get(name);
         if(source != null) {
            try {
               byte[] data = source.getBytes("UTF-8");
               return new ByteArrayInputStream(data);
            }catch(Exception e){
               throw new IllegalStateException("Could not load " + name, e);
            }
         }
         return null;
      }

      @Override
      public OutputStream getOutputStream(String name) {
         return null;
      }
      
   }
}
