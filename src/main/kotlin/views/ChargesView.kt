package views

import controllers.ChargesController
import controllers.MainController
import javafx.geometry.Orientation
import javafx.scene.control.ComboBox
import javafx.scene.control.DatePicker
import javafx.scene.control.TextField
import model.entity.Charge
import model.entity.ExpenseItem
import tornadofx.*
import java.time.LocalDate

class ChargesView(title: String, private val buttonText: String, private val op: () -> Unit) : Fragment(title) {

  constructor(title: String, buttonText: String, charge: Charge, expenseItem: ExpenseItem, op: () -> Unit) : this(
    title,
    buttonText,
    op
  ) {
    amountField.text = charge.amount.toString()
    chargeDateField.value = charge.chargeDate.toLocalDateTime().toLocalDate()
    expenseItemComboBox.value = mainController.getExpenseItemNameById(expenseItem.id)
    this.charge = charge
  }

  private lateinit var charge: Charge

  private val mainController: MainController by inject()
  private val chargesController: ChargesController by inject()
  private val expenseItemNameList = mainController.getExpenseItemsName()

  private var amountField: TextField by singleAssign()
  private var chargeDateField: DatePicker by singleAssign()
  private var expenseItemComboBox: ComboBox<String> by singleAssign()

  override val root = form {
    fieldset(labelPosition = Orientation.VERTICAL) {
      field("Amount") {
        textfield {
          amountField = this
        }
      }

      field("Charge date") {
        datepicker {
          chargeDateField = this
          value = LocalDate.now()
        }
      }

      field("Expense item") {
        combobox(values = expenseItemNameList) {
          expenseItemComboBox = this
        }
      }

      buttonbar {
        button(buttonText) {
          action {
            try {
              when (buttonText) {
                "Add" -> addAction(Integer.parseInt(amountField.text), chargeDateField.value, expenseItemComboBox.value)
                "Edit" -> editAction(
                  Integer.parseInt(amountField.text),
                  chargeDateField.value,
                  expenseItemComboBox.value
                )
              }
              op.invoke()
              close()
            } catch (e: NumberFormatException) {
              ErrorView("Amount must be a number").openModal(resizable = false)
            }
          }
        }
      }
    }
  }

  private fun addAction(amount: Int, chargeDate: LocalDate, itemName: String) {
    try {
      chargesController.addCharge(amount, chargeDate, itemName)

    } catch (e: java.sql.SQLException) {
      ErrorView("You can't add charge with the amount greater than 1000").openModal(resizable = false)
    }
  }

  private fun editAction(amount: Int, chargeDate: LocalDate, itemName: String) {
    chargesController.editCharge(charge.id, amount, chargeDate, itemName)
  }
}
