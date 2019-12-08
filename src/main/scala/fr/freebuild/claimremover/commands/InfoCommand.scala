package fr.freebuild.claimremover.commands

import fr.freebuild.claimremover.ClaimRemoverPlugin
import fr.freebuild.claimremover.ClaimRemoverPlugin.configs
import fr.freebuild.claimremover.RegionsAnalysis
import fr.freebuild.claimremover.utils.PlayerUtils
import org.bukkit.command.CommandSender

import scala.collection.mutable
import scala.jdk.CollectionConverters._

object InfoCommand extends Command {
  val separator = "&3==================&7"

  override def execute(sender: CommandSender, args: Seq[String]): Boolean = {
    if (ClaimRemoverPlugin.analysis == null)
      PlayerUtils.sendMessage(sender, configs.language.errorMessages.noAnalyze)
    else
      PlayerUtils.sendMessage(sender, getDisplayerAnalysis(ClaimRemoverPlugin.analysis))
    true
  }

  /**
   * Display analysis information to player
   */
  private def getDisplayerAnalysis(analysis: RegionsAnalysis): String = {
    val sb = new StringBuilder(s"${separator}\n")
    val infoDisplay = configs.language.infoDisplay

    val playersNumber = analysis.regions.foldLeft(new mutable.HashSet[(String, String)]())((acc, region) => {
      acc.addAll(region.getLeaders.asScala.map(player => (player.getPlayerName, player.getUUID)))
      acc
    }).size

    sb ++= infoDisplay.analysisDate.format(analysis.name)
    sb ++= infoDisplay.playersNumber.format(playersNumber)
    sb ++= infoDisplay.claimsNumber.format(analysis.regions.size)
    sb ++= infoDisplay.worldDetails
    analysis.regions.foldLeft(Map[String, Int]()){
      (acc, region) => acc + (region.getWorld -> (acc.getOrElse(region.getWorld, 0) + 1))
    }.foreach {
      case (world, quantity) => sb ++= s"- &9${world} : &7${quantity}\n"
    }
    sb ++= separator
    sb.toString
  }

}
