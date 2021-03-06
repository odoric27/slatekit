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

package slatekit.core.email

import slatekit.common.*
import slatekit.common.info.ApiLogin
import slatekit.common.templates.Templates
import slatekit.results.Failure
import slatekit.results.Notice
import slatekit.results.Success

class EmailServiceSendGrid(
    user: String,
    key: String,
    phone: String,
    templates: Templates? = null
)
    : EmailService(templates) {

    val _settings = EmailSettings(user, key, phone)
    private val _baseUrl = "https://api.sendgrid.com/api/mail.send.json"

    /**
     * Initialize with api credentials
     * @param apiKey
     */
    constructor(apiKey: ApiLogin, templates: Templates? = null) :
            this(apiKey.key, apiKey.pass, apiKey.account, templates)

    override fun send(msg: EmailMessage): Notice<Boolean> {

        // Parameters
        val bodyArg = if (msg.html) "html" else "text"

        val result = HttpRPC().sendSync(
                method = HttpRPC.Method.Post,
                url = _baseUrl,
                headers = null,
                creds = HttpRPC.Auth.Basic(_settings.user, _settings.key),
                body = HttpRPC.Body.FormData(listOf(
                        Pair("api_user", _settings.user),
                        Pair("api_key", _settings.key),
                        Pair("to", msg.to),
                        Pair("from", _settings.account),
                        Pair("subject", msg.subject),
                        Pair(bodyArg, msg.body)
                )
        ))
        return result.fold( { Success(true) }, { Failure(it.message ?: "") })
    }
}
