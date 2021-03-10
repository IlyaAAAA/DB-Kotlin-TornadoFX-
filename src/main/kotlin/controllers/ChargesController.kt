package controllers

import model.data.Charges
import model.db.DataBaseConnection
import model.db.DataBaseConnection.charges
import model.db.DataBaseConnection.expenseItems
import org.ktorm.dsl.eq
import org.ktorm.dsl.insert
import org.ktorm.entity.find
import tornadofx.Controller
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ChargesController : Controller() {

  private val database = DataBaseConnection.database

  fun addCharge(amount: Int, chargeDate: LocalDate, itemName: String) {

    println(itemName)

    val expenseItem = database.expenseItems.find { it.itemName eq itemName } ?: return

    println(itemName)

    println(expenseItem)

    database.insert(Charges) {
      set(it.amount, amount)
      set(it.chargeDate, Timestamp.valueOf(LocalDateTime.of(chargeDate, LocalTime.now())))
      set(it.expenseItem, expenseItem.id)
    }
  }

  fun editCharge(id: Int, amount: Int, chargeDate: LocalDate, itemName: String) {

    val charge = database.charges.find { it.id eq id } ?: return

    val timestampDate = Timestamp.valueOf(LocalDateTime.of(chargeDate, LocalTime.now()))

    charge.amount = amount
    charge.chargeDate = timestampDate
    charge.expenseItem = database.expenseItems.find { it.itemName eq itemName } ?: return
    charge.flushChanges()
  }
}