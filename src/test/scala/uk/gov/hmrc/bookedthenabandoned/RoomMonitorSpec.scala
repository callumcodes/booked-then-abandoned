package uk.gov.hmrc.bookedthenabandoned

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult

class RoomMonitorSpec extends org.specs2.mutable.Specification {
  "RoomMonitor Hello" >> {
    "return 200" >> {
      uriReturns200()
    }
    "return hello world" >> {
      uriReturnsHelloWorld()
    }
  }


  "RoomMonitor Post" >> {
    "return 200" >> {
      roomUriReturns200()
    }

    "return log info" >> {
      roomUriReturnLogInfo()
    }
  }

  private[this] val retHelloWorld: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("/hello/world"))
    RoomMonitorServer.service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def uriReturns200(): MatchResult[Status] =
    retHelloWorld.status must beEqualTo(Status.Ok)

  private[this] def uriReturnsHelloWorld(): MatchResult[String] =
    retHelloWorld.as[String].unsafeRunSync() must beEqualTo("{\"message\":\"Hello, world\"}")

  private[this] val retRoom: Response[IO] = {
    val getHW = Request[IO](Method.POST, Uri.uri("/room/BP9001"))
    RoomMonitorServer.service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def roomUriReturns200(): MatchResult[Status] =
    retRoom.status must beEqualTo(Status.Ok)

  private[this] def roomUriReturnLogInfo() =
    retRoom.as[String].unsafeRunSync() must startWith("Logged movement in BP9001")


}
