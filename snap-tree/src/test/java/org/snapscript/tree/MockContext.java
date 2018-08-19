package org.snapscript.tree;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.ContextValidator;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.ResourceManager;
import org.snapscript.core.StoreManager;
import org.snapscript.core.constraint.transform.ConstraintTransformer;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.core.link.NoPackage;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.module.ModuleRegistry;
import org.snapscript.core.module.Path;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.CacheTypeLoader;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeLoader;

public class MockContext implements Context {
   
   private final ConstraintTransformer transformer;
   private final ConstraintMatcher matcher;
   private final ResourceManager manager;
   private final ModuleRegistry registry;
   private final TypeLoader loader;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   private final ProxyWrapper wrapper;
   private final FunctionResolver resolver;
   private final FunctionIndexer indexer;
   private final PackageLinker linker;
   private final ErrorHandler handler;
   private final FunctionBinder table;
   private final Store store;
   
   public MockContext(){
      this.linker = new TestLinker();
      this.store = new ClassPathStore();
      this.stack = new ThreadStack();
      this.wrapper = new ProxyWrapper(this);
      this.manager = new StoreManager(store);
      this.registry = new ModuleRegistry(this, null);
      this.loader = new CacheTypeLoader(linker, registry, manager, wrapper, stack);
      this.extractor = new TypeExtractor(loader);
      this.transformer = new ConstraintTransformer(extractor);
      this.indexer = new FunctionIndexer(extractor, stack);
      this.resolver = new FunctionResolver(extractor, stack, indexer);
      this.matcher = new ConstraintMatcher(loader, wrapper);
      this.handler = new ErrorHandler(extractor, stack);
      this.table = new FunctionBinder(resolver, handler);
   }

   @Override
   public ThreadStack getStack() {
      return stack;
   }
   
   @Override
   public FunctionBinder getBinder() {
      return table;
   }

   @Override
   public ErrorHandler getHandler() {
      return handler;
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
   public ContextValidator getValidator() {
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
   public FunctionResolver getResolver() {
      return resolver;
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
   public ConstraintTransformer getTransformer() {
      return transformer;
   }
   
   @Override
   public TypeLoader getLoader() {
      return loader;
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

}