
package org.snapscript.core.extend;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.snapscript.common.command.CommandBuilder;
import org.snapscript.common.command.Console;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageDefinition;

public class ScopeExtension {

   private final CommandBuilder builder;
   private final Context context;
   
   public ScopeExtension(Context context) {
      this.builder = new CommandBuilder();
      this.context = context;
   }
   
   public <T> T eval(Scope scope, String source) throws Exception {
      ExpressionEvaluator executor = context.getEvaluator();
      Module module = scope.getModule();
      String name = module.getName();
      
      return executor.evaluate(scope, source, name);
   }
   
   public <T> T eval(Scope scope, String source, String name) throws Exception {
      ExpressionEvaluator executor = context.getEvaluator();
      ModuleRegistry registry = context.getRegistry();
      Module module = registry.addModule(name);
      Scope inner = module.getScope();
      
      return executor.evaluate(inner, source, name);
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
   
   public List<String> exec(Scope scope, String command) throws Exception {
      Callable<Console> task = builder.create(command);
      Console console = task.call();
      
      return console.readAll();
   }
   
   public List<String> exec(Scope scope, String command, String directory) throws Exception {
      Callable<Console> task = builder.create(command, directory);
      Console console = task.call();
      
      return console.readAll();
   }
   
   public List<String> exec(Scope scope, String command, Map<String, String> environment) throws Exception {
      Callable<Console> task = builder.create(command, environment);
      Console console = task.call();
      
      return console.readAll();
   }
   
   public List<String> exec(Scope scope, String command, String directory, Map<String, String> environment) throws Exception {
      Callable<Console> task = builder.create(command, directory, environment);
      Console console = task.call();
      
      return console.readAll();
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
