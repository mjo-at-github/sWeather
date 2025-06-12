//> using dep "com.squareup.okhttp3:okhttp:4.12.0"

//> using dep "com.google.code.gson:gson:2.13.1"

import okhttp3.{OkHttpClient, Request, Response}

import com.google.gson.{JsonObject, JsonParser}

import scala.util.{Try, Success, Failure}

object Weather:

  private def parseTemperatureFromJson(json: String): Double =

    val jsonObj = JsonParser.parseString(json).getAsJsonObject

    jsonObj.getAsJsonObject("main").get("temp").getAsDouble

  @main def main(args: String*): Unit =

    val apiKey = ""

    println(args)

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

            val temperature = parseTemperatureFromJson(responseBody)

            println(s"Temperature in $city: ${temperature}°C")

          else

            println(s"Error: ${response.code} - ${response.message}")

        finally

          response.close()

      case Failure(exception) =>

        println(s"Request failed: ${exception.getMessage}")