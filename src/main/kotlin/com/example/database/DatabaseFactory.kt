package com.example.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
        transaction {
            SchemaUtils.create(UserTable)
            SchemaUtils.statementsRequiredToActualizeScheme(UserTable)
            SchemaUtils.createMissingTablesAndColumns(UserTable)
//            //Create a default usser
//            UserTable.insert {
//                it[name] = "Ishroid"
//                it[email] = "your-email@here.com"
//                it[city] = "Delhi"
//            }
            //SchemaUtils.create(IssueTable)

            SchemaUtils.create(ChatTable)
            SchemaUtils.statementsRequiredToActualizeScheme(ChatTable)
            SchemaUtils.createMissingTablesAndColumns(ChatTable)

            SchemaUtils.create(ChatMemberTable)
            SchemaUtils.statementsRequiredToActualizeScheme(ChatMemberTable)
            SchemaUtils.createMissingTablesAndColumns(ChatMemberTable)

            SchemaUtils.create(MessageTable)
            SchemaUtils.statementsRequiredToActualizeScheme(MessageTable)
            SchemaUtils.createMissingTablesAndColumns(MessageTable)
        }
    }

    /**
     * Look at hikari.properties and change accordingly
     * */
    private fun hikari(): HikariDataSource {
        val config = HikariConfig("/hikari.properties")
        return HikariDataSource(config)
    }
}