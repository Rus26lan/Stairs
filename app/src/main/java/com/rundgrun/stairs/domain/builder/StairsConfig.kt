package com.rundgrun.stairs.domain.builder

data class StairsConfig(
    val height: Float,
    val landing: Float,
    val steps: Int
) {
    val interval = height / steps
    val rungLanding = landing / steps
}
