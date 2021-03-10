package controllers

import model.db.DataBaseConnection
import model.file.FileWorker
import tornadofx.Controller
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MonthlyIncomeController : Controller() {

  private val jdbcConnection = DataBaseConnection.jdbcConnection
  private val fileWorker = FileWorker()

  fun getTheMonthlyIncome(date: LocalDate): Int {
    val command = "{call getIncomeForMonth(?, ?)}"

    val callStat = jdbcConnection.prepareCall(command)


    callStat.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(date, LocalTime.now())))
    callStat.registerOutParameter(2, Types.INTEGER)

    callStat.execute()

    val income = callStat.getInt(2)

    println(income)

    return income
  }

  fun writeToFile(date: LocalDate, income: String) {
    val textToFile = "Income for ${date.month} ${date.year} is $income\n"

    val path = Paths.get("monthlyIncome.txt")

    fileWorker.writeToFile(path, textToFile)
  }
}