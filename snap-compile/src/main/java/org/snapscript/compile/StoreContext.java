package org.snapscript.compile;

import java.util.concurrent.Executor;

import org.snapscript.common.store.Store;
import org.snapscript.compile.assemble.ExecutorLinker;
import org.snapscript.compile.assemble.OperationEvaluator;
import org.snapscript.compile.validate.ContextValidator;
import org.snapscript.compile.verify.ExecutableVerifier;
import org.snapscript.core.ApplicationValidator;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.StoreManager;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeLoader;

public class StoreContext implements Context {

   private final ContextValidator validator;
   private final ExpressionEvaluator evaluator;
   private final TraceInterceptor interceptor;
   private final PlatformProvider provider;
   private final ExecutableVerifier verifier;
   private final ConstraintMatcher matcher;
   private final ResourceManager manager;
   private final FunctionIndexer indexer;
   private final FunctionResolver resolver;
   private final TypeExtractor extractor;
   private final ModuleRegistry registry;
   private final FunctionBinder binder;
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
      this.verifier = new ExecutableVerifier();
      this.interceptor = new TraceInterceptor(verifier, stack);
      this.manager = new StoreManager(store);
      this.registry = new ModuleRegistry(this, executor);
      this.linker = new ExecutorLinker(this, executor);      
      this.loader = new TypeLoader(linker, registry, manager, wrapper, stack);
      this.matcher = new ConstraintMatcher(loader, wrapper);
      this.extractor = new TypeExtractor(loader);
      this.handler = new ErrorHandler(extractor, stack);
      this.indexer = new FunctionIndexer(extractor, stack);
      this.validator = new ContextValidator(matcher, extractor, indexer, verifier);
      this.resolver = new FunctionResolver(extractor, stack, indexer);
      this.evaluator = new OperationEvaluator(this, verifier, executor);
      this.provider = new PlatformProvider(extractor, wrapper, stack);
      this.binder = new FunctionBinder(resolver, handler);
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
   public ApplicationValidator getValidator() {
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
   public PlatformProvider getProvider() {
      return provider;
   }
   
   @Override
   public FunctionResolver getResolver() {
      return resolver;
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