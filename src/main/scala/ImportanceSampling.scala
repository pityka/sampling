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
    val totalWeight = samples.map(weight).sum
    val numerator = samples.map(sample => f(sample) * weight(sample)).sum
    val estimate = numerator / totalWeight
    def weight_i(x: Double) = weight(x) / totalWeight
    val variance = samples.map { sample =>
      val wi = weight_i(sample)
      val d = f(sample) - estimate
      wi * wi * d * d
    }.sum
    Estimate(estimate, variance)

  }
}
