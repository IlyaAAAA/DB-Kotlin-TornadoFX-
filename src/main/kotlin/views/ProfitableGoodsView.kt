package views

import controllers.ProfitableGoodController
import javafx.scene.control.DatePicker
import javafx.scene.control.TableView
import model.data.ProfitableGoods
import model.exceptions.IncorrectDateInput
import tornadofx.*
import java.time.LocalDate

class ProfitableGoodsView : Fragment("Five the profitable goods") {

  private val profitableGoodController: ProfitableGoodController by inject()

  private var profitableGoodsList = mutableListOf<ProfitableGoods>().asObservable()
  private var firstDate: DatePicker by singleAssign()
  private var secondDate: DatePicker by singleAssign()
  private var goodsTableView: TableView<ProfitableGoods> by singleAssign()


  override val root = vbox {

    tableview(profitableGoodsList) {
      goodsTableView = this

      readonlyColumn("Name", ProfitableGoods::name)
      readonlyColumn("Amount", ProfitableGoods::amount)

      smartResize()
    }

    hbox {
      form {
        fieldset {
          field("First date") {
            datepicker {
              firstDate = this
              value = LocalDate.now().minusMonths(1)
            }
          }

          field("Second date") {
            datepicker {
              secondDate = this
              value = LocalDate.now()
            }
          }
        }
      }
    }


    vbox {

      button("Get goods") {
        vboxConstraints {
          marginLeft = 120.0
        }
        action {
          try {
            profitableGoodsList.setAll(
              profitableGoodController.getTheMostProfitableGoods(
                firstDate?.value ?: return@action,
                secondDate?.value ?: return@action
              ).asObservable()
            )
          } catch (e: IncorrectDateInput) {
            ErrorView("First date must be earlier than the second one").openModal(resizable = false)
          }
        }
      }

      button("Save to file") {
        vboxConstraints {
          marginLeft = 120.0
          marginTop = 10.0
        }

        action {
          if (profitableGoodsList.size > 0) {
            profitableGoodController.writeToFile(firstDate.value, secondDate.value, profitableGoodsList)
          }
        }
      }
    }
  }
}
