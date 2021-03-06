/**
<slate_header>
author: Kishore Reddy
url: https://github.com/kishorereddy/scala-slate
copyright: 2015 Kishore Reddy
license: https://github.com/kishorereddy/scala-slate/blob/master/LICENSE.md
desc: a scala micro-framework
usage: Please refer to license on github for more info.
</slate_header>
 */

package slatekit.db.types

import slatekit.common.Types
import slatekit.db.DbUtils.ensureField
import slatekit.common.newline
import slatekit.db.DbFieldType
import java.rmi.UnexpectedException

/**
 * Builds up database tables, indexes and other database components
 */
open class DbSourcePostGres : DbSource {

    val types = listOf(
            DbTypeInfo(DbFieldType.DbString, "NVARCHAR", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbBool, "BIT", Types.JBoolClass),
            DbTypeInfo(DbFieldType.DbShort, "TINYINT", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbNumber, "INTEGER", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbLong, "BIGINT", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbFloat, "FLOAT", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbDouble, "DOUBLE", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbDecimal, "DECIMAL", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbLocalDate, "DATE", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbLocalTime, "TIME", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbLocalDateTime, "DATETIME", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbZonedDateTime, "DATETIME", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbInstant, "INSTANT", Types.JStringClass),
            DbTypeInfo(DbFieldType.DbDateTime, "DATETIME", Types.JStringClass)
    )

    /**
     * Mapping of normalized types ot postgres type names
     */
    val dataToColumnTypes = types.map { Pair(it.metaType, it.dbType) }.toMap()
    val langToDataTypes = types.map { Pair(it.langType, it.metaType) }.toMap()

    /**
     * Builds the drop table DDL for the name supplied.
     */
    override fun buildDropTable(name: String): String = build(name,"DROP TABLE IF EXISTS")

    /**
     * Builds a delete statement to delete all rows
     */
    override fun buildDeleteAll(name: String): String = build(name,"DELETE FROM")

    /**
     * Builds an add column DDL sql statement
     */
    override fun buildAddCol(name: String, dataType: DbFieldType, required: Boolean, maxLen: Int): String {
        val nullText = if (required) "NOT NULL" else ""
        val colType = buildColType(dataType, maxLen)
        val colName = buildColName(name)

        val sql = " $newline$colName $colType $nullText"
        return sql
    }

    /**
     * Builds a valid column name
     */
    override fun buildColName(name: String): String = "`" + ensureField(name) + "`"

    /**
     * Builds a valid column type
     */
    override fun buildColType(colType: DbFieldType, maxLen: Int): String {
        return if (colType == DbFieldType.DbString && maxLen == -1)
            "longtext"
        else if (colType == DbFieldType.DbString)
            "VARCHAR($maxLen)"
        else
            getColTypeName(colType)
    }


    private fun build(name:String, prefix:String): String {
        val tableName = ensureField(name)
        val sql = "$prefix `$tableName`;"
        return sql
    }

    private fun getColTypeName(sqlType: DbFieldType): String {
        return if(dataToColumnTypes.containsKey(sqlType))
            dataToColumnTypes[sqlType] ?: ""
        else
            throw UnexpectedException("Unexpected db type : $sqlType")
    }
}
