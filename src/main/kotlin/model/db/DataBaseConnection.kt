package model.db

import model.data.Charges
import model.data.ExpenseItems
import model.data.Sales
import model.data.Warehouses
import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import java.sql.Connection
import java.sql.DriverManager


object DataBaseConnection {
  private const val username = "c##ilya"
  private const val password = "MyPass"

  val jdbcConnection: Connection = DriverManager.getConnection("jdbc:oracle:thin:$username/$password@localhost:1521:xe")
  val database = Database.connect(
    url = "jdbc:oracle:thin:@localhost:1521",
    driver = "oracle.jdbc.driver.OracleDriver",
    user = "c##ilya",
    password = "MyPass"
  )

  val Database.warehouses get() = this.sequenceOf(Warehouses)
  val Database.sales get() = this.sequenceOf(Sales)
  val Database.expenseItems get() = this.sequenceOf(ExpenseItems)
  val Database.charges get() = this.sequenceOf(Charges)



//  fun closeConnection() = connection.close()
}