/*
 * OperationAssembler.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.compile.assemble;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.stack.ThreadStack;
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
   public <T> T assemble(SyntaxNode token, Path path) throws Exception {
      ThreadStack stack = context.getStack();
      Object value = create(token, path, 0);
      
      stack.clear();
      return (T)value;
   }
   
   private Object create(SyntaxNode node, Path path, int depth) throws Exception {
      List<SyntaxNode> children = node.getNodes();
      String grammar = node.getGrammar();
      Operation type = resolver.resolve(grammar);
      int size = children.size();
      
      if (type == null) {
         return createChild(node, path, children, type,depth);
      }
      if (size > 0) {
         return createBranch(node, path, children, type,depth);
      }
      return createLeaf(node, path, children, type,depth);
   }
   
   private Object createBranch(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      Type type = operation.getType();
      Line line = node.getLine();
      int size = children.size();
      
      if(size > 0) {
         Object[] arguments = new Object[size];

         for (int i = 0; i < size; i++) {
            SyntaxNode child = children.get(i);
            Object argument = create(child, path, depth+1);

            arguments[i] = argument;
         }
         return builder.create(type, arguments, line);
      }
      return builder.create(type, empty, line);
   }

   private Object createChild(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
      String grammar = node.getGrammar();
      int size = children.size();
      
      if (size > 1) {
         throw new InternalStateException("No type defined for '" + grammar + "'");
      }
      if (size > 0) {
         SyntaxNode child = children.get(0);

         if (child == null) {
            throw new InternalStateException("No child for '" + grammar + "'");
         }
         return create(child, path, depth);
      }
      if (size > 0) {
         return createBranch(node, path, children, operation, depth);
      }
      return createLeaf(node, path, children, operation, depth);
   }
   
   private Object createLeaf(SyntaxNode node, Path path, List<SyntaxNode> children, Operation operation, int depth) throws Exception {
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
