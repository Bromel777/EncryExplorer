package org.encryfoundation.explorer.db.dao

import org.encryfoundation.explorer.db.models.Input

object InputsDao extends Dao[Input] {

  import doobie._
  import doobie.implicits._
  import org.encryfoundation.explorer.db.tables.InputsTable._

  val fields: String = fields.mkString(", ")

  def getById(id: String): ConnectionIO[Input] = perform(selectById(id), s"Cannot find output with id = $id")

  def findByTxId(ch: String): ConnectionIO[List[Input]] = selectByTxId(ch).to[List]

  private def selectById(id: String): Query0[Input] =
    sql"SELECT $fields FROM $name WHERE id = $id;".query[Input]

  private def selectByTxId(ch: String): Query0[Input] =
    sql"SELECT $fields FROM $name WHERE tx_id = $ch;".query[Input]
}
