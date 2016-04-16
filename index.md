---
layout: cayman_page
title: Slate Kit
permalink: /index
---
# Slate
---
Slate-Source is a simple, and very useful micro-framework and utility library built in Scala. It provides the technical foundation to quickly build Scala apps. Save time and effort on technical infrastructure so you can focus on building the business features of your product or start-up. Slate is built on the concept of using modular, re-usable code and components to get your apps developed, and rolled out to users as quickly as possible.

[http://github.com/kishorereddy/scala-slate](http://github.com/kishorereddy/scala-slate)

## Approach
Slate solves some very common problems in the technology area:


## Goals
Slate solves some very common problems in the technology area:


|:--|:--|
|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/window.png) | Rapidly create scala apps and prototypes |
|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/business-bag.png) | Focus on business features, not technology needs |
|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/cloud-upload.png) | Support for cloud services ( Amazon S3, SQS ) |
|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/chat.png) | Build a community for further improvement and feedback |
|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/dollar.png) | Reduce costs of development |


<!--|![RAD](https://raw.githubusercontent.com/kishorereddy/scala-slate/gh-pages/images/app/file-text.png) | Well documented apis and components |-->


---

# Library
The Slate Source core library offers several modules in the core library. These modules represent re-usable code and components that can be used in any type of application.

## All
This is a listing all the components available in the Slate Source core library.

|:--|:--|
| **[Args](mod-args.html)** 		| A lexical arguments/options parser for command line parsing and more | 
| **[Auth](mod-auth.html)** 		| Simple authorization component for both desktop and concurrency based apps | 
| **[Api](mod-api.html)**			| A mini api container to easily built and host protocol independent apis that can be used in the web or command line |
| **[App](mod-app.html)**			| A robust application template with built-in support for command line args, environments, logging, configs and more | 
| **[Cmd](mod-cmd.html)**	    | A robust implementation of the command pattern | 
| **[Csv](mod-csv.html)**			| A csv lexical parser for reading any type of csv files | 
| **[Data](mod-data.html)**		| A collection of database related functions | 
| **[Entities](mod-entities.html)** | A micro-framework and ORM for managing your data models |  
| **[Files](mod-aws-s3.html)** 		| A light-weight abstraction over file storage with support for Amazon S3 | 
| **[Lex](mod-lex.html)** 			| A lexical parser that can be extended | 
| **[Logger](mod-logger.html)** 	| A simple logger that can be extended with 3rd party logging software |
| **[Model](mod-model.html)** 		| A component to easily build up schema for some data model |  
| **[Mapper](mod-mapper.html)** 	| Maps data from 1 source to another source |  
| **[Query](mod-query.html)** 		| Construct your own query object model for handling queries | 
| **[Queue](mod-aws-sqs.html)** 	| A light-weight abstraction over messaging with support Amazon SQS | 
| **[Reflect](mod-reflect.html)** 	| Convenience wrapper around reflection in Scala |  
| **[Shell](mod-shell.html)**		| A command line shell for your services |
| **[Tasks](mod-tasks.html)**		| A component to easily build batch, background or continuously running jobs |
| **[Utils](mod-utils.html)**		| More than a dozen plus utilities and more |


## Example

|:--|:--|
| **desc** | A lexical arguments/options parser for command line parsing and more | 
| **date**| 2016-3-25 10:56:53 |
| **version** | 0.9.1  |
| **namespace** | slate.common.args  |
| **core source** | slate.common.args.Args  |
| **example** | [Example_Args](https://github.com/kishorereddy/scala-slate/blob/master/src/apps/scala/slate-examples/src/main/scala/slate/examples/Example_Args.scala) |

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


# Support
Feel free to contact us for more info. Check out our [documentation](https://help.github.com/pages) or [contact support](https://github.com/contact) and we’ll help you sort it out. Please feel free to post git issues, fork the code and submit patches. For any further and dedicated support, please contact us for details.
There is a lot of documentation available for each component. The docs are available in the form of boths detailed docs and examples.

## Docs
Check out the wiki for information on releases, downloads, components available, links, support, examples and much more.
There is a dedicated area that contains several detailed examples of using the components in the apps\examples folder. You can check out the wiki and source code here for more info.

## Authors
Kishore Reddy ( @kishorereddy ) is the author of Slate-Source. Feel free to contact Kishore for any questions. @kishorereddy is an software architecht, artist and open-source developer living and working in the New York City Area. For the last 10+ years, Kishore has focused on various technologies ranging from Java, C#, Python, Sql-Server, MySql, AWS, Azure, DSL ( Domain Specific Languages ) and Language Implementation. For the last 2-3 years, Kishore has focused mostly on Scala, working in server side, and mobile areas.



# Modules
Slate offers several modules in the core library. These modules represent re-usable code and components that can be used in any type of application.

## Api
This is a listing all the components available in the Slate Source core library.

## App
This is a listing all the components available in the Slate Source core library.

## Batch
This is a listing all the components available in the Slate Source core library.

## Cloud
This is a listing all the components available in the Slate Source core library.

## ORM
This is a listing all the components available in the Slate Source core library.

## Job
This is a listing all the components available in the Slate Source core library.

## Shell
This is a listing all the components available in the Slate Source core library.

## Server
This is a listing all the components available in the Slate Source core library.

## UI
This is a listing all the components available in the Slate Source core library.
