package org.bz.autoorganizer.root.extensions

import timber.log.Timber.Forest.tag

/**
 * Debug with custom tag
 */
fun logDebugCustomTag(tag: String, message: String) {
    tag(tag).d(message)
}

/**
 * Verbose with custom tag
 */
fun logVerboseCustomTag(tag: String, message: String) {
    tag(tag).v(message)
}

/**
 * Info with custom tag
 */
fun logInfoCustomTag(tag: String, message: String) {
    tag(tag).i(message)
}