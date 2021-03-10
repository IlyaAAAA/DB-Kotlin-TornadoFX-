package views

import controllers.MonthlyIncomeController
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import tornadofx.*

class MonthlyIncomeVIew : Fragment("My View") {

  private val monthlyIncomeController: MonthlyIncomeController by inject()
  private var date: DatePicker by singleAssign()
  private var incomeField: TextField by singleAssign()

  override val root = form {
    fieldset {
      field("Choose month") {
        datepicker {
          date = this
        }
      }

      field("Income of this month") {
        textfield {
          incomeField = this
          isEditable = false
        }
      }

      vbox {
        button("Get income") {
          action {
            incomeField.text = monthlyIncomeController.getTheMonthlyIncome(date?.value ?: return@action).toString()
          }
        }

        button("Save to file") {
          vboxConstraints {
            marginTop = 10.0
          }
          action {
            if (incomeField.length > 0) {
              monthlyIncomeController.writeToFile(date.value, incomeField.text)
            }
          }
        }
      }
    }
  }
}

