package org.snapscript.tree;

import org.snapscript.tree.annotation.AnnotationDeclaration;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.annotation.AnnotationName;
import org.snapscript.tree.closure.Closure;
import org.snapscript.tree.closure.ClosureParameterList;
import org.snapscript.tree.collection.Array;
import org.snapscript.tree.collection.ArrayIndex;
import org.snapscript.tree.collection.Range;
import org.snapscript.tree.condition.Choice;
import org.snapscript.tree.condition.Combination;
import org.snapscript.tree.condition.Comparison;
import org.snapscript.tree.condition.DefaultCase;
import org.snapscript.tree.condition.ForInStatement;
import org.snapscript.tree.condition.ForStatement;
import org.snapscript.tree.condition.IfStatement;
import org.snapscript.tree.condition.LoopStatement;
import org.snapscript.tree.condition.MatchStatement;
import org.snapscript.tree.condition.NullCoalesce;
import org.snapscript.tree.condition.SwitchStatement;
import org.snapscript.tree.condition.ValueCase;
import org.snapscript.tree.condition.WhileStatement;
import org.snapscript.tree.constraint.ArrayConstraint;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.FunctionConstraint;
import org.snapscript.tree.constraint.ListConstraint;
import org.snapscript.tree.constraint.MapConstraint;
import org.snapscript.tree.constraint.SetConstraint;
import org.snapscript.tree.constraint.TypeConstraint;
import org.snapscript.tree.construct.ConstructArray;
import org.snapscript.tree.construct.ConstructList;
import org.snapscript.tree.construct.ConstructMap;
import org.snapscript.tree.construct.ConstructObject;
import org.snapscript.tree.construct.ConstructSet;
import org.snapscript.tree.construct.MapEntry;
import org.snapscript.tree.construct.MapEntryList;
import org.snapscript.tree.construct.MapKey;
import org.snapscript.tree.define.ClassConstructor;
import org.snapscript.tree.define.ClassDefinition;
import org.snapscript.tree.define.EnumConstructor;
import org.snapscript.tree.define.EnumDefinition;
import org.snapscript.tree.define.EnumKey;
import org.snapscript.tree.define.EnumList;
import org.snapscript.tree.define.EnumValue;
import org.snapscript.tree.define.MemberField;
import org.snapscript.tree.define.MemberFunction;
import org.snapscript.tree.define.ModuleDefinition;
import org.snapscript.tree.define.ModuleFunction;
import org.snapscript.tree.define.ModuleName;
import org.snapscript.tree.define.SuperConstructor;
import org.snapscript.tree.define.ThisConstructor;
import org.snapscript.tree.define.TraitConstant;
import org.snapscript.tree.define.TraitDefinition;
import org.snapscript.tree.define.TraitFunction;
import org.snapscript.tree.define.TraitName;
import org.snapscript.tree.define.TypeHierarchy;
import org.snapscript.tree.define.TypeName;
import org.snapscript.tree.function.FunctionInvocation;
import org.snapscript.tree.function.ParameterDeclaration;
import org.snapscript.tree.function.ParameterList;
import org.snapscript.tree.function.ScriptFunction;
import org.snapscript.tree.literal.BooleanLiteral;
import org.snapscript.tree.literal.NullLiteral;
import org.snapscript.tree.literal.NumberLiteral;
import org.snapscript.tree.literal.TextLiteral;
import org.snapscript.tree.operation.Assignment;
import org.snapscript.tree.operation.AssignmentStatement;
import org.snapscript.tree.operation.CalculationList;
import org.snapscript.tree.operation.CalculationOperand;
import org.snapscript.tree.operation.CalculationOperator;
import org.snapscript.tree.operation.PostfixDecrement;
import org.snapscript.tree.operation.PostfixIncrement;
import org.snapscript.tree.operation.PrefixDecrement;
import org.snapscript.tree.operation.PrefixIncrement;
import org.snapscript.tree.operation.PrefixOperation;
import org.snapscript.tree.operation.SignedNumber;
import org.snapscript.tree.reference.ReferenceNavigation;
import org.snapscript.tree.reference.ReferencePart;
import org.snapscript.tree.reference.TypeReference;
import org.snapscript.tree.reference.TypeReferencePart;
import org.snapscript.tree.template.TextTemplate;
import org.snapscript.tree.variable.Variable;

public enum Instruction {
   DECIMAL(NumberLiteral.class, "decimal"),
   HEXIDECIMAL(NumberLiteral.class, "hexidecimal"),   
   BINARY(NumberLiteral.class, "binary"),  
   BOOLEAN(BooleanLiteral.class, "boolean"),
   IDENTIFIER(TextLiteral.class, "identifier"), // identifier 
   TEMPLATE(TextTemplate.class, "template"),
   CLASS(TextLiteral.class, "class"), // identifier
   TYPE(TextLiteral.class, "type"),  // identifier
   TEXT(TextLiteral.class, "text"),
   THIS(TextLiteral.class, "this"), // identifier
   NULL(NullLiteral.class, "null"),
   NUMBER(SignedNumber.class, "number"), 
   VARIABLE(Variable.class, "variable"), 
   ARGUMENT(Argument.class, "argument"),
   RANGE(Range.class, "range"),     
   ARRAY(Array.class, "array"),      
   ARRAY_INDEX(ArrayIndex.class, "array-index"),                
   FUNCTION_INVOCATION(FunctionInvocation.class, "function-invocation"),           
   ARGUMENT_LIST(ArgumentList.class, "argument-list"),     
   REFERENCE_NAVIGATION(ReferenceNavigation.class, "reference-navigation"), 
   REFERENCE_TYPE(ReferenceNavigation.class, "reference-type"),   
   REFERENCE_PART(ReferencePart.class, "reference-part"),
   CALCULATION_LIST(CalculationList.class, "calculation-list"),
   CALCULATION_OPERATOR(CalculationOperator.class, "calculation-operator"),
   CALCULATION_OPERAND(CalculationOperand.class, "calculation-operand"),   
   COMPARISON(Comparison.class, "comparison"),
   COMBINATION(Combination.class, "combination"),        
   POSTFIX_INCREMENT(PostfixIncrement.class, "postfix-increment"),
   POSTFIX_DECREMENT(PostfixDecrement.class, "postfix-decrement"),
   PREFIX_INCREMENT(PrefixIncrement.class, "prefix-increment"),
   PREFIX_DECREMENT(PrefixDecrement.class, "prefix-decrement"),
   PREFIX_OPERATION(PrefixOperation.class, "prefix-operation"),   
   CHOICE(Choice.class, "choice"), 
   NULL_COALESCE(NullCoalesce.class, "null-coalesce"),    
   ASSIGNMENT(Assignment.class, "assignment"),
   TYPE_REFERENCE(TypeReference.class, "type-reference"),
   TYPE_REFERENCE_PART(TypeReferencePart.class, "type-reference-part"),   
   CONSTRUCT_LIST(ConstructList.class, "construct-list"),
   CONSTRUCT_ARRAY(ConstructArray.class, "construct-array"),
   CONSTRUCT_OBJECT(ConstructObject.class, "construct-object"),
   CONSTRUCT_MAP(ConstructMap.class, "construct-map"),   
   CONSTRUCT_SET(ConstructSet.class, "construct-set"),
   LIST_ENRTY_LIST(ArgumentList.class, "list-entry-list"),
   LIST_ENTRY(Argument.class, "list-entry"),
   SET_ENRTY_LIST(ArgumentList.class, "set-entry-list"),
   SET_ENTRY(Argument.class, "set-entry"),
   MAP_ENRTY_LIST(MapEntryList.class, "map-entry-list"),
   MAP_ENTRY(MapEntry.class, "map-entry"),
   MAP_KEY(MapKey.class, "map-key"),
   DECLARE_VARIABLE(DeclareVariable.class, "declare-variable"),
   DECLARE_CONSTANT(DeclareConstant.class, "declare-constant"),      
   DECLARATION_STATEMENT(DeclarationStatement.class, "declaration-statement"),
   ASSIGNMENT_STATEMENT(AssignmentStatement.class, "assignment-statement"),      
   EXPRESSION_STATEMENT(ExpressionStatement.class, "expression-statement"),
   TERMINAL_STATEMENT(EmptyStatement.class, "terminal-statement"),   
   BLANK_STATEMENT(EmptyStatement.class, "empty-statement"),
   COMPOUND_STATEMENT(CompoundStatement.class, "compound-statement"),   
   IF_STATEMENT(IfStatement.class, "if-statement"),
   BREAK_STATEMENT(BreakStatement.class, "break-statement"),
   CONTINUE_STATEMENT(ContinueStatement.class, "continue-statement"),
   RETURN_STATEMENT(ReturnStatement.class, "return-statement"),      
   ASSERT_STATEMENT(AssertStatement.class, "assert-statement"),    
   WHILE_STATEMENT(WhileStatement.class, "while-statement"),
   FOR_STATEMENT(ForStatement.class, "for-statement"),
   FOR_IN_STATEMENT(ForInStatement.class, "for-in-statement"),
   LOOP_STATEMENT(LoopStatement.class, "loop-statement"),
   SWITCH_STATEMENT(SwitchStatement.class, "switch-statement"),
   SWITCH_CASE(ValueCase.class, "switch-case"),
   SWITCH_DEFAULT(DefaultCase.class, "switch-default"),
   MATCH_STATEMENT(MatchStatement.class, "match-statement"),
   MATCH_CASE(ValueCase.class, "match-case"),
   MATCH_DEFAULT(DefaultCase.class, "match-default"),      
   FUNCTION_CONSTRAINT(FunctionConstraint.class, "function-constraint"),
   TYPE_CONSTRAINT(TypeConstraint.class, "type-constraint"),
   ARRAY_CONSTRAINT(ArrayConstraint.class, "array-constraint"),
   LIST_CONSTRAINT(ListConstraint.class, "list-constraint"),   
   SET_CONSTRAINT(SetConstraint.class, "set-constraint"),   
   MAP_CONSTRAINT(MapConstraint.class, "map-constraint"),   
   CONSTRAINT(Constraint.class, "constraint"),   
   VARIABLE_ARGUMENT(Modifier.class, "variable-argument"),
   PARAMETER(ParameterDeclaration.class, "parameter-declaration"),
   PARAMETER_LIST(ParameterList.class, "parameter-list"),
   CLOSURE_PARAMETER_LIST(ClosureParameterList.class, "closure-parameter-list"),
   CLOSURE(Closure.class, "closure"),
   THROW(ThrowStatement.class, "throw-statement"),    
   TRY_CATCH(TryCatchStatement.class, "try-catch-statement"),
   TRY_FINALLY(TryFinallyStatement.class, "try-finally-statement"),     
   TYPE_NAME(TypeName.class, "type-name"),
   TRAIT_NAME(TraitName.class, "trait-name"),     
   TRAIT_HIERARCHY(TypeHierarchy.class, "trait-hierarchy"),
   TRAIT_CONSTANT(TraitConstant.class, "trait-constant"),   
   TRAIT_DEFINITION(TraitDefinition.class, "trait-definition"),    
   TRAIT_FUNCTION(TraitFunction.class, "trait-function"),
   ENUM_KEY(EnumKey.class, "enum-key"),
   ENUM_HIERARCHY(TypeHierarchy.class, "enum-hierarchy"),
   ENUM_FIELD(MemberField.class, "enum-field"),
   ENUM_FUNCTION(MemberFunction.class, "enum-function"),   
   ENUM_DEFINITION(EnumDefinition.class, "enum-definition"), 
   ENUM_CONSTRUCTOR(EnumConstructor.class, "enum-constructor"), 
   ENUM_VALUE(EnumValue.class, "enum-value"),
   ENUM_LIST(EnumList.class, "enum-list"),     
   CLASS_HIERARCHY(TypeHierarchy.class, "class-hierarchy"),
   CLASS_DEFINITION(ClassDefinition.class, "class-definition"),
   CLASS_FIELD(MemberField.class, "class-field"),
   CLASS_FUNCTION(MemberFunction.class, "class-function"),   
   CLASS_CONSTRUCTOR(ClassConstructor.class, "class-constructor"),  
   ANNOTATION_NAME(AnnotationName.class, "annotation-name"),
   ANNOTATION_LIST(AnnotationList.class, "annotation-list"),
   ANNOTATION_DECLARATION(AnnotationDeclaration.class, "annotation-declaration"),
   FIELD_MODIFIER(Modifier.class, "field-modifier"),
   FIELD_MODIFIER_LIST(ModifierList.class, "field-modifier-list"),
   FUNCTION_MODIFIER(Modifier.class, "function-modifier"),
   FUNCTION_MODIFIER_LIST(ModifierList.class, "function-modifier-list"),
   ACCESS_MODIFIER(Modifier.class, "access-modifier"),
   ACCESS_MODIFIER_LIST(ModifierList.class, "access-modifier-list"), 
   SUPER_CONSTRUCTOR(SuperConstructor.class, "super-constructor"),
   THIS_CONSTRUCTOR(ThisConstructor.class, "this-constructor"),
   EXPRESSION(Expression.class, "expression"),
   WILD_QUALIFIER(WildQualifier.class, "wild-qualifier"),
   FULL_QUALIFIER(FullQualifier.class, "full-qualifier"),
   IMPORT(Import.class, "import"), 
   IMPORT_STATIC(ImportStatic.class, "import-static"),
   MODULE_NAME(ModuleName.class, "module-name"),    
   MODULE_FUNCTION(ModuleFunction.class, "module-function"),     
   MODULE_DEFINITION(ModuleDefinition.class, "module-definition"),    
   SCRIPT_FUNCTION(ScriptFunction.class, "script-function"),
   SCRIPT_PACKAGE(ScriptPackage.class, "script-package"),
   SCRIPT(Script.class, "script");
   
   public final Class type;
   public final String name;
   
   private Instruction(Class type, String name){
      this.type = type;
      this.name = name;
   }

   public String getName(){
      return name;
   }
   
   public Class getType(){
      return type;
   }
   
   public int getCode() {
      return ordinal();
   }
   
   public static Instruction resolveInstruction(int code) {
      Instruction[] instruction = Instruction.values();
      
      if(instruction.length > code) {
         return instruction[code];
      }
      return null;
   }
}
   