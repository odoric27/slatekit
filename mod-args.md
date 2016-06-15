---
layout: start_page
title: module Args
permalink: /mod-args
---

# Args

|:--|:--|
| **desc** | A lexical arguments/options parser for command line parsing and more | 
| **date**| 2016-4-16 0:24:10 |
| **version** | 0.9.1  |
| **namespace** | slate.common.args  |
| **source** | slate.common.args.Args  |
| **example** | [Example_Args](https://github.com/code-helix/slatekit/blob/master/src/apps/scala/slate-examples/src/main/scala/slate/examples/Example_Args.scala) |

## Import
```scala 
// required 
import slate.common.args.Args


// optional 
import slate.common.OperationResult
import slate.core.cmds.Cmd


```

## Setup
```scala

n/a

```

## Usage
```scala


    // Use case 1: Defaults e..g prefix = "-", sep = ":"
    showResults( Args.parse( "-env:dev -text:'hello word' -batch:10" ) )

    // Use case 2: Custom prefix and sep e.g. "!" and separator "="
    showResults( Args.parse( "!env=dev !text='hello word' !batch=10 ", prefix = "!", sep = "=" ) )

    // Use case 3a: Check for method call in the beginning
    showResults( Args.parse( "service.method -env=dev -text='hello word' -batch=10", prefix = "-",
                 sep = "=", hasAction = true ) )

    // Use case 3b: Check for method call in the beginning
    showResults( Args.parse( "method -env=dev -text='hello word' -batch=10", prefix = "-",
                 sep = "=", hasAction = true ) )

    // Use case 4: No args
    showResults( Args.parse( "service.method", prefix = "-", sep = "=", hasAction = true ) )

    // Use case 5a: Help request ( "?", "help")
    showResults( Args.parse( "?"         ) )

    // Use case 5b: Help request with method call ( "method.action" ? )
    showResults( Args.parse( "service.method help"   , hasAction = true ) )

    // Use case 6: Version request ( "ver", "version" )
    showResults( Args.parse( "version"  ) )

    // Use case 7: Exit request
    showResults( Args.parse( "exit"     ) )

    // Use case 8: apply args to an object
    Args.apply( "-env:dev -text:'hello word' -batch:10", new SampleOptions(), "-", ":", true)
    

```

