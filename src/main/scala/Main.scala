import okhttp3.{OkHttpClient, Request, Response}
import com.google.gson.{JsonObject, JsonParser}
import scala.util.{Try, Success, Failure}

object sWeather:

  private def parseTemperatureFromJson(json: String): Double =
    val jsonObj = JsonParser.parseString(json).getAsJsonObject
    jsonObj.getAsJsonObject("main").get("temp").getAsDouble

  private def parseFeelsLikeFromJson(json: String): Double =
    val jsonObj = JsonParser.parseString(json).getAsJsonObject
    jsonObj.getAsJsonObject("main").get("feels_like").getAsDouble

  @main def main(args: String*): Unit =

    val apiKey = "e8afda2e61d4d6272f9313ee6637c5e5"

    val city = args(0)

    val apiUrl = s"http://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiKey&units=metric"
    
    val client = new OkHttpClient()

    val request = new Request.Builder()
      .url(apiUrl)
      .build()

    Try(client.newCall(request).execute()) match

      case Success(response) =>

        try

          if response.isSuccessful then

            val responseBody = response.body.string

            // println(responseBody)

            val temperature = parseTemperatureFromJson(responseBody)
            val feels_like = parseFeelsLikeFromJson(responseBody)

            println(s"The temperature in $city is ${temperature}. Feels like ${feels_like}.")

          else

            println(s"Error: ${response.code} - ${response.message} ")

        finally

          response.close()

      case Failure(exception) =>

        println(s"Request failed: ${exception.getMessage}")