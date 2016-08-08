package org.snapscript.core.extend;

import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.link.Package;

public class ScopeExtension {

   private final Context context;
   
   public ScopeExtension(Context context) {
      this.context = context;
   }
   
   public <T> T eval(Scope scope, String source) throws Exception {
      ExpressionEvaluator executor = context.getEvaluator();
      Module module = scope.getModule();
      String name = module.getName();
      
      return executor.evaluate(scope, source, name);
   }
   
   public Module load(Scope scope, String name) throws Exception {
      ModuleRegistry registry = context.getRegistry();
      TypeLoader loader = context.getLoader();
      Package module = loader.importPackage(name);
      Statement statement = module.compile(scope);
      
      statement.execute(scope);
      
      return registry.getModule(name);
   }
   
   public void printf(Scope scope, Object value, Object... values)  throws Exception{
      String text = String.valueOf(value);
      String result = String.format(text, values);
      
      System.out.print(result);
   }   
   
   public void print(Scope scope, Object value)  throws Exception{
      System.out.print(value);
   }
   
   public void println(Scope scope, Object value) throws Exception{
      System.out.println(value);
   }
   
   public void println(Scope scope) throws Exception{
      System.out.println();
   }
   
   public void sleep(Scope scope, long time) throws Exception {
      Thread.sleep(time);
   }

   public long time(Scope scope) throws Exception {
      return System.currentTimeMillis();
   }
}
