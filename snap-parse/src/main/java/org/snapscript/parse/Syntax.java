package org.snapscript.parse;

public enum Syntax {
   SIGN("sign", "{'-'|'+'}"),
   NUMBER("number", "?<sign>{[hexidecimal]|[binary]|[decimal]}"), 
   BOOLEAN("boolean", "{'true'|'false'}"),
   NULL("null", "'null'"),
   LITERAL("literal", "{<null>|<boolean>|<number>|[template]|[text]}"),
   ASSIGNMENT_OPERATOR("assignment-operator", "{'='|'**='|'*='|'/='|'%='|'+='|'-='|'<<='|'>>='|'>>>='|'&='|'^='|'|='}"),
   ARITHMETIC_OPERATOR("arithmetic-operator", "{'**'|'+'|'-'|'*'|'/'|'%'}"),
   BINARY_OPERATOR("binary-operator", "{'&'|'|'|'^'|'>>>'|'>>'|'<<'}"),
   COALESCE_OPERATOR("coalesce-operator", "'??'"),    
   COMPARISON_OPERATOR("comparison-operator", "{'>='|'<='|'>'|'<'|'==='|'!=='|'=='|'!='|'instanceof'|'!instanceof'}"), 
   CONDITIONAL_OPERATOR("conditional-operator", "{'&&'|'||'}"),
   PREFIX_OPERATOR("prefix-operator", "{'!'|'~'|'+'|'-'}"),
   INCREMENT_OPERATOR("increment-operator", "'++'"),
   DECREMENT_OPERATOR("decrement-operator", "'--'"),
   REFERENCE_OPERATOR("reference-operator", "{'?.'|'.'}"),
   THIS("this", "'this'"),
   CLASS("class", "'class'"),
   SUPER("super", "'super'"),
   VARIABLE("variable", "{<this>|<class>|[identifier]|[type]}"),          // ((array[x])[x] -> (<var>[])
   VARIABLE_REFERENCE("variable-reference", "{<super>|<variable>}"),
   RANGE("range", "<number>'..'<number>"),
   ARRAY("array", "{<function-invocation>|<variable-reference>}"),
   ARRAY_INDEX("array-index", "<array>+('['<argument>']')"),   
   FUNCTION("function", "[identifier]"),
   FUNCTION_INVOCATION("function-invocation", "<function>'('?<argument-list>')'"),
   REFERENCE("reference", "{<reference-type>|<reference-navigation>}"),
   REFERENCE_PART("reference-part", "{<array-index>|<function-invocation>|<construct>|<closure>|<variable-reference>|<literal>}"),
   REFERENCE_NAVIGATION("reference-navigation", "<reference-part>*(<reference-operator><reference-navigation>)"),   
   REFERENCE_TYPE("reference-type", "<array-constraint>*(<reference-operator><reference-navigation>)"),      
   LIST_ENTRY("list-entry", "<argument>"),
   LIST_ENTRY_LIST("list-entry-list", "<list-entry>*(','<list-entry>)"),
   SET_ENTRY("set-entry", "<argument>"),
   SET_ENTRY_LIST("set-entry-list", "<set-entry>*(','<set-entry>)"),
   MAP_KEY("map-key", "[identifier]"),
   MAP_ENTRY("map-entry", "(<map-key>|<literal>)':'<argument>"),
   MAP_ENTRY_LIST("map-entry-list", "<map-entry>*(','<map-entry>)"),
   TYPE_REFERENCE("type-reference", "<type-reference-part>*('.'<type-reference>)"),
   TYPE_REFERENCE_PART("type-reference-part", "[type]"),
   TRAIT_REFERENCE("trait-reference", "<trait-reference-part>*('.'<trait-reference>)"),
   TRAIT_REFERENCE_PART("trait-reference-part", "[type]"),
   CONSTRUCT_OBJECT("construct-object", "'new'' '<type-reference>'('?<argument-list>')'"),
   CONSTRUCT_LIST("construct-list", "'[]'|'['<list-entry-list>']'"),
   CONSTRUCT_ARRAY("construct-array", "'new'' '<type-reference>+('['<argument>']')"),
   CONSTRUCT_SET("construct-set", "'{}'|('{'<set-entry-list>'}')"),
   CONSTRUCT_MAP("construct-map", "'{:}'|('{'<map-entry-list>'}')"),
   CONSTRUCT("construct", "{<construct-object>|<construct-list>|<construct-array>|<construct-map>|<construct-set>}"),
   ARGUMENT("argument", "{<literal>|<annotation-declaration>|<increment-decrement-operand>|<prefix-operand>|<reference>}|<choice>|<conditional>|<calculation>"),
   ARGUMENT_LIST("argument-list", "<argument>*(','<argument>)"),
   ASSIGNMENT("assignment", "<reference>?' '<assignment-operator>?' '<expression>"),
   ASSIGNMENT_OPERAND("assignment-operand", "'('<assignment>')'"),
   PREFIX_OPERATION("prefix-operation", "<prefix-operator><reference>"),
   PREFIX_OPERAND("prefix-operand", "{<prefix-operation>|'('<prefix-operation>')'}"),
   INCREMENT("increment", "{<postfix-increment>|<prefix-increment>}"),
   PREFIX_INCREMENT("prefix-increment", "<increment-operator><reference>"),
   POSTFIX_INCREMENT("postfix-increment", "<reference><increment-operator>"),
   DECREMENT("decrement", "{<postfix-decrement>|<prefix-decrement>}"),
   PREFIX_DECREMENT("prefix-decrement", "<decrement-operator><reference>"),
   POSTFIX_DECREMENT("postfix-decrement", "<reference><decrement-operator>"),
   INCREMENT_DECREMENT("increment-decrement", "<increment>|<decrement>"),
   INCREMENT_DECREMENT_OPERAND("increment-decrement-operand", "<increment-decrement>|'('<increment-decrement>')'"),
   REFERENCE_OPERAND("reference-operand", "<reference>|'('<reference>')'"),
   VALUE_OPERAND("value-operand", "<increment-decrement-operand>|<literal>|<prefix-operand>|<reference-operand>"),
   COMPARISON_OPERAND("comparison-operand", "<value-operand>|<calculation>|<assignment-operand>|<conditional-result>|'('<comparison-operand>')'"),
   COMPARISON("comparison", "<comparison-operand>?' '<comparison-operator>?' '<comparison-operand>"),
   CONDITIONAL_OPERAND("conditional-operand", "<comparison>|<value-operand>|<assignment-operand>|<boolean>|<conditional-result>|'('<combination>')'"),
   CONDITIONAL_RESULT("conditional-result", "'('<conditional-operand>')'"),
   CONDITIONAL("conditional", "{<combination>|<conditional-operand>|'('<conditional>')'}"),
   COMBINATION("combination", "{<conditional>|<conditional-operand>}?' '<conditional-operator>?' '<conditional>"),
   CHOICE("choice", "<conditional>'?'<expression>':'<expression>"),   
   NULL_COALESCE("null-coalesce", "<expression>'??'<expression>"), // ?? is done in calculation
   SUBSTITUTE("substitute", "{<choice>|<null-coalesce>}"),   // use this over <choice>?
   CALCULATION_OPERATOR("calculation-operator", "{<arithmetic-operator>|<binary-operator>|<coalesce-operator>}"),
   CALCULATION_OPERAND("calculation-operand", "<assignment-operand>|<value-operand>|'('<calculation-operand>')'|'('<calculation-list>')'"),
   CALCULATION_LIST("calculation-list", "<calculation-operand>?' '<calculation-operator>?' '<calculation-operand>*(<calculation-operator><calculation-operand>)"),
   CALCULATION("calculation", "<calculation-list>|'('<calculation>')'"),
   EXPRESSION("expression", "{<literal>|<increment-decrement-operand>|<reference>|<prefix-operation>}|<assignment>|<calculation>|<choice>|<comparison>|<conditional>|'('<expression>')'"),
   FUNCTION_CONSTRAINT("function-constraint", "'('<parameter-list>')'"),
   TYPE_CONSTRAINT("type-constraint", "<type-reference>"),
   ARRAY_DIMENSION("array-dimension", "'[]'"),
   ARRAY_CONSTRAINT("array-constraint", "<type-reference>+<array-dimension>"),
   LIST_CONSTRAINT("list-constraint", "'[]'"),   
   SET_CONSTRAINT("set-constraint", "'{}'"),   
   MAP_CONSTRAINT("map-constraint", "'{:}'"),      
   CONSTRAINT("constraint", "{<array-constraint>|<type-constraint>|<function-constraint>|<list-constraint>|<set-constraint>|<map-constraint>}"),
   RETURN_STATEMENT("return-statement", "'return'*(?' '<expression>)';'"),
   BREAK_STATEMENT("break-statement", "'break;'"),
   CONTINUE_STATEMENT("continue-statement", "'continue;'"),
   THROW_STATEMENT("throw-statement", "'throw'?' '(<reference>|<literal>)';'"),
   ASSERT_STATEMENT("assert-statement", "'assert'?' '<conditional>';'"),
   EXPRESSION_STATEMENT("expression-statement", "(<reference>|<assignment>|<increment-decrement>)';'"),
   COMPOUND_STATEMENT("compound-statement", "'{'+<statement>'}'"),
   TERMINAL_STATEMENT("terminal-statement", "';'"),
   EMPTY_STATEMENT("empty-statement", "'{}'"),
   GROUP_STATEMENT("group-statement", "{<compound-statement>|<empty-statement>}"),
   CONTROL_STATEMENT("control-statement", "{<return-statement>|<throw-statement>|<break-statement>|<continue-statement>}"),
   STATEMENT("statement", "{<control-statement>|<try-statement>|<synchronized-statement>|<assert-statement>|<assignment-statement>|<expression-statement>|<conditional-statement>|<declaration-statement>|<group-statement>|<terminal-statement>}"),
   ASSIGNMENT_VARIABLE("assignment-variable", "[identifier]"),
   ASSIGNMENT_EXPRESSION("assignment-expression", "(<value-operand>|<reference>|<calculation>|<choice>|<conditional>)"),
   ASSIGNMENT_STATEMENT("assignment-statement", "<reference><assignment-operator><assignment-expression>';'"),   
   DECLARE_VARIABLE("declare-variable", "'var'' '<assignment-variable>?(':'<constraint>)?('='<assignment-expression>)"),
   DECLARE_CONSTANT("declare-constant", "'const'' '<assignment-variable>?(':'<constraint>)?('='<assignment-expression>)"),
   DECLARATION_STATEMENT("declaration-statement", "{<declare-variable>|<declare-constant>}';'"),   
   CONDITIONAL_STATEMENT("conditional-statement", "{<if-statement>|<while-statement>|<for-statement>|<for-in-statement>|<loop-statement>|<switch-statement>|<match-statement>}"),
   IF_STATEMENT("if-statement", "'if('<conditional>')'<statement>?('else'?' '<statement>)"),  
   WHILE_STATEMENT("while-statement", "'while('<conditional>')'<statement>"),
   FOR_STATEMENT("for-statement", "'for('(<declaration-statement>|<assignment-statement>|<terminal-statement>)<conditional>';'?(<assignment>|<increment-decrement>)')'<statement>"),
   FOR_IN_STATEMENT("for-in-statement", "'for('?('var'' ')([identifier])' ''in'?' '{<range>|<reference>}')'<statement>"),
   LOOP_STATEMENT("loop-statement", "{'loop'|'for(;;)'}<statement>"),
   SWITCH_STATEMENT("switch-statement", "'switch''('<reference>')''{'*<switch-case>?<switch-default>'}'"),
   SWITCH_CASE("switch-case", "'case'?' '<value-operand>':'*<statement>"),
   SWITCH_DEFAULT("switch-default", "'default'':'*<statement>"),
   MATCH_STATEMENT("match-statement", "'match''('<reference>')''{'*<match-case>?<match-default>'}'"),
   MATCH_CASE("match-case", "'case'?' '<value-operand>':'<expression-statement>"),
   MATCH_DEFAULT("match-default", "'default'':'<expression-statement>"), 
   SYNCHRONIZED_STATEMENT("synchronized-statement", "'synchronized''('<reference>')'<compound-statement>"),
   TRY_BLOCK("try-block", "'try'<group-statement>"),
   CATCH_BLOCK("catch-block", "'catch('<parameter-declaration>')'<group-statement>"),
   FINALLY_BLOCK("finally-block", "'finally'<group-statement>"),
   CATCH_BLOCK_LIST("catch-block-list", "+<catch-block>"),
   TRY_STATEMENT("try-statement", "<try-block>?<catch-block-list>?<finally-block>}"), 
   VARIABLE_ARGUMENT("variable-argument", "'...'"),
   PARAMETER_NAME("parameter-name", "[identifier]"),
   PARAMETER_DECLARATION("parameter-declaration", "?<annotation-list><parameter-name>?<variable-argument>?(':'<constraint>)"),
   PARAMETER_LIST("parameter-list", "?(<parameter-declaration>*(','<parameter-declaration>))"),
   CLOSURE_PARAMETER_LIST("closure-parameter-list", "'('<parameter-list>')'|?<parameter-declaration>"),
   CLOSURE("closure", "<closure-parameter-list>?' ''->'?' '{<group-statement>|<expression>}"),
   TYPE_NAME("type-name", "[type]"),
   TRAIT_NAME("trait-name", "[type]"),
   ANNOTATION_NAME("annotation-name", "[type]"),   
   ANNOTATION_LIST("annotation-list", "*<annotation-declaration>?' '"),
   ANNOTATION_DECLARATION("annotation-declaration", "'@'<annotation-name>?<annotation-attributes>"),
   ANNOTATION_ATTRIBUTES("annotation-attributes", "'('?<map-entry-list>')'"),   
   FIELD_MODIFIER("field-modifier", "{'var'|'const'|'static'|'public'|'private'}"),
   FIELD_MODIFIER_LIST("field-modifier-list", "*(<field-modifier>' ')"),
   FUNCTION_MODIFIER("function-modifier", "{'static'|'public'|'private'|'abstract'|'override'}"),
   FUNCTION_MODIFIER_LIST("function-modifier-list", "*(<function-modifier>' ')"),   
   ACCESS_MODIFIER("access-modifier", "{'public'|'private'}"),
   ACCESS_MODIFIER_LIST("access-modifier-list", "*(<access-modifier>' ')"), 
   SUPER_CONSTRUCTOR("super-constructor", "'super('?<argument-list>')'"),
   THIS_CONSTRUCTOR("this-constructor", "'this('?<argument-list>')'"),
   CLASS_FIELD("class-field", "<annotation-list><field-modifier-list><assignment-variable>?(':'<constraint>)?('='<assignment-expression>)';'"),
   CLASS_FUNCTION("class-function", "<annotation-list><function-modifier-list><function>'('<parameter-list>')'?(':'<constraint>){';'|<group-statement>}"),   
   CLASS_CONSTRUCTOR("class-constructor", "<annotation-list><access-modifier-list>'new''('<parameter-list>')'?(':'{<this-constructor>|<super-constructor>})<group-statement>"),
   CLASS_PART("class-part", "{<class-field>|<class-constructor>|<class-function>|<inner-class-definition>|<inner-enum-definition>|<inner-trait-definition>}"),
   CLASS_HIERARCHY("class-hierarchy", "?{' ''extends'' '<type-reference>|' ''with'' '<trait-reference>}*(' ''with'' '<trait-reference>)"),
   CLASS_DEFINITION("class-definition", "?<annotation-list>'class'' '<type-name>?<class-hierarchy>{'{}'|'{'*<class-part>'}'}"),
   TRAIT_CONSTANT("trait-constant", "<annotation-list><field-modifier-list><assignment-variable>?(':'<constraint>)'='<assignment-expression>';'"),
   TRAIT_HIERARCHY("trait-hierarchy", "*(' ''extends'' '<trait-reference>)"),
   TRAIT_FUNCTION("trait-function", "?<annotation-list><function-modifier-list><function>'('<parameter-list>')'?(':'<constraint>){';'|<group-statement>}"),
   TRAIT_PART("trait-part", "{<trait-constant>|<trait-function>}"),
   TRAIT_DEFINITION("trait-definition", "?<annotation-list>'trait'' '<trait-name>?<trait-hierarchy>{'{}'|'{'*<trait-part>'}'}"),   
   ENUM_HIERARCHY("enum-hierarchy", "*(' ''with'' '<trait-reference>)}"),
   ENUM_KEY("enum-key", "[identifier]"),
   ENUM_VALUE("enum-value", "<enum-key>?('('<argument-list>')')"),
   ENUM_LIST("enum-list", "<enum-value>*(','<enum-value>)"),
   ENUM_CONSTRUCTOR("enum-constructor", "<annotation-list><access-modifier-list>'new''('<parameter-list>')'<group-statement>"),
   ENUM_FIELD("enum-field", "<annotation-list><field-modifier-list><assignment-variable>?(':'<constraint>)?('='<assignment-expression>)';'"),
   ENUM_FUNCTION("enum-function", "<annotation-list><function-modifier-list><function>'('<parameter-list>')'?(':'<constraint>){';'|<group-statement>}"),
   ENUM_PART("enum-part", "{<enum-field>|<enum-constructor>|<enum-function>}"),
   ENUM_DEFINITION("enum-definition", "?<annotation-list>'enum'' '<type-name>?<enum-hierarchy>'{'<enum-list>?(';'*<enum-part>)'}'"),   
   INNER_CLASS_DEFINITION("inner-class-definition", "<class-definition>"),
   INNER_ENUM_DEFINITION("inner-enum-definition", "<enum-definition>"),
   INNER_TRAIT_DEFINITION("inner-trait-definition", "<trait-definition>"), 
   TYPE_DEFINITION("type-definition", "{<class-definition>|<trait-definition>|<enum-definition>}"),
   FULL_QUALIFIER("full-qualifier", "[qualifier]*('.'[qualifier])"),
   WILD_QUALIFIER("wild-qualifier", "[qualifier]*('.'[qualifier])'.*'"),
   IMPORT_STATIC("import-static", "'import static'' '(<full-qualifier>|<wild-qualifier>)';'"),
   IMPORT("import", "'import'' '(<full-qualifier>|<wild-qualifier>)?(' as '[type])';'"),   
   MODULE_NAME("module-name", "[identifier]"),
   MODULE_IMPORT("module-import", "<import>"),   
   MODULE_FUNCTION("module-function", "<annotation-list><access-modifier-list><function>'('<parameter-list>')'?(':'<constraint>)<group-statement>"),
   MODULE_STATEMENT("module-statement", "{<try-statement>|<synchronized-statement>|<declaration-statement>|<conditional-statement>|<type-definition>|<assignment-statement>|<expression-statement>}"),   
   MODULE_DEFINITION("module-definition", "?<annotation-list>'module'' '<module-name>{<empty-statement>|'{'*{<module-import>|<module-function>|<module-statement>}'}'}"),   
   SCRIPT_IMPORT("script-import", "<import-static>|<import>"),
   SCRIPT_FUNCTION("script-function", "'function'' '<function>'('<parameter-list>')'?(':'<constraint>)<group-statement>"),
   SCRIPT_STATEMENT("script-statement", "{<try-statement>|<synchronized-statement>|<declaration-statement>|<assert-statement>|<conditional-statement>|<type-definition>|<module-definition>|<assignment-statement>|<expression-statement>}"),
   SCRIPT_PACKAGE("script-package", "*{<script-import>|<type-definition>|<module-definition>}"),
   SCRIPT("script", "*{<script-import>|<script-function>|<script-statement>}");
   
   public final String name;
   public final String grammar;
   
   private Syntax(String name, String grammar) {
      this.name = name;
      this.grammar = grammar;
   }
   
   public String getName(){
      return name;
   }
   
   public String getGrammar() {
      return grammar;
   }
}

