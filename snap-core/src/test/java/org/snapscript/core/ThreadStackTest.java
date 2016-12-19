package org.snapscript.core;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.snapscript.core.ContextModule;
import org.snapscript.core.Module;
import org.snapscript.core.error.ThreadStack;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceType;

public class ThreadStackTest extends TestCase {

   /*
    java.lang.Throwable
      at run.layout.PanelLayout.getDimensions(/layout.snap:33)
      at run.graphics.Panel.transform(/run.snap:21)
      at run.graphics.Panel.paint(/run.snap:413)
      at run.main(/run.snap:13)
      at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
      at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
      at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
      at java.lang.reflect.Method.invoke(Method.java:606)
      at junit.framework.TestCase.runTest(TestCase.java:154)
      at junit.framework.TestCase.runBare(TestCase.java:127)
      at junit.framework.TestResult$1.protect(TestResult.java:106)
      at junit.framework.TestResult.runProtected(TestResult.java:124)
      at junit.framework.TestResult.run(TestResult.java:109)
      at junit.framework.TestCase.run(TestCase.java:118)
      at junit.framework.TestSuite.runTest(TestSuite.java:208)
      at junit.framework.TestSuite.run(TestSuite.java:203)
      at org.eclipse.jdt.internal.junit.runner.junit3.JUnit3TestReference.run(JUnit3TestReference.java:131)
      at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
      at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
      at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
      at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
      at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
    */
   public void testStack() throws Exception {
      ThreadStack stack = new ThreadStack();
      Throwable cause = new Throwable();
      
      createTrace(stack, "/run.snap", 1);
      createTrace(stack, "/run.snap", 13);
      createFunction(stack, "paint", "Panel", "run.graphics");
      createTrace(stack, "/run.snap", 413);
      createFunction(stack, "transform", "Panel", "run.graphics");
      createTrace(stack, "/run.snap", 21);
      createFunction(stack, "getDimensions", "PanelLayout", "run.layout");
      createTrace(stack, "/layout.snap", 33);
      
      StackTraceElement[] list =stack.build();
      cause.setStackTrace(list);
      cause.printStackTrace();
   }
   
   public static void createTrace(ThreadStack stack, String resource, int line){
      stack.before(new Trace(TraceType.NORMAL, new ContextModule(null, resource, resource, -1), line));
   }
   
   public static void createFunction(ThreadStack stack, String functionName, String typeName, String moduleName){
      Module module = new ContextModule(null, moduleName, moduleName, -1);
      TestType type = new TestType(module, typeName, null, null);
      List<Parameter> parameters = new ArrayList<Parameter>();
      Signature signature = new Signature(parameters, module);
      
      stack.before(new InvocationFunction(signature, null, type, null, functionName, 11));
   }
}
