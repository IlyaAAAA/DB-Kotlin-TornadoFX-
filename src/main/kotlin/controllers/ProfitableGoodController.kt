package controllers

import model.data.ProfitableGoods
import model.db.DataBaseConnection
import model.exceptions.IncorrectDateInput
import model.file.FileWorker
import tornadofx.Controller
import java.nio.file.Paths
import java.sql.ResultSet
import java.sql.Timestamp
import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ProfitableGoodController : Controller() {

  private val jdbcConnection = DataBaseConnection.jdbcConnection
  private val fileWorker = FileWorker()

  fun getTheMostProfitableGoods(firstDate: LocalDate, secondDate: LocalDate): List<ProfitableGoods> {

    if (firstDate.isAfter(secondDate)) {
      throw IncorrectDateInput("First date must be before earlier than the second one")
    }

    val command = "{call getTheMostProfitableGoods(?, ?, ?)}"

    val callStat = jdbcConnection.prepareCall(command)


    callStat.setTimestamp(1, Timestamp.valueOf(LocalDateTime.of(firstDate, LocalTime.now())))
    callStat.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(secondDate, LocalTime.now())))
    callStat.registerOutParameter(3, Types.REF_CURSOR)


    callStat.execute()

    val rs: ResultSet = callStat.getObject(3) as ResultSet

    val profitableGoodsList = mutableListOf<ProfitableGoods>()

    while (rs.next()) {
      profitableGoodsList.add(ProfitableGoods(rs.getString(1), rs.getFloat(2)))
    }

    profitableGoodsList.forEach {
      println("${it.name} ${it.amount}")
    }
    return profitableGoodsList
  }

  fun writeToFile(startDate: LocalDate, endDate: LocalDate, profitableGoods: List<ProfitableGoods>) {
    val textForFile: StringBuilder = java.lang.StringBuilder()

    textForFile.append("The most profitable goods between ${startDate.month} ${startDate.year} and ${endDate.month} ${endDate.year}:\n")

    profitableGoods.forEach {
      textForFile.append("${it.name} ${it.amount}\n")
    }

    textForFile.append("---------------------------------------------\n")

    val path = Paths.get("profitableGoods.txt")

    fileWorker.writeToFile(path, textForFile.toString())
  }

}