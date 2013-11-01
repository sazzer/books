package uk.co.grahamcox.oauth

import java.util.UUID

/**
 * Representation of a Nonce in the OAuth transaction
 * @param value The value of the nonce
 */
class Nonce(val value: String = UUID.randomUUID().toString)
