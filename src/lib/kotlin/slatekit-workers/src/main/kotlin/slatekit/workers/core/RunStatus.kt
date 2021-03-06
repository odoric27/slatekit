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

package slatekit.workers.core

import slatekit.common.DateTime
import slatekit.common.Status

data class RunStatus(
    val id: String = "",
    val name: String = "",
    val lastRunTime: DateTime = DateTime.now(),
    val status: Status = Status.InActive
)
