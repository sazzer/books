package uk.co.grahamcox.oauth

/**
 * Representation of the actual HTTP method to be made
 * @param method The HTTP Method to use
 * @param url The URL to be called
 * @param parameters The parameters to the method
 */
class Request(val method: String = "GET", 
  val url: String,
  val parameters: Map[String, String] = Map.empty[String, String])
