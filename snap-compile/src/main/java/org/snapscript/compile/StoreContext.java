package org.snapscript.compile;

import java.util.concurrent.Executor;

import org.snapscript.common.store.Store;
import org.snapscript.compile.assemble.ExecutorLinker;
import org.snapscript.compile.assemble.OperationEvaluator;
import org.snapscript.compile.validate.ExecutableValidator;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.ModuleRegistry;
import org.snapscript.core.ProgramValidator;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.StoreManager;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.bridge.BridgeProvider;
import org.snapscript.core.bridge.PlatformBridgeProvider;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.TraceInterceptor;

public class StoreContext implements Context {

   private final ExecutableValidator validator;
   private final ExpressionEvaluator evaluator;
   private final TraceInterceptor interceptor;
   private final BridgeProvider provider;
   private final ConstraintMatcher matcher;
   private final ResourceManager manager;
   private final FunctionBinder binder;
   private final TypeExtractor extractor;
   private final ModuleRegistry registry;
   private final ErrorHandler handler;
   private final ProxyWrapper wrapper;
   private final PackageLinker linker;
   private final ThreadStack stack;
   private final TypeLoader loader; 
   
   public StoreContext(Store store){
      this(store, null);
   }
   
   public StoreContext(Store store, Executor executor){
      this.stack = new ThreadStack();
      this.wrapper = new ProxyWrapper(this);
      this.handler = new ErrorHandler(stack);
      this.interceptor = new TraceInterceptor(stack);
      this.manager = new StoreManager(store);
      this.registry = new ModuleRegistry(this);
      this.linker = new ExecutorLinker(this, executor);      
      this.loader = new TypeLoader(linker, registry, manager);
      this.matcher = new ConstraintMatcher(loader, wrapper);
      this.extractor = new TypeExtractor(loader);
      this.validator = new ExecutableValidator(matcher, extractor);
      this.binder = new FunctionBinder(extractor, stack);
      this.evaluator = new OperationEvaluator(this, executor);
      this.provider = new PlatformBridgeProvider(extractor, stack);
   }
   
   @Override
   public TypeExtractor getExtractor(){
      return extractor;
   }
   
   @Override
   public ThreadStack getStack() {
      return stack;
   }
   
   @Override
   public ProxyWrapper getWrapper() {
      return wrapper;
   }
   
   @Override
   public ErrorHandler getHandler() {
      return handler;
   }
   
   @Override
   public ProgramValidator getValidator() {
      return validator;
   }
   
   @Override
   public ConstraintMatcher getMatcher() {
      return matcher;
   }
   
   @Override
   public TraceInterceptor getInterceptor() {
      return interceptor;
   }
   
   @Override
   public ResourceManager getManager() {
      return manager;
   }
   
   @Override
   public ExpressionEvaluator getEvaluator() {
      return evaluator;
   }

   @Override
   public ModuleRegistry getRegistry() {
      return registry;
   }  
   
   @Override
   public BridgeProvider getProvider() {
      return provider;
   }

   @Override
   public FunctionBinder getBinder() {
      return binder;
   }

   @Override
   public TypeLoader getLoader() {
      return loader;
   }

   @Override
   public PackageLinker getLinker() {
      return linker;
   }
}