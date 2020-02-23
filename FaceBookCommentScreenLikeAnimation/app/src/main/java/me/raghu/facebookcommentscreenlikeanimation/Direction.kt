package me.raghu.facebookcommentscreenlikeanimation

/**
 * Direction sealed class used as enum for defining directions
 */
sealed class Direction {
    class LEFT : Direction()
    class RIGHT : Direction()
    class UP : Direction()
    class DOWN : Direction()
    class NONE : Direction()
}