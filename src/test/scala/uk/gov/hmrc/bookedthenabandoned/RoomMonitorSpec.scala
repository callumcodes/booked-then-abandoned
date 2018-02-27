package uk.gov.hmrc.bookedthenabandoned

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult
import uk.gov.hmrc.bookedthenabandoned.services.Room

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


  "RoomMonitor Rooms" >> {
    "return 200" >> {
      roomUriReturns200()
    }

    "return rooms" >> {
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


  private[this] val retRooms: Response[IO] = {
    val getHW = Request[IO](Method.POST, Uri.uri("/rooms"))
    RoomMonitorServer.service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def roomsUriReturns200(): MatchResult[Status] =
    retRooms.status must beEqualTo(Status.Ok)

  private[this] def roomsUriReturnRooms() = {
    val testList = List(
      Room("BP9001"),
      Room("BP9002"),
      Room("Pink Show and Tell")
    )
    retRooms.as[String].unsafeRunSync() must beEqualTo(testList)
  }
}
