/*
 * PackageBuilder.java December 2016
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

import org.snapscript.core.Context;
import org.snapscript.core.FilePathConverter;
import org.snapscript.core.Path;
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

   public Package create(Path path, String source, String grammar) throws Exception {
      String resource = path.getPath();
      SyntaxParser parser = compiler.compile();
      SyntaxNode node = parser.parse(resource, source, grammar); // source could be split here!
      Statement statement = assembler.assemble(node, path);
      String module = converter.createModule(resource);

      return new StatementPackage(statement, path, module);
   } 
}
