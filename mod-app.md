---
layout: cayman_page
title: module App
permalink: /mod-app
---

# App

|:--|:--|
| **desc** | A base application with support for logging, config, status and more | 
| **date**| 2016-4-16 0:24:10 |
| **version** | 0.9.1  |
| **namespace** | slate.core.app  |
| **source** | slate.core.app.AppProcess  |
| **example** | [Example_App](https://github.com/code-helix/slatekit/blob/master/src/apps/scala/slate-examples/src/main/scala/slate/examples/Example_App.scala) |

## Import
```scala 
// required 
import slate.core.app.{AppProcess, AppRunner}


// optional 
import slate.common.{Env, OperationResult}
import slate.core.cmds.Cmd
import scala.collection.mutable.ListBuffer


```

## Setup
```scala


  class SampleApp extends AppProcess
  {

    /**
     * initialize app and metadata
     */
    override def init(): Unit =
    {
      // Initialize info about this app
      meta.name = "sampleapp"
      meta.desc = "sample app to show the appprocess base class, template methods, and functionality"
      meta.region = "usa.ny"
      meta.env = Env.DEV
      meta.version = "1.0.0.3"
      meta.log = "{@app}-{@env}-{@date}.log"

      // Set some options

      // 1. Prints a summary of the all the metadata and
      // stats( time, duration, errors, etc ) during shutdown
      options.printSummaryBeforeExec = false
      options.printSummaryOnShutdown = true

      // 2.Renames config files e.g. meta.config = "{@env}.config" = "dev.config"
      options.renameConfig = true

      // 3. Renames the log file e.g. meta.log = "{@app}-{@env}.log" = "sample-app-qa.log"
      options.renameLog = true
    }


    /**
     * accepts the arguments from command line or other source
     * @param args
     */
    override def onAccept(args:Option[Array[String]]): Unit =
    {
      if(args.isEmpty)
        return

      meta.env = args.get(0)
      meta.region = args.get(1)

      log.info("app configured with env(dev,qa,uat,prod) and region(usa.ny) etc... ")
    }


    /**
     * executes the app
     * @return
     */
    override def onExecute():OperationResult =
    {
      log.info("app executing now")

      // simulate work
      Thread.sleep(1000)

      log.info("app completed")

      OperationResult(true, "", 0, null)
    }


    /**
     * called when app is done
     */
    override def onShutdown(): Unit =
    {
      log.info("app shutting down")
    }


    override def collectSummaryExtra(args:ListBuffer[String]): Unit =
    {
      args += (meta.name + ": extra 1  = extra summary data1")
      args += (meta.name + ": extra 2  = extra summary data2")
    }
  }
  

```

## Usage
```scala


    AppRunner.run(new SampleApp(), Some(Array("qa", "usa.ny")))
    

```


## Output

```java
  Info : app executing now
  Info : app completed
  Info : app shutting down
  Info : ===============================================================
  Info : SUMMARY :
  Info : ===============================================================
  Info : name          = sampleapp
  Info : desc          = sample app to show the appprocess base class, template methods, and functionality
  Info : version       = 1.0.0.3
  Info : tags          = feature_01
  Info : args          = Some([Ljava.lang.String;@9304022)
  Info : env           = qa
  Info : config        = qa.config
  Info : log           = sampleapp-qa-2016-4-13-3-1-14.log
  Info : region        = usa.ny
  Info : started       = 2016-4-13 3:1:14
  Info : ended         = 2016-4-13 3:1:15
  Info : duration      = slate.common.TimeSpan@3ec27ce4
  Info : status        = ended
  Info : errors        = 0
  Info : error         = n/a
  Info : host.name     = KREDDY
  Info : host.ip       = Windows 7
  Info : host.origin   = local
  Info : host.version  = 6.1
  Info : lang.name     = scala
  Info : lang.version  = 2.11.7
  Info : lang.java     = local
  Info : lang.home     = C:/Tools/Java/jdk1.7.0_79/jre
  Info : sampleapp: extra 1  = extra summary data1
  Info : sampleapp: extra 2  = extra summary data2
  Info : ===============================================================
```
  