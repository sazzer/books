package uk.co.grahamcox.oauth

/**
 * Representation of an OAuth Key - either a Consumer Key or a Token
 * @param key The value of the key
 * @param secret The secret for the key
 */
class Key(val key: String, val secret: String)
