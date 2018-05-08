package sampling

case class Estimate(estimate: Double, variance: Double)

object ImportanceSampling {
  def unnormalized(evaluateUnnormalizedTarget: Double => Double,
                   sampleFromSamplingDistribution: => Double,
                   evaluateSamplingDistribution: Double => Double,
                   f: Double => Double,
                   numberOfSamples: Int) = {
    val samples =
      1 to numberOfSamples map (_ => sampleFromSamplingDistribution) toList

    def weight(x: Double) =
      evaluateUnnormalizedTarget(x) / evaluateSamplingDistribution(x)

    val weights = samples.map(weight).toArray
    val totalWeight = weights.sum
    val numerator = samples.zipWithIndex.map {
      case (sample, i) => f(sample) * weights(i)
    }.sum
    val estimate = numerator / totalWeight
    def weight_i(x: Double, i: Int) = weights(i) / totalWeight
    val variance = samples.zipWithIndex.map {
      case (sample, i) =>
        val wi = weight_i(sample, i)
        val d = f(sample) - estimate
        wi * wi * d * d
    }.sum
    Estimate(estimate, variance)

  }
}
