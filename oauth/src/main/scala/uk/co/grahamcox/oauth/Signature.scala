package uk.co.grahamcox.oauth

/**
 * Representation of a Signature in the OAuth transaction
 * @param value The value of the signature
 * @param method The method for the signature
 */
class Signature(val value: String, val method: String)

