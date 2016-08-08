package org.snapscript.parse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SyntaxTree {

   private final Comparator<SyntaxNode> comparator;
   private final List<SyntaxCursor> nodes;
   private final LexicalAnalyzer analyzer;
   private final GrammarIndexer indexer;
   private final AtomicInteger commit;
   private final PositionStack stack;
   private final String resource;
   private final String grammar;

   public SyntaxTree(GrammarIndexer indexer, String resource, String grammar, char[] original, char[] source, short[] lines, short[] types) {
      this.analyzer = new TokenScanner(indexer, resource, original, source, lines, types);
      this.comparator = new SyntaxNodeComparator();
      this.nodes = new ArrayList<SyntaxCursor>();
      this.commit = new AtomicInteger();
      this.stack = new PositionStack();
      this.resource = resource;
      this.indexer = indexer;
      this.grammar = grammar;
   } 

   public SyntaxBuilder mark() {   
      int index = indexer.index(grammar);
      int depth = stack.depth(0, index);

      if (depth >= 0) {
         throw new ParseException("Tree has been created");
      }
      stack.push(0,index);
      return new SyntaxCursor(nodes, index, 0);
   }
   
   public SyntaxNode commit() { 
      int size = nodes.size();
      
      if(size > 2) {
         throw new ParseException("Tree has more than one root");
      }
      int mark = analyzer.mark();
      int count = analyzer.count();
      
      if(mark != count) {
         int error = commit.get(); // last successful commit
         Line line = analyzer.line(error);
         
         if(resource != null) {
            throw new ParseException("Syntax error in '" + resource + "' at line " + line);
         }  
         throw new ParseException("Syntax error at line " + line);
      }
      return create();
   }
   
   public SyntaxNode create() {
      int size = nodes.size();
      
      if(size > 2) {
         throw new ParseException("Tree has more than one root");
      }
      SyntaxCursor cursor = nodes.get(0);
      SyntaxNode node = cursor.create();
      
      if(node == null) {
         throw new ParseException("Tree has no root");
      }
      return node;
   }

   private class SyntaxCursor implements SyntaxBuilder {

      private List<SyntaxCursor> parent;
      private List<SyntaxCursor> nodes;
      private Token value;
      private int grammar;
      private int start;

      public SyntaxCursor(List<SyntaxCursor> parent, int grammar, int start) {
         this.nodes = new ArrayList<SyntaxCursor>();
         this.grammar = grammar;
         this.parent = parent;
         this.start = start;
      }      
      
      public SyntaxNode create() {
         return new SyntaxResult(nodes, value, grammar, start);
      }

      @Override
      public int position() {
         return analyzer.mark();
      }

      @Override
      public SyntaxBuilder mark(int grammar) {              
         int off = analyzer.mark();
         int index = stack.depth(off, grammar); // this is slow!!

         if (index <= 0) {
            stack.push(off, grammar);
            return new SyntaxCursor(nodes, grammar, off);
         }
         return null;
      }    

      @Override
      public int reset() {
         int mark = analyzer.mark();
            
         stack.pop(start, grammar);
         analyzer.reset(start); // sets the global offset
         return mark;
      }

      @Override
      public void commit() {
         int mark = analyzer.mark();
         int error = commit.get();
         int value = stack.pop(start, grammar);

         if (value != -1) {
            if(mark > error) {
               commit.set(mark);
            }
            parent.add(this);
         }
      }

      @Override
      public boolean literal(String text) {
         Token token = analyzer.literal(text);

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }

      @Override
      public boolean decimal() {
         Token token = analyzer.decimal();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }
      
      @Override
      public boolean binary() {
         Token token = analyzer.binary();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }

      @Override
      public boolean hexidecimal() {
         Token token = analyzer.hexidecimal();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }

      @Override
      public boolean identifier() {
         Token token = analyzer.identifier();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }
      
      @Override
      public boolean qualifier() {
         Token token = analyzer.qualifier();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }     
      
      @Override
      public boolean type() {
         Token token = analyzer.type();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }       

      @Override
      public boolean text() {
         Token token = analyzer.text();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }
      
      @Override
      public boolean template() {
         Token token = analyzer.template();

         if (token != null) {
            value = token;
            return true;
         }
         return false;
      }
   }

   private class SyntaxResult implements SyntaxNode {

      private List<SyntaxCursor> children;
      private Token token;
      private int grammar;
      private int start;

      public SyntaxResult(List<SyntaxCursor> children, Token token, int grammar, int start) {
         this.children = children;
         this.grammar = grammar;
         this.token = token;
         this.start = start;
      }

      @Override
      public List<SyntaxNode> getNodes() {
         int size = children.size();
         
         if(size > 0) {
            List<SyntaxNode> result = new ArrayList<SyntaxNode>(size);
            
            for(int i = 0; i < size; i++) {
               SyntaxCursor child = children.get(i);
               SyntaxNode node = child.create();
               
               if(node != null) {
                  result.add(node);
               }
            }         
            if(size > 1) {
               Collections.sort(result, comparator);
            }
            return result;
         }
         return Collections.emptyList();
      }

      @Override
      public String getGrammar() {
         return indexer.value(grammar);
      }
      
      @Override
      public Line getLine() {
         return analyzer.line(start);
      }

      @Override
      public Token getToken() {
         return token;
      }

      @Override
      public int getStart() {
         return start;
      }
      
      @Override
      public String toString(){
         return indexer.value(grammar);
      }
   } 
}
