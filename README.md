![Snap](https://raw.githubusercontent.com/snapscript/snap-site/master/www.snapscript.org/img/logo.png)
  
Snap is an optionally typed object oriented language. It borrows concepts and constructs from many sources
including JavaScript, Python, Java, Go and Scala amongst others. It is interpreted and has no 
intermediate representation, so there is no need to compile or build your application.

The interpreter has been built from the ground up, no tools or libraries have been used. As a result the
project is small, fully self contained, and can easily be embedded or run as a standalone application. 
Here you will get an overview on how the interpreter works and the language.
  
  * [Overview](#overview)    
      * [Parallel Compilation](#parallel-compilation)
        * [Scanner](#scanner)      
        * [Grammar](#grammar)      
        * [Lexical Analysis](#lexical-analysis)
        * [Parser](#parser)
        * [Assembler](#assembler)
      * [Static Analysis](#static-analysis)      
      * [Evaluation](#evaluation)
      * [Command Directive](#command-directive)
      * [Example Programs](#example-programs)
        * [Mario](#mario)
        * [Flappy Bird](#flappy-bird)            
        * [Space Invaders](#space-invaders)
        * [Tetris](#tetris)      
  * [Language](#language)      
      * [Basic Types](#basic-types)
          * [Booleans](#booleans)
          * [Numbers](#numbers)
          * [Strings](#strings)
          * [Arrays](#arrays)
          * [Collections](#collections)
      * [Operators](#operators)
          * [Arithmetic Operators](#arithmetic-operators)
          * [Relational Operators](#relational-operators)
          * [Bitwise Operators](#bitwise-operators)
          * [Logical Operators](#logical-operators)      
      * [Conditions](#conditions)
          * [If Statement](#if-statement)
          * [Else Statement](#else-statement)
          * [Unless Statement](#unless-statement) 
          * [Assert Statement](#assert-statement) 
          * [Debug Statement](#debug-statement)             
          * [Ternary Operator](#ternary-operator)    
          * [Null Coalesce](#null-coalesce)                 
      * [Loops](#loops)
          * [While Statement](#while-statement)
          * [Until Statement](#until-statement)      
          * [For Statement](#for-statement)
          * [For In Statement](#for-in-statement)
          * [Loop Statement](#loop-statement)   
      * [Exceptions](#exceptions)
          * [Catch Statement](#catch-statement)
          * [Finally Statement](#finally-statement)   
      * [Functions](#functions)
          * [Declaration](#declaration)
          * [Type Constraints](#type-constraints)      
          * [Variable Arguments](#variable-arguments)
          * [Closures](#closures)
          * [Function Handles](#function-handles)      
          * [Generic Functions](#generic-functions)
          * [Coroutines](#coroutines)
          * [Async Await](#async-await)
          * [Blank Parameters](#blank-parameters)      
      * [Types](#types)
          * [Class](#class)
          * [Enumeration](#enumeration)      
          * [Trait](#trait)     
          * [Module](#module)   
          * [Annotations](#annotations)           
          * [Type Alias](#type-alias)     
          * [Import](#import)   
          * [Coercion](#coercion)
          * [Platform Integration](#platform-integration)  
  * [Tools](#tools)
      * [Command Line Interpreter](#command-line-interpreter)
      * [Development Environment](#development-environment)    
          * [Breakpoints](#breakpoints)
          * [Console](#console)
          * [Variables](#variables)    
          * [Threads](#threads)    
          * [Process View](#process-view)    
          * [Debug Perspective](#debug-perspective)    
          * [Full Screen](#full-screen)                                                    
      * [Debug Agent](#debug-agent)
      * [Android](#android)                                                              

## Overview

Snap is an open source, optionally typed, object oriented scripting language for the Java platform. 
The learning curve is small for anyone with experience of Java, JavaScript, TypeScript, or Scala, 
amongst others. It has excellent integration with the host platform, and can do whatever can be 
done with Java and much more.

The language is ideal for embedding in to an existing application, and is a fraction of the size 
of similar languages for the Java platform. In addition to embedding it can be run as a standalone 
interpreter and has an development environment which allows scripts to be debugged and profiled.

### Parallel Compilation

Snap programs can be separated in to multiple source files that define the types and functions representing 
the execution flow. To minimise start times the parsing and assembly of the source is performed in parallel. 
Once defined the execution graph is joined in to a single executable and static analysis is performed.

The tools and frameworks required to parse and assemble the source code are all custom and written from the 
ground up with performance and correctness being the primary goals. In most conventional implementations
a grammar is used to generate the parser, however for flexibility this implementation processes the grammar
at runtime as the program starts, the parser has no prior knowledge of the grammar. This architecture simplifies 
the implementation and makes the language more extensible.

#### Scanner

In the initial phase of compilation the source is passed through a scanner and compressor. This removes 
comments and command directives from the source text in addition to whitespace that has no semantic value.
When the scanner has completed it emits three segments representing the compressed source text, the line
numbers the source was scanned from, and a type index classifying the source characters.

#### Grammar 

To make sense of the source code a custom grammar is required. The grammar used for compilation of
the Snap language leverages a custom framework that uses a variant of Bacus Naur Form (BNF). It is defined 
using special rules and literal values that form the basis of a recursive descendant parser.

| Rule  | Semantics |
| ------------- | ------------- |
| &#124;       | Represents a logical OR  |
| &#42;       | Represents one or more  |
| &#43;       | Represents at least once |
| ?      | Represents one or none  |
| &lt;&gt;       | Define a production |
| ()       | Group productions and literals  |
| {}       | Group productions where first match wins  |
| _       | Represents whitespace |
| []      | Represents a symbol  |
| ''       | Represents a literal text value  |

The formal grammar for the language is defined with these rules, it can be found with the link below. 

[Language Grammar](https://github.com/snapscript/snap/blob/master/snap-parse/src/main/resources/grammar.txt)

#### Lexical Analysis

The lexical analysis phase indexes the source in to a stream of tokens or lexemes. A token can represent
one or more primitive character sequences that are known to the parser. For example a quoted string, a 
decimal number, or perhaps a known keyword defined in the grammar. To categorise the tokens the formal
grammar is indexed in to a sequence of literals. If a token matches a known literal then it
is classified as a literal. Any given token can contain a number of separate classifications which enables
the parser to determine based on the grammar and its context what the token represents.

When this phase of processing completes there is an ordered sequence of classified tokens. Each token
will have the line number it was extracted from in addition to a bitmask describing the classifications
it has received. It is up to the parser to map these tokens to the formal grammar.

#### Parser

The parser consumes the sequence of categorised tokens produced by the lexer. The parser has backtracking
semantics and is performed in two phases. The first phase is to the map the tokens against the grammar and
the second phase is to produce an abstract syntax tree (AST).

#### Assembler

The final phase of the compilation process is assembly. This process uses a configured set of instructions
to map top level grammars to nodes within an execution graph. Configuring a set of instructions facilitates
a dependency injection mechanism which is used to build the program. 

The AST is traversed in a depth first manner to determine what the instruction dependencies are needed. As the
traversal retreats back up from the leafs of the tree to the root instructions are assembled. This process is
similar to how many other dependency injection system works.

[Language Instructions](https://github.com/snapscript/snap/blob/master/snap-tree/src/main/resources/instruction.txt)

### Static Analysis

As a program grows large so to does its complexity. To manage this complexity static analysis is performed across 
the entire codebase. The level of static analysis performed is up to the developer as types are optional. Access 
modifiers are also provided to describe intent and visibility of functions and variables.

When leveraging types further qualification can be given in the form of generics. Generics allow the developer
to describe the types of parameters that can be used for a specific declaration. 

### Evaluation

Code evaluation is the process of transforming text to code at runtime. This can be useful when you want
to perform some dynamic task. In languages such as Java the reflection framework allows developers to 
introspect and execute code in a dynamic way. With evaluation you can achieve similar functionality without
the boilerplate. Internally evaluations cache the execution trees they represent which eliminates 
the performance overheads.

```js
let instance = eval("new " + type + "()");
```


### Command Directive

The command directive is used to tell command interpreters where the interpreter for the source is located. This is 
is often called the 'shebang' directive and is interpreted by common shells like bash. The first line of any Snap 
source file can contain this command directive.

```sh
#!/usr/bin/env snap
```

### Example Programs

The best way to learn any language is through examples. Below is a collection of examples from applications that
have been written in Snap. The source code for these examples are available on Github and are free to download.

#### Mario

This is a clone of the Mario Game comes with the full source code in addition to the images and sounds. It has
been written twice, once with full static typing and once with dynamic typing. Below is a YouTube video of the 
program being run and debugged with Snap Studio.

[![Debug Desktop Game](http://img.youtube.com/vi/6vo2y83unG0/0.jpg)](https://www.youtube.com/watch?v=6vo2y83unG0)

[Source Code - Statically Typed](https://github.com/snapscript/snap-develop/tree/master/snap-studio/work/demo/games/src/mario)

[Source Code - Dynamically Typed](https://github.com/snapscript/snap-develop/tree/master/snap-studio/work/games/mario/src/mario)
   
[Source Code - Android](https://github.com/snapscript/snap-develop/blob/master/snap-studio/work/android/mario/src/mario/start.snap)

#### Flappy Bird

This is a clone of the Flappy Bird game and is targeted for Android. Below is a YouTube vide of the application
being run and debugged remotely with Snap Studio.

[![Debug Android Game](http://img.youtube.com/vi/w-baBQbZ5dI/0.jpg)](https://www.youtube.com/watch?v=w-baBQbZ5dI)

[Source Code](https://github.com/snapscript/snap-develop/tree/master/snap-studio/work/android/flappybird/src/flappybird)
        
#### Space Invaders

This is a very basic clone of the classic Space Invaders game. The implementation is short but leverages some
of the more interesting language features such as async await.

[Source Code](https://github.com/snapscript/snap-develop/tree/master/snap-studio/work/demo/games/src/spaceinvaders)
  
#### Tetris

This is a very basic clone of the classic Tetris game. The implementation does not leverage graphics or sounds and
shapes are painted on the screen with AWT primitives.

[Source Code](https://github.com/snapscript/snap-develop/tree/master/snap-studio/work/demo/games/src/tetris)
  
### Language  
  
Learning how to code applications with Snap is easy, particularly if you have experience with Java, Javascript, 
or a similar language. Below you will find various sections illustrating the basics, where you will learn about 
types, functions, and the various statements and expressions that can be used. 
  
#### Basic Types

For programs to be useful, we need to be able to work with some of the simplest units of data such as numbers, 
strings, structures, boolean values, and the like. Support for these basic types is much the same as you would 
expected for Java, with some additional features such as string templates, map, set, and list literals.

In order to reference values they must be associated with a variable. Variables are declared with the keyword 
```let``` or ```const```. A variable can have an optional constraint by declaring a type. If constrained a 
variable can only reference values of the declared type.

```js
let v = 22; // v can reference any type
let i: Integer = 22; // i can only reference integers
let d: Double = 22.0; // d can only reference doubles
const c = 1.23; // c is constant, it cannot change
```

##### Booleans

The most basic type is the simple true or false value, which is called a boolean value.

```js
let a = true; // value a is true
let b = false; // false
let c = Boolean.FALSE; // type constraint of Boolean
let d: Boolean = Boolean.TRUE; // like Boolean d = Boolean.TRUE
let e = Boolean.FALSE; // like Object e = Boolean.FALSE
```

##### Numbers

The most basic type is the simple true or false value, which is called a 'boolean' value.

```js
let binary = 0b0111011; // binary literal
let hex = 0xffe16; // hexidecimal literal
let int = 11;
let real = 2.13;
let typed: Integer = 22; // integer value 22
let coercion: Double = "1.234e2"; // coercion of string to double
```

##### Strings

A fundamental part of creating programs is working with textual data. As in other languages, we use the type string to refer to these textual types. Strings are represented by characters between either a single quote or a double quote. When characters are between double quotes they are interpolated, meaning they have expressions evaluated within them. These expressions start with the dollar character. All strings can span multiple lines.

```js
let string = 'Hello World!'; // literal string
let template = "The sum of 1 and 2 is ${1 + 2}"; // interpolated string
let concat = "The sum of 1 and 2 is " + (1 + 2); // concatenation
```

##### Arrays

The most basic type is the simple true or false value, which is called a 'boolean' value.

```js
let array = new String[10]; // array of strings
let bytes = new Byte[11]; // primitive byte[11]
let byte = array[1]; // reference element in array
let matrix = new Long[10][22]; // multidimensional long[10][22];
let long = matrix[2][3]; // reference multidimensional
```

#### Collections

Complex data structures can be represented with a simple and straight forward syntax. Collection types found in Java such as maps, sets, and lists can be represented as follows.

```js
let set = {1, 2, "x", "y"}; // creates a LinkedHashSet
let list = [1, 2, 3]; // creates an ArrayList
let map = {"a": 1, "b": 2}; // creates a LinkedHashSet
let empty = {:}; // creates an empty map
let mix = [1, 2, {"a": {"a", "b", [55, 66]}}]; // mix collection types
```

### Operators

Operators are special symbols that perform specific operations on one, two, or three operands, and then return a result. They are typically used to manipulate or compare values.


#### Arithmetic Operators

Arithmetic operators are used in mathematical expressions in the same way that they are used in algebra.

```js
let a = 10;
let b = 20;
let c = a + b; // add is 30
let d = b - a; // subtract is 10
let e = b / a; // divide is 2
let f = a * b; // multiply is 200
let g = b % a; // modulus is 0
let h = a++; // a is 11 and h is 10
let i = b--// b is 19 and i is 20
let j = --a; // a is 10 and j is 10
let k = ++b; // b is 20 as is k
```

#### Relational Operators

Relational operators are used to make comparisons, such as equal to, not equal to, greater than, less than.

```js
let a = 10;
let b = 20;
let c = a == b // equal operator, c is false
let d = a != b; // not equal operator, d is true
let e = a > b; // greater than operator, e is false
let f = a < b; // less than operator, f is true
let g = a <= b; // g is false
let h = a >= b; // h is true
```

#### Bitwise Operators

Bitwise operators are used to manipulate numbers, typically integers, at the byte level. They do so by change the binary representation of the value.

```js
let a = 0b00111100;
let b = 0b00001101;
let c = a & b; // bitwise and, c is 00001100
let d = a | b; // bitwise or, d is 00111101
let e = a & b; // bitwise xor, e is 00110001
let f = ~a; // f is 11000011
let g = f >> 2; // f is 00110000
let h = f << 2; // h is 11000000
let i = f >>> 2; // unsigned shift, i is 00110000
```

#### Logical Operators

Logical operators are typically used to combine multiple relational operations in to a single boolean result.

```js
let a = 1;
let b = 3;
let c = true;
let d = false;
let e = a && b; // e is false
let f = a || b; // f is true
let g = !d; // not operator, g is true
let h = b > a && a == 1; // logical and of, h is true
let i = b > a && a != 1; // i is false
```

### Conditions

Conditional statements are used to perform different actions based on different conditions.


#### If Statement

The if statement is used to specify a group of statements to execute if a statement is true.

```js
const a = 2;
const b = 3;

if(a < b) { // true
   println("a > b"); // prints as a < b
}
```

#### Else Statement

The else statement is used to specify a group of statements to execute if a statement is false.

```js
const a = 2;
const b = 3;

if(a >= b) { // false
   println("a >= b");
} else {
   println("a < b"); // prints as a < b
}
```

#### Unless Statement

The unless statement is used to specify a group of statements to execute if a statement is false.

```js
const a = 2;
const b = 3;

unless(a > b) { // false
   println("a > b"); // prints as a < b
}
```

#### Assert Statement

The assert statement is used to determine if an expression evaluates to true or false. If the expression evaluates 
to true the operation has no effect, otherwise an assertion exception is thrown.

```js
const a = 2;
const b = 3;

assert a < b;
assert a > b; // assert exception
```

#### Debug Statement

The debug statement is used to suspend any attached debugger if and expression evaluates to true. This can be useful
if there is a specific part of the program that you want to evaluate given a known state of execution. It is similar
to the debugger statement for JavaScript with the addition of logic predicate the suspension.

```js   
debug a * b > 4; // suspend the debugger if true
```

#### Ternary Operator

To make statements more concise there is a ternary operator.

```js
let a = 2;
let b = 3;

println(a >= b ? "a >= b" : "a < b"); // prints a < b
```

#### Null Coalesce

The null coalesce operator is similar to the ternary operator with one exception, the evaluation is whether a value is null.

```js
let a = null;
let b = 3;

println(a ?? b); // prints b
```

### Loops

Loops are used to perform a group of statements a number of times until a condition has been satisfied.

#### While Statement

The while statement is the simplest conditional statement. It repeats a group of statements while the condition it evaluates is false.

```js
let n = 0;

while(n < 10) { // conditional loop
   n++;
}
```

#### For Statement

The for statement is typically used to count over a range of numeric values. It contains three parts, a declaration, a condition, and an optional statement which is evaluated at the end of the loop.

```js
for(let i = 0; i < 10; i++){ // loops from 1 to 10
   if(i % 2 == 0) {
      continue; // continue loop
   }
   println(i);  // prints only odd numbers
}
```

#### For In Statement

The for in statement offers a simpler way to iterate over a range of values, a collection, or an array.

```js
let list = [35, 22, 13, 64, 53];

for(e in list){ // iterates over the list
   println(e);
}

for(e in 0..9) { // iterates from 0 to 9
   if(e == 7) {
      break; // exit loop when e is 7
   }
   println(e); // prints from 0 to 6
}
```
#### Loop Statement

The loop statement offers a way to specify an infinite loop, it does not evaluate any condition.

```js
let n = 0;

loop { // infinite loop
   if(n++ > 100) {
      break;
   }
}
```

### Exceptions

Exceptions are used to indicate an error has occurred. It offers a simple means to return control to a 
calling function, which can then handle the error. Typically an exception object is thrown, however it is 
possible to throw any type.

#### Catch Statement

In order to catch an exception the throwing statement needs to be wrapped in a try catch statement. This 
statement basically allows the program to try to execute a statement or group of statements, if during 
execution an exception is thrown then an error handling block is executed.

```js
try {
   throw new IllegalStateException("some error");
} catch(e: IllegalStateException) {
   e.printStackTrace();
}
```

#### Finally Statement

The finally statement is a group of statements that are always executed regardless of whether an exception is thrown.

```js
try {
   throw "throw a string value";
} catch(e) {
   println(e);
} finally {
   println("finally always runs");
}
```

### Functions

Functions group together control structures, operations, and method calls. These functions can then be called when needed, and the code contained within them will be run. This makes it very easy to reuse code without having to repeat it within your script.

#### Declaration

The most basic type of function is declared with a name and a specific number of parameters. Such a method can then be called using the declared name by passing in a right number of arguments.

```js
let r = max(11, 3, 67); // r is 67

func max(a, b) {
   return a > b ? a : b;
}

func max(a, b, c) { // function overloading
   return a < b ? max(a, c) : max(b, c);
}
```

#### Type Constraints

In order to bind invocations to the correct function implementation it can be declared with optional type constraints. These type constraints will ensure that variables of a specific type will be bound to the correct implementation.

```js
let x: Double = 11.2;
let y: Integer = 11;
let z: String = "11";

f(x); // prints double 11.2
f(y); // prints integer 11
f(z); // prints string 11
f(true); // type coercion to string, prints string true

func f(x: Integer) {
   println("integer ${x}");
}

func f(x: Double) {
   println("double ${x}");
}

func f(x: String) {
   println("string ${x}");
}
```

#### Variable Arguments

At times it can be useful to provide a large number of arguments to a function. To achieve this the last parameter can be declared with a variable argument modifier.

```js
let result = sum(0, 13, 44, 234, 1, 3); 

func sum(offset, numbers...){ // variable arguments
   let size = numbers.size();
   let sum = 0;
   
   for(let i = offset; i < size; i++){
      sum += number;
   }
   return sum;
}
```

#### Closures

A closure is an anonymous function that captures the current scope and can be assigned to a variable. This variable can then act as a function and can be called in the same manner.

```js
const square = (x) -> x * x;
const cube = (x) -> square(x) * x;

cube(2); // result is 8

const printAll = (values...) -> {
   for(var e in values) {
      println(e);
   }
}

printAll(1, 2, 3, 4); // print all values
```

#### Function Handles

A function handle is simply a way to reference an existing function as a closure. Function handles can represent constructors or functions that are in scope.
For example take the constructor for a string, it is quite possible to execute the following.

```js
['a', 'b', 'c'].iterator.forEachRemaining(this::println)
```

Here we are calling the println function with the item passed to the function. This function is represented as a function handle
that takes a string. A function handle can represent a static or an instance function. For example:

```js
class Formatter {

    public static upper(s: String) {
        return s.toUpperCase();
    }
}

['a', 'b', 'c'].stream().map(Formatter::upper).forEach(this::println);
```

#### Generic Functions

Generics can be used to qualify the arguments that can be passed to a function. They are useful when the static analyser verifies the program as it ensures arguments and return types match the declared qualifiers.

```js
func abs<T: Number>(nums: T): List<T> {
    let result: List<T>  = [];
    
    for(num in nums) {
        let abs = num.abs();
        result.add(abs);
    }
    return result;
}

let list: List<Double> = abs<Double>(-1.0, 2.0, -3.0);

assert list[0] == 1;
assert list[2] == 2;
```

#### Coroutines

It is often useful to suspend execution of a function in order to return a result. Typically this requires a great deal of effort from the developer. Coroutines allow an idomatic means of suspending the 
execution of a function which can be resumed at the point of suspension. This allows for complex reactive iteration to be performed
with minimal effort. For example take a Fibonnaci sequence.

```js
func fib(n){
   let a = 1;
   let b = 2;
   
   until(n-- <= 0) {
      yield a;
      (a, b) = (b, a + b);
   }
}
```

#### Async Await

Asynchronous functions can be implemented with the async and await modifiers. This is similar to a standard Coroutine however this paradigm will allow the execution
of the program to fork in two different threads of execution.

```js
async loadImage(n: String): Promise<?> {
    return await ImageIO.read(n);
}
```

#### Blank Parameters

Blank parameters allow you to specify an argument that is not needed or can be ignored.

```js
func create<T>(type: T): T {
    return cache.computeIfAbsent(type.name, (_) -> new T());
}
```

### Types

In any substantial application types are required. A type is basically a way to define and encapsulate variables 
and functions within a named scope. All types can have generic parameters allowing the static analyser to verify
interactions with the type.

#### Class

A class is the most basic type. It contains variables and functions that can operate on those variables. 
Once declared a type can be instantiated by calling a special function called a constructor.

```js
class NumberCache<K, V: Number> {
    
    let map = new HashMap<?, V>();    

    public cache(k: K, v: V) {
        map.put(k, v);
    }
    
    public fetch(k: K): V {
        return map.get(k);
    }
} 

let cache = new NumberCache<String, Double>();

cache.cache('1', 1.0);
cache.cache('2', 2.0);
```

#### Enumeration

An enumeration is a type that specifies a list of constant values. This values are constant and are instances of the enum they are declared in.

```js
enum Color {
   RED("#ff0000"),
   BLUE("#0000ff"),
   GREEN("#00ff00");
   
   let rgb;
   
   new(rgb) {
      this.rgb = rgb;
   }
}

let red = Color.RED;
let blue = Color.BLUE;
```

#### Trait

A trait is similar to a class in that is specifies a list of functions. However, unlike a class a trait does not declare any variables and does not have a constructor. It can be used to add functions to a class.

```js
trait Format
   format(a);
}

class BoldFormat with Format{
   
   override format(a) {
      return "<b>${a}</b>";
   }
}

class ItalicFormat with Format {
   
   override format(a) {
      return "<i>${a}</i>";
   }
}
```

#### Module

A module is collection of types, functions, and variables. It is similar to enclosing a script within a named type. 
Modules are useful in providing constructs such as singletons.

```js
module ImageStore {

   private const cache = {:};
   
   public find(name) {
      return cache.get(name);
   }
   
   private cache(name, image) {
      cache.put(name, image);
   }
} 
```
#### Type Alias

It can often be useful to alias types for readability, particularly when generics are involved. An alias is not
a new type but rather a new name for a known type. 

```js
import util.concurrent.ConcurrentHashMap;

type Bag<T> = ConcurrentHashMap<String, T>();

func bagOf<T: Number>(nums...: T): Bag<T> {
    let bag: Bag<T> = new Bag<T>();
    
    for(num in nums){
        bag.put(`${num}`, num);
    }
    return bag;
}
```

#### Import

In order to access the Java types available they can be imported by name. Once imported the type can be instantiated 
and used as if it was a script object. In addition to importing types, functions can also be imported by using a 
static import.

```js
import static lang.Math.*; // import static functions
import security.SecureRandom;

const random = new SecureRandom(); // create a java type
const a = random.nextInt(40);
const b = random.nextInt(40);
const c = max(a, b); // Math.max(a, b)

println(c); // prints the maximum random
```

To avoid name collisions it is also possible to import types with aliases. Additionally an imports visibility can
be encapsulated within a module so that it is only available in that module. 

```js
import util.concurrent.ConcurrentHashMap as Bag;

module ImageStore {

    import aws.image.BufferedImage as Image;
    import aws.Graphics;
    
    public paint(g: Graphics) {
        // ...
    }
}
```

#### Coercion

For interfaces that have only a single method a closure can be coerced to that interface type. This makes for a much simpler and concise syntax similar to that offered by Java closures.

```js
const set = new TreeSet((a,b)->Double.compare(a,b));

set.add(1.2);
set.add(2.3);
set.add(33.4);
set.add(4.55);
set.add(2);

for(entry in set){
   println(entry);
}
```

#### Platform Integration

To leverage the large array of frameworks and services available on the Java platform any Java type 
can be instantiated, and any Java interface can be implemented.

```js
class DoubleComparator with Comparator{

   override compare(a,b){
      return Double.compare(a,b);
   }
}

let comparator = new DoubleComparator();
let set = new TreeSet(comparator);

set.add(1.2);
set.add(2.3);
set.add(33.4);
set.add(4.55);
set.add(2);

for(let entry in set){
   println(entry);
}
```

## Tools

To be productive in any language there needs to be a way to write, evalute and debug applications. The development 
environment is free to use and can be used in any standard web browser supporting HTML 5. Alternatively this 
development client can be run as a standalone application.

### Development Environment

The development environment, Snap Studio, is written with HTML5 and TypeScript. It comes packaged as a standalone application 
leveraging the Chrome embedded framework CEF. Running an application from Snap Studio is as simple has pressing the 
play button. This will initiate a bootstrapping process where the interpreter is downloaded in to a harness once
this boot strapping process has completed the source program is downloaded and executed. Stepping through the
code can be done by setting break points.

#### Breakpoints

A breakpoint forces the debugger to suspend at a particular line when execution flow arrives at that line. Once
suspended the developer can step in, out or over the statements.

![Developer Breakpoints](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_breakpoints.png)

#### Console

All output from the application is captured in the console and displayed. This console is a scrolling window and
will keep only the most recent history up to a configurable number of lines.

![Developer Console](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_console.png)

#### Variables

When execution is suspended it is possible to evaluate expressions and look at variables on the stack and in
the surrounding scope. These variables can be navigated by clicking through references.

![Developer Variables](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_variables.png)

#### Threads

At any time multiple threads may be suspended. A thread view is provided so that the developer can select the
thread to debug and also to view the stack frames.

![Developer Threads](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_threads.png)

#### Process View

If there are multiple applications running from the development environment focus can only be given to one. It
is possible to switch focus through the process view. Once focused an application can be debugged or terminated.

![Developer Debug](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_agents.png)

#### Debug Perspective

To capture as much relevant information on a single screen the debug perspectived can be used. This will allow
the developer to see the threads and variables as well as the console.

![Developer Debug Perspective](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_changelayout.png)

#### Full Screen

When editing it can be useful to see the full screen. This perspective can be achieved by double clicking on
the tab in focus.

![Developer Full Screen](https://raw.githubusercontent.com/snapscript/snap-site/master/images/debugger_fullscreen.png)

### Debug Agent

The development environment can act as a debug service. As such it is possible to connect to a debugger and 
push code and debug information. To do this you simple need to embed the debug agent in to your application.

### Android

Full compatibility is provided for Android. A basic JIT is also provided to reduce the overhead of reflection
and to allow types to be extended.


