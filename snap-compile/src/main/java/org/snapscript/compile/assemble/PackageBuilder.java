package org.snapscript.compile.assemble;

import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.PathConverter;
import org.snapscript.core.Statement;
import org.snapscript.core.link.Package;
import org.snapscript.core.link.StatementPackage;
import org.snapscript.parse.SyntaxCompiler;
import org.snapscript.parse.SyntaxNode;
import org.snapscript.parse.SyntaxParser;

public class PackageBuilder {

   private final SyntaxCompiler compiler;
   private final PathConverter converter;
   private final Assembler assembler;   
   
   public PackageBuilder(Context context) {
      this.assembler = new OperationAssembler(context);
      this.converter = new FilePathConverter();
      this.compiler = new SyntaxCompiler();
   }

   public Package create(String resource, String source, String grammar) throws Exception {
      SyntaxParser parser = compiler.compile();
      SyntaxNode node = parser.parse(resource, source, grammar); // source could be split here!
      Statement statement = assembler.assemble(node, resource);
      String module = converter.createModule(resource);
      String path = converter.createPath(resource);

      return new StatementPackage(statement, module, path);
   } 
}
