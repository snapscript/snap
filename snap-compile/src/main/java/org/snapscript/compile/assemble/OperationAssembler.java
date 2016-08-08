package org.snapscript.compile.assemble;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.error.ThreadStack;
import org.snapscript.parse.Line;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.parse.Token;
import org.snapscript.tree.Operation;
import org.snapscript.tree.OperationResolver;

public class OperationAssembler implements Assembler {
   
   private final OperationBuilder builder;
   private final OperationResolver resolver;
   private final Context context;
   private final Object[] empty;

   public OperationAssembler(Context context) {
      this.builder = new OperationBuilder(context);
      this.resolver = new OperationResolver(context);
      this.empty = new Object[]{};
      this.context = context;
   }
   
   @Override
   public <T> T assemble(SyntaxNode token, String name) throws Exception {
      ThreadStack stack = context.getStack();
      Object value = create(token, name, 0);
      
      stack.clear();
      return (T)value;
   }
   
   private Object create(SyntaxNode node, String name, int depth) throws Exception {
      List<SyntaxNode> children = node.getNodes();
      String grammar = node.getGrammar();
      Operation type = resolver.resolve(grammar);
      int size = children.size();
      
      if (type == null) {
         return createChild(node, name, children, type,depth);
      }
      if (size > 0) {
         return createBranch(node, name, children, type,depth);
      }
      return createLeaf(node, name, children, type,depth);
   }
   
   private Object createBranch(SyntaxNode node, String name, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      Type type = operation.getType();
      Line line = node.getLine();
      int size = children.size();
      
      if(size > 0) {
         Object[] arguments = new Object[size];

         for (int i = 0; i < size; i++) {
            SyntaxNode child = children.get(i);
            Object argument = create(child, name, depth+1);

            arguments[i] = argument;
         }
         return builder.create(type, arguments, line);
      }
      return builder.create(type, empty, line);
   }

   private Object createChild(SyntaxNode node, String name, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      String grammar = node.getGrammar();
      int size = children.size();
      
      if (size > 1) {
         throw new InternalStateException("No type defined for " + grammar);
      }
      if (size > 0) {
         SyntaxNode child = children.get(0);

         if (child == null) {
            throw new InternalStateException("No child for " + grammar);
         }
         return create(child, name, depth);
      }
      if (size > 0) {
         return createBranch(node, name, children, operation, depth);
      }
      return createLeaf(node, name, children, operation, depth);
   }
   
   private Object createLeaf(SyntaxNode node, String name, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      Token token = node.getToken();     
      Line line = node.getLine();
      
      if (operation != null) {
         Type type = operation.getType();
         
         if (token == null) {
            return builder.create(type, empty, line); // no line number????
         }      
         return builder.create(type, new Object[]{token}, line);
      }
      return token;
   }
}
