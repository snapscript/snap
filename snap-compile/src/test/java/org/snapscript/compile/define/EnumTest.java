package org.snapscript.compile.define;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.snapscript.compile.ClassPathCompilerBuilder;
import org.snapscript.compile.Compiler;
import org.snapscript.core.MapModel;
import org.snapscript.core.Model;
import org.snapscript.core.Value;

public class EnumTest extends TestCase{
   private static final String SOURCE_1=
   "enum Animal {\n"+
   "   DOG,\n"+
   "   CAT,\n"+
   "   FISH;\n"+
   "}\n"+
   "out.println('enum for DOG='+Animal.DOG.name+' class='+Animal.DOG.class+' ordinal='+Animal.DOG.ordinal);\n"+
   "out.println('enum for CAT='+Animal.CAT.name+' class='+Animal.CAT.class+' ordinal='+Animal.CAT.ordinal);\n"+
   "out.println('enum for FISH='+Animal.FISH.name+' class='+Animal.FISH.class+' ordinal='+Animal.FISH.ordinal);\n";   

   private static final String SOURCE_2=
   "enum Person {\n"+
   "   JIM('Jim',23),\n"+
   "   JIM_2('Jim',23),\n"+
   "   TOM('Tom',33),\n"+
   "   BOB('Bob',21);\n"+
   "   var title;\n"+
   "   var age;\n"+
   "   new(title,age){\n"+
   "      this.title=title;\n"+
   "      this.age=age;\n"+
   "   }\n"+
   "}\n"+
   "out.println('enum for JIM='+Person.JIM.name+' class='+Person.JIM.class+' ordinal='+Person.JIM.ordinal+' age='+Person.JIM.age+' title='+Person.JIM.title);\n"+
   "out.println('enum for TOM='+Person.TOM.name+' class='+Person.TOM.class+' ordinal='+Person.TOM.ordinal+' age='+Person.TOM.age+' title='+Person.TOM.title);\n"+
   "out.println('enum for BOB='+Person.BOB.name+' class='+Person.BOB.class+' ordinal='+Person.BOB.ordinal+' age='+Person.BOB.age+' title='+Person.BOB.title);\n";   

//   public void testSimpleEnum() throws Exception {
//      Map map = new HashMap<String,Value>();
//      map.put("out",System.out);
//      Model s = new MapModel(map);
//      InstructionResolver set = new InterpretationResolver();
//      Context context =new ClassPathContext(set);
//      ContextModule m = new ContextModule(context);
//      ScriptCompiler compiler = new ScriptCompiler(context);
//      //compiler.compile(SOURCE_1).execute(s);
//      System.err.println();
//      System.err.println(SOURCE_1);
//      compiler.compile(SOURCE_1).execute(s);
//      System.err.println();     
//   }
   
   public void testComplexEnum() throws Exception {
      Map map = new HashMap<String,Value>();
      map.put("out",System.out);
      Model s = new MapModel(map);
      Compiler compiler = ClassPathCompilerBuilder.createCompiler();
      //compiler.compile(SOURCE_1).execute(s);
      System.err.println();
      System.err.println(SOURCE_2);
      compiler.compile(SOURCE_2).execute(s);
      System.err.println();      

   }
}
