package model.file

import java.io.FileWriter
import java.nio.file.Path

class FileWorker() {

  fun writeToFile(path: Path, outputText: String) {
    val fileWriter = FileWriter(path.toFile(), true)

    fileWriter.use {
      it.write(outputText)
    }
  }
}