package fr.freebuild.claimremover.csv

import java.util.Date

import better.files.File
import com.github.tototoshi.csv.CSVWriter
import fr.freebuild.claimremover.RegionsAnalysis
import fr.freebuild.claimremover.csv.CSVRegionsExporter.header
import fr.freebuild.claimremover.utils.FileUtils
import org.bukkit.OfflinePlayer

trait CSVExporter[T] {
  val fileName: String
  private[csv] val header: List[String]

  def exportCSV(file: File, analysis: RegionsAnalysis)
  def importCSV(file: File): T

  def loadFile(analysisName: String): File = FileUtils.loadFile(FileUtils.buildPath(fileName, analysisName), create = true)
  def initWriter(file: File): CSVWriter = {
    val writer =  CSVWriter.open(file.toJava)
    writer.writeRow(header)
    writer
  }
}