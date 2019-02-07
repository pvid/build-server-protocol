package ch.epfl.scala.bsp.gen
import java.net.URI
import java.nio.file.{Path, Paths}

import org.scalacheck.Gen

object UtilGenerators {

  /** An uri string. */
  lazy val genUri: Gen[String] = for {
    schema <- Gen.listOfN(5, Gen.alphaChar).map(_.mkString)
    host <- Gen.identifier
    port <- Gen.choose(80, 1024)
    segmentCount <- Gen.choose(0, 10)
    segments <- Gen.listOfN(segmentCount, Gen.identifier)
  } yield s"$schema://$host:$port/${segments.mkString("/")}"

  /** A system-dependent file path. */
  lazy val genPath: Gen[Path] = for {
    segmentCount <- Gen.choose(0, 10)
    segments <- Gen.listOfN(segmentCount, Gen.identifier)
  } yield Paths.get(segments.mkString("/")).toAbsolutePath

  /** URI representing a system-dependent file. */
  lazy val genFileUri: Gen[URI] = genPath.map(_.toUri)

  /** URI path representing a system-dependent file. */
  lazy val genFileUriString: Gen[String] = genFileUri.map(_.toString)

  /** Fully qualified class name. */
  lazy val genFQN: Gen[String] = for {
    packages <- Gen.nonEmptyListOf(Gen.identifier)
    className <- genClassName
  } yield s"${packages.mkString(".")}.$className"

  /** A valid classname, beginning with uppercase character. */
  lazy val genClassName: Gen[String] = for {
    initial <- Gen.alphaChar
    rest <- Gen.identifier
  } yield s"$initial$rest"


}