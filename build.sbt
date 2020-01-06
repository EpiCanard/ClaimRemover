val Name = "ClaimRemover"
val Version = "0.0.1"
val GroupId = "fr.freebuild"

name := Name
version := Version
organization := GroupId

scalaVersion := "2.13.0"
scalacOptions += "-language:implicitConversions"

packageOptions in (Compile, packageBin) +=
    Package.ManifestAttributes("Automatic-Module-Name" -> (GroupId + "." + Name.toLowerCase))

/* Resolver */
resolvers += Resolver.mavenCentral

resolvers += "jitpack" at "https://jitpack.io"
resolvers += "spigot-repo" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
resolvers += "redprotect-repo" at "https://raw.githubusercontent.com/FabioZumbi12/RedProtect/mvn-repo/"
resolvers += "keyle-repo" at "https://nexus.keyle.de/content/groups/public/"
resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq(
    /* General dependencies */
    "com.github.pathikrit" %% "better-files" % "3.8.0",
    "com.github.tototoshi" %% "scala-csv" % "1.3.6",
    "io.circe" %% "circe-core" % "0.12.0-RC4",
    "io.circe" %% "circe-generic" % "0.12.0-RC4",
    "io.circe" %% "circe-parser" % "0.12.0-RC4",
    "io.circe" %% "circe-yaml" % "0.11.0-M1",
    "org.bukkit" % "bukkit" % "1.14.4-R0.1-SNAPSHOT" % "provided",
    "com.github.Jannyboy11.ScalaPluginLoader" % "ScalaLoader" % "0.12.2" % "provided",
    /* "com.github.Jannyboy11.ScalaPluginLoader" % "ScalaLoader" % "0.12.0-SNAPSHOT" % "provided", */

    /* Minecraft plugin dependencies */
    "br.net.fabiozumbi12.RedProtect" % "RedProtect-Core" % "7.6.2" % "provided" intransitive(),
    "br.net.fabiozumbi12.RedProtect" % "RedProtect-Spigot" % "7.6.2" % "provided" intransitive(),
    "ru.tehkode" % "PermissionsEx" % "1.23.4" % "provided" from "file://" + baseDirectory.value + "/lib/PermissionsEx-1.23.4.jar" intransitive(),
    "litebans" % "api" % "0.3.1" % "provided" from "file://" + baseDirectory.value + "/lib/LiteBansAPI.jar" intransitive()
)

/* Assembly */
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyMergeStrategy in assembly := {
    case "plugin.yml"   => MergeStrategy.first /* always choose our own plugin.yml if we shade other plugins */
    case x              =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
}
assemblyJarName in assembly := Name + "-" + Version + ".jar"
