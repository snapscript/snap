package org.snapscript.core.extend;

import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.SystemConsole;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;

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
      PackageDefinition definition = module.define(scope);
      Statement statement = definition.compile(scope, null);
      
      statement.execute(scope);
      
      return registry.getModule(name);
   }
   
   public void printf(Scope scope, Object value, Object... values)  throws Exception{
      Module module = scope.getModule();
      Context context = module.getContext();
      SystemConsole console = context.getConsole();
      
      console.printf(value, values);
   }   
   
   public void print(Scope scope, Object value)  throws Exception{
      Module module = scope.getModule();
      Context context = module.getContext();
      SystemConsole console = context.getConsole();
      
      console.print(value);
   }
   
   public void println(Scope scope, Object value) throws Exception{
      Module module = scope.getModule();
      Context context = module.getContext();
      SystemConsole console = context.getConsole();
      
      console.println(value);
   }
   
   public void println(Scope scope) throws Exception{
      Module module = scope.getModule();
      Context context = module.getContext();
      SystemConsole console = context.getConsole();
      
      console.println();
   }
   
   public void sleep(Scope scope, long time) throws Exception {
      Thread.sleep(time);
   }

   public long time(Scope scope) throws Exception {
      return System.currentTimeMillis();
   }
}
