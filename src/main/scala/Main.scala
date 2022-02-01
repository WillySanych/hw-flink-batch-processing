import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

case class vgSales(Rank: Int,
                   Name: String,
                   Platform: String,
                   Year: String,
                   Genre: String,
                   Publisher: String,
                   NA_Sales: Double,
                   EU_Sales: Double,
                   JP_Sales: Double,
                   Other_Sales: Double)

object Main extends App{

  val env = ExecutionEnvironment.getExecutionEnvironment

  val data = "in/vgsales.csv"
  val csvGenresIncome = "out/GenresIncome.csv"
  val csvPlatformIncome = "out/PlatformIncome.csv"

  val ds: DataSet[vgSales] = env.readCsvFile[vgSales](
    filePath = data,
    lineDelimiter = "\n",
    fieldDelimiter = "|",
    ignoreFirstLine = true)

  val genresIncome = ds
    .map(x => (x.Genre, x.NA_Sales + x.EU_Sales + x.JP_Sales + x.Other_Sales))
    .groupBy(0)
    .sum(1)

  val platformIncome = ds
    .map(x => (x.Platform, x.NA_Sales + x.EU_Sales + x.JP_Sales + x.Other_Sales))
    .groupBy(0)
    .sum(1)

  genresIncome
    .writeAsCsv(
      filePath = csvGenresIncome,
      fieldDelimiter = "|",
      rowDelimiter = "\n",
      writeMode = WriteMode.OVERWRITE
    )
    .setParallelism(1)

  platformIncome
    .writeAsCsv(
      filePath = csvPlatformIncome,
      fieldDelimiter = "|",
      rowDelimiter = "\n",
      writeMode = WriteMode.OVERWRITE
    )
    .setParallelism(1)

  genresIncome.print()
  platformIncome.print()

}
