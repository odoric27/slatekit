/**
 * <slate_header>
 * url: www.slatekit.com
 * git: www.github.com/code-helix/slatekit
 * org: www.codehelix.co
 * author: Kishore Reddy
 * copyright: 2016 CodeHelix Solutions Inc.
 * license: refer to website and/or github
 * about: A tool-kit, utility library and server-backend
 * mantra: Simplicity above all else
 * </slate_header>
 */

package slatekit.server.spark

import slatekit.apis.ApiContainer
import slatekit.apis.core.Api
import slatekit.apis.security.WebProtocol
import slatekit.apis.core.Auth
import slatekit.apis.core.Events
import slatekit.apis.doc.DocWeb
import slatekit.common.*
import slatekit.common.info.Info
import slatekit.common.metrics.Metrics
import slatekit.common.diagnostics.Tracker
import slatekit.core.common.AppContext
import slatekit.meta.Deserializer
import slatekit.results.Success
import slatekit.server.ServerConfig
import slatekit.server.common.Diagnostics
import spark.Request
import spark.Response
import spark.Spark
import spark.Spark.staticFiles
import java.io.File
import javax.servlet.MultipartConfigElement

class SparkServer(
        val config: ServerConfig,
        val ctx: Context,
        val auth: Auth?,
        val apis: List<Api>,
        val metrics: Metrics,
        val events: Events = Events()
)  {

    /**
     * initialize with port, prefix for api routes, and all the dependent items
     */
    constructor(
            ctx: Context = AppContext.simple("slatekit-server"),
            port: Int = 5000,
            prefix: String = "",
            info: Boolean = true,
            cors: Boolean = false,
            docs: Boolean = false,
            static: Boolean = false,
            staticDir: String = "",
            docKey: String = "",
            apis: List<Api>,
            auth: Auth? = null,
            setup: ((Any) -> Unit)? = null,
            metrics: Metrics,
            events: Events
    ) :
            this(ServerConfig(port, prefix, info, cors, docs, docKey, static, staticDir, setup), ctx, auth, apis, metrics, events)

    val container = ApiContainer(ctx,
            false,
            auth,
            WebProtocol,
            apis,
            deserializer = { req, enc -> Deserializer(req, enc) },
            docKey = config.docKey,
            docBuilder = ::DocWeb)


    val log = ctx.logs.getLogger("slatekit.server.api")
    val tracker = Tracker<slatekit.common.requests.Request, slatekit.common.requests.Response<*>, Exception>(Random.uuid(), ctx.app.name)
    val diagnostics = Diagnostics(metrics, log)
    val info = Info(ctx.app, ctx.build, ctx.start, ctx.sys)

    /**
     * executes the application
     * @return
     */
    fun run() {

        // Configure
        Spark.port(config.port)

        // Display startup
        if (config.info) {
            this.info()
        }

        // Static files
        if (config.static) {
            if (config.staticDir.isNullOrEmpty()) {
                staticFiles.location("/public")
            } else {
                staticFiles.externalLocation(File(config.staticDir).absolutePath)
            }
        }

        // Ping/Check
        Spark.get(config.prefix + "/ping", { req, res -> ping(req, res) })

        // CORS
        if (config.cors) Spark.options("/*") { req, res -> cors(req, res) }

        // Before
        Spark.before("*", { req, res ->
            req.attribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement((System.getProperty("java.io.tmpdir"))))
            // req.attribute("org.eclipse.multipartConfig", MultipartConfigElement((System.getProperty("java.io.tmpdir"))))
            if (config.cors) {
                res.header("Access-Control-Allow-Origin", "*")
                res.header("Access-Control-Request-Method", "*")
                res.header("Access-Control-Allow-Headers", "*")
            }
        })

        // Allow all the verbs/routes to hit exec method
        // The exec method will dispatch the request to
        // the corresponding SlateKit API.
        Spark.get(config.prefix + "/*", { req, res -> exec(req, res) })
        Spark.post(config.prefix + "/*", { req, res -> exec(req, res) })
        Spark.put(config.prefix + "/*", { req, res -> exec(req, res) })
        Spark.patch(config.prefix + "/*", { req, res -> exec(req, res) })
        Spark.delete(config.prefix + "/*", { req, res -> exec(req, res) })

        // Setup scrpt
        config.setup?.let { c -> c("") }
    }

    /**
     * stops the server ( this is not currently accessible on the command line )
     */
    fun stop() {
        spark.Spark.stop()
    }

    fun cors(req: Request, res: Response) {
        val accessControlRequestHeaders = req.headers("Access-Control-Request-Headers")
        if (accessControlRequestHeaders != null) {
            res.header("Access-Control-Allow-Headers", accessControlRequestHeaders)
        }

        val accessControlRequestMethod = req.headers("Access-Control-Request-Method")
        if (accessControlRequestMethod != null) {
            res.header("Access-Control-Allow-Methods", accessControlRequestMethod)
        }
    }

    /**
     * pings the server to only get back the datetime.
     * Used for quickly checking a deployment.
     */
    fun ping(req: Request, res: Response): String {
        val result = DateTime.now()
        val text = SparkResponse.json(res, Success(result).toResponse())
        return text
    }

    /**
     * handles the core logic of execute the http request.
     * This is actually accomplished by the SlateKit API Container
     * which handles abstracted Requests and dispatches them to
     * Slate Kit "Protocol Independent APIs".
     */
    fun exec(req: Request, res: Response): Any {

        // Convert the http request to a SlateKit Request
        val request = SparkRequest.build(ctx, req, config)

        // Execute the API call
        // The SlateKit ApiContainer will handle the heavy work of
        // 1. Checking routes to area/api/actions ( methods )
        // 2. Validating parameters to methods
        // 3. Decoding request to method parameters
        // 4. Executing the method
        // 5. Handling errors
        val result = container.call(request)

        // Record all diagnostics
        // e.g. logs, track, metrics, event
        diagnostics.record(container, request, result)

        // Finally convert the result back to a HttpResult
        val text = SparkResponse.result(res, result)
        return text
    }

    /**
     * prints the summary of the arguments
     */
    fun info() {
        println("===============================================================")
        println("STARTING : ")
        info.each({ name: String, value: String -> println(name + " = " + value) })
        println("===============================================================")
    }
}
